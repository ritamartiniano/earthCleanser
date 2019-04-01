package com.example.ritamartiniano.earthcleanser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class CreateAction extends AppCompatActivity {
    Spinner sector;
    TextView title_ac, desc_ac;
    ImageView img;
    Button addAction;
    Uri file;
    String title,description,ac_sector;
    Bundle extras;
    String receiveSector, receivePoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);
        title_ac = findViewById(R.id.title_ac);
        desc_ac = findViewById(R.id.description_ac);
        sector = findViewById(R.id.sectorSpinner);
        img = findViewById(R.id.actionImage);
        addAction = findViewById(R.id.btnAdd);
        extras = getIntent().getExtras();
        if(extras!= null)
        {
          receivePoints = extras.getString("Points");
          receiveSector = extras.getString("Sector");
          Log.d("CreateAction",receivePoints + receiveSector);
        }
        ArrayAdapter<String> options = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.options));
        options.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sector.setAdapter(options);
        addAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = title_ac.getText().toString();
                description = desc_ac.getText().toString();
                if(receiveSector != null)
                {
                    ac_sector = receiveSector;
                }
                else{
                    ac_sector = sector.getSelectedItem().toString();
                }
                addAction();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.photo:
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Bitmap bm = (Bitmap)data.getExtras().get("data");
            img.setImageBitmap(bm);
            file = data.getData();
            if(file == null)
            {
                Toast.makeText(CreateAction.this,"file is null",Toast.LENGTH_SHORT).show();
            }
    }
    public void addAction()
    {
        final StorageReference mStorageRef;
        //save the url of the storage reference to the database
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Actions");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = user.getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Points");
        final String action_ID = ref.push().getKey();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Actions").child(userID).child(action_ID);
        mStorageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUri = uri.toString();
                        // Log.d("CreateAction",uri);
                        HashMap<String,String> action = new HashMap<>();
                        action.put("title",title);
                        action.put("description",description);
                        action.put("sector",ac_sector);
                        action.put("image",downloadUri);
                        ref.child(userID).child(action_ID).setValue(action);
                    }
                });
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==receiveSector)
                {
                    int points = Integer.parseInt(dataSnapshot.getValue().toString());
                    int totalPoints = Integer.parseInt(points + receivePoints);
                    reference.setValue(totalPoints);
                }
                else
                {
                    reference.child("Points");
                    HashMap<String,Object> points = new HashMap<>();
                    points.put("total",Integer.valueOf(receivePoints));
                    reference.child(userID).child(receiveSector).setValue(points);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_action,menu);
        return true;
    }
}
