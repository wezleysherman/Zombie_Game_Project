package com.zombie.game;

public class Bullet extends DynamicObject
{
	private boolean isFriendly;
	private float damage;
	private String ammoType; // Future implementation
    private int ammoSpeed; // Future implementation
	
	public Bullet(float x, float y, float rot, boolean isFriendly, float damage)
	{
		super(x, y, rot);             //create dynamicObject with given position data
		this.isFriendly = isFriendly; //set if bullet is friendly or not(if bullet will hurt player)
		this.damage = damage;
	}
	
	// Returns whether the bullet will hurt the player or not.
	public boolean getFriendly()
	{
		return isFriendly;
	}
	
	// Return the ammo type as a string
	public String getAmmoType()
	{
         return ammoType;
	}
	
	// Get the speed of the bullet
	public int getAmmoSpeed()
	{
         return ammoSpeed;
	}
	
	// Get the damage from the ammo
	public float getAmmoDamage()
	{
         return damage;
	}
	
	// Set the bullets damage
	public void setDamage(float newDamage)
	{
		damage = newDamage;
	}
}
