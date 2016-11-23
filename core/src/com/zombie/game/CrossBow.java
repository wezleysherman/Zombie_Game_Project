package com.zombie.game;


/**
 *
 * @author jensenhemming
 */
public class CrossBow extends Weapon {
//------------------------------------------------------------------------------
//Sets the attributes for the crossbow in the game
//------------------------------------------------------------------------------    
    private final int damage = 4; //damage subject to change
    private int capacity = 1; //can hold 1 arrow
    private final int reloadTime = 2; //2 seconds to reload
    private final int rateFire = 3; // fires one arrow every 3 seconds
    private final int range = 15; //range of 15 feet(?)
    private final String weaponName = "Crossbow"; //weapons name
    private static final int idNum = 2; //weapons id
    private final String ammoType = "Arrow"; //weapons ammo type
    
    public String SetName(){
        return weaponName;
    }
   
    public int SetidNum(){
        return idNum;
    }
    
    public int SetDamage(){
        return damage;
    }
    
    public int SetReloadTime(){
        return reloadTime;
    }
    
    public int SetWeaponRate(){
        return rateFire;
    }
    
    public int SetRange(){
        return range;
    }
    
    public String SetBulletType(){
        return ammoType;
    }
    
    public int Reload(int capacity, int i){
        while(capacity > 0 ){ //while loop, while the capacity is more than 0
            return capacity;  //return the capacity and can keep firing
        }
        if(capacity == 0){ //once capacity is 0, must reload to weapons capacity
            for (i=0; i < reloadTime; i++){
                return i;
            }
            if(i == reloadTime){
                while (capacity <= 1){
                    int reload = capacity++;
                }
        }
        }
        return capacity;
    }
    public int Fire(int i){
        
        for(i = 0; i< rateFire; i++){ //will add 1 to i until i equals rateFire
            return i;
        }
        if(i == rateFire){ //when i equals rate of fire, shoot
            while(capacity > 0){
                capacity--; //when player shoots, subtract 1 from their capacity
        }
        return capacity;
    }
        return 0;
    }
    
}