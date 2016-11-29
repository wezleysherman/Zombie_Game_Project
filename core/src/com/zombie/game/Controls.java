package com.zombie.game;

// Parent class of various Controls options (Allows multiplatform expansion)
public class Controls 
{
	Player player;
	
	public Controls(Player p)
	{
		player = p;
	}
	
	public void fireWeapon(Weapon newWeapon)
	{
		player.fireWeapon(newWeapon);
	}
	
	public void moveUp(float speed)
	{
		//Collision testing
		if(player.getY() + speed >= 512)
		{
			speed = 0;
		}
				
		float newYPos = player.getY() + speed;
		player.setY(newYPos);
	}
	
	public void moveDown(float speed)
	{
		//Collision testing
		if(player.getY() - speed <= 0)
		{
			speed = 0;
		}
		
		float newYPos = player.getY() - speed;
		player.setY(newYPos);
	}
	
	public void moveLeft(float speed)
	{
		//Collision testing
		if(player.getX() - speed <= 0)
		{
			speed = 0;
		}
		
		float newXPos = player.getX() - speed;
		player.setX(newXPos);
	}
	
	public void moveRight(float speed)
	{
		//Collision testing
		if(player.getX() + speed >= 832)
		{
			speed = 0;
		}
		
		float newXPos = player.getX() + speed;
		player.setX(newXPos);
	}
	
	public void rotate(float rotation)
	{
		player.setRot(rotation);
	}
	
}