package com.zombie.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends DynamicObject
{
	private boolean isFriendly;
	public Bullet(float x, float y, float rot, boolean isFriendly)
	{
		super(x, y, rot);
		this.isFriendly = isFriendly;
	}
	
	public boolean getFriendly()
	{
		return isFriendly;
	}
}
