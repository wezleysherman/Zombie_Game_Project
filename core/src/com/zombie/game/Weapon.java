package com.zombie.game;

public class Weapon {
    private int damage; //damage weapon does
    private int capacity; //capacity of weapon
    private int reloadTime; //reload time
    private int rateFire; // rate of fire
    private int range; //range of weapon
    private String weaponName;
    private static int idNum;
    private String bulletType;
    
    public String GetName(){
        return weaponName;
    }
    
    public int GetDamage(){
        return damage;
    }
    
    public int GetReloadTime(){
        return reloadTime;
    }
    
    public int GetWeaponRate(){
        return rateFire;
    }
    
    public int GetRange(){
        return range;
    }
    
    public String GetBulletType(){
        return bulletType;
    }
}
