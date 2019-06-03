package com.example.ritamartiniano.earthcleanser.Model;

public class User {

    private String username, GasType, MPG;

    public User(){}

    public User(String username, String GasType, String MPG)
    {
        this.username = username;
        this.GasType = GasType;
        this.MPG = MPG;
    }

    public String getusername()
    {
        return username;
    }
    public void setusername(String username){
        this.username = username;
    }
    public String getGasType() {
        return GasType;
    }

    public String getMpg() {
        return MPG;
    }
    public void setMpg(String MPG)
    {
        this.MPG = MPG;
    }
    public void setGasType(String GasType)
    {
        this.GasType = GasType;
    }
}
