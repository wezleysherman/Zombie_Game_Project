package com.zombie.game;

import com.badlogic.gdx.math.Vector2;

public class DynamicObject {
	private float x;
	private float y;
	private float rotation;
	
	public DynamicObject(float x, float y, float rot)
	{
		this.x = x;
		this.y = y;
		this.rotation = rot;
	}
	
	public void setX(float newX)
	{
		this.x = newX;
	}
	
	public void setY(float newY)
	{
		this.y = newY;
	}
	
	public void setRot(float newRot)
	{
		this.rotation = newRot;
	}
	
	public Vector2 getPosVector()
	{
		Vector2 position = new Vector2(x, y);
		return position;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getRot()
	{
		return rotation;
	}
}
