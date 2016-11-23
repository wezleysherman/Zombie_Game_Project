package com.zombie.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends DynamicObject
{
	private boolean isFriendly;
	private float damage;
	private String ammoType; // Future implementation
    private int ammoSpeed; // Future implementation
	
	public Bullet(float x, float y, float rot, boolean isFriendly, float damage)
	{
		super(x, y, rot);
		this.isFriendly = isFriendly;
		this.damage = damage;
	}
	
	public boolean getFriendly()
	{
		return isFriendly;
	}
	
	public String getAmmoType(){
         return ammoType;
	}
	
	public int getAmmoSpeed(){
         return ammoSpeed;
	}
	
	public float getAmmoDamage(){
         return damage;
	}
	
	public void setDamage(float newDamage)
	{
		damage = newDamage;
	}
}
