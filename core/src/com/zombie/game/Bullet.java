package com.zombie.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends DynamicObject
{
	private boolean isFriendly;
	private float damage;
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
	
	public float getDamage()
	{
		return damage;
	}
	
	public void setDamage(float newDamage)
	{
		damage = newDamage;
	}
}
