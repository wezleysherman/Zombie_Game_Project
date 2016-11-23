package com.zombie.game;

/**
*
* @author jensenhemming
*/
public class PistolAmmo extends Bullet {

//------------------------------------------------------------------------------
//Sets attributes for the 9mm ammo for the Pistol
//------------------------------------------------------------------------------   
   private final String ammoType = "9mm";
   private final int ammoSpeed = 8; //just a filler will change
   private final int damage = 2; // possibly will change

   public PistolAmmo(float x, float y, float rot, boolean isFriendly, float damage) {
       super(x, y, rot, isFriendly, damage);
   }
   
   public String SetAmmoType(){
        return ammoType;
   }
   
   public int SetAmmoSpeed(){
        return ammoSpeed;
   }
   
   public int SetDamage(){
        return damage;
   }
}