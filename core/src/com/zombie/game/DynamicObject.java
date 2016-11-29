package com.zombie.game;

import com.badlogic.gdx.math.Vector2;

public class DynamicObject 
{
	private float x;
	private float y;
	private float rotation;
	
	// Instantiate the object.
	public DynamicObject(float x, float y, float rot)
	{
		this.x = x;
		this.y = y;
		this.rotation = rot;
	}
	
	// Set the x coordinate
	public void setX(float newX)
	{
		this.x = newX;
	}
	
	// Set the y coordinate
	public void setY(float newY)
	{
		this.y = newY;
	}
	
	// Returns the rotation.
	public void setRot(float newRot)
	{
		this.rotation = newRot;
	}
	
	// Returns the position vector on a 2D Plane.
	public Vector2 getPosVector()
	{
		Vector2 position = new Vector2(x, y);
		return position;
	}
	
	// Returns the x position of the object.
	public float getX()
	{
		return x;
	}
	
	// Returns the y position of the object.
	public float getY()
	{
		return y;
	}
	
	// Returns the rotation of the object
	public float getRot()
	{
		return rotation;
	}
	
	//calculate distance between two objects
	public float calcDistance(Vector2 vect1, Vector2 vect2) 
	{
		return (float) Math.sqrt(((vect1.x - vect2.x)*(vect1.x-vect2.x)) + ((vect1.y - vect2.y)*(vect1.y - vect2.y)));
	}
}
