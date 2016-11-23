package com.zombie.game;

/**
*
* @author jensenhemming
*/
public class Pistol extends Weapon {
//------------------------------------------------------------------------------
//sets attributes for the pistol in the game
//------------------------------------------------------------------------------    
   private final int damage = 2; //damage subject to change
   private int capacity = 6; //can hold 6 bullets
   private final int reloadTime = 3; //3 seconds to reload
   private final int rateFire = 3; // fires one bullet every 3 seconds
   private final int range = 7; //range of 7 feet(?)
   private final String weaponName = "Pistol";  //weapon name
   private static final int idNum = 0; // weapon ID
   private final String ammoType = "9mm"; //weapon ammo tpye
   
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
   
   public int Reload(int capacity,int i){
       while(capacity > 0 ){ //while loop, while the capacity is more than 0
           return capacity;  //return the capacity and can keep firing
       }
       if(capacity == 0){ //once capacity is 0, must reload to weapons capacity
           for (i=0; i < reloadTime; i++){
               return i;
           }
           if(i == reloadTime){
           while (capacity <= 6){
               int reload = capacity++;
           }
       }
       return capacity;
   }
       return 0;
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