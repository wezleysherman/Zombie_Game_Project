package com.zombie.game;

/**
*
* @author jensenhemming
*/
public class BowAmmo extends Bullet {
	

//------------------------------------------------------------------------------
//Sets attributes for the arrow for the crossbow
//------------------------------------------------------------------------------    
   private final String ammoType = "Arrow";
   private final int ammoSpeed = 8; //plan on changing this, just a filler for now
   private final int damage = 4;

   public BowAmmo(float x, float y, float rot, boolean isFriendly, float damage) {
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
