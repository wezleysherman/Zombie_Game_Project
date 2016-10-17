package com.zombie.game;

public class Controls {
	Player player;
	
	public Controls(Player p)
	{
		player = p;
	}
	
	public void fireWeapon(Weapon newWeapon)
	{
		
	}
	
	public void moveUp(float speed)
	{
		float newYPos = player.getY() + speed;
		player.setY(newYPos);
	}
	
	public void moveDown(float speed)
	{
		float newYPos = player.getY() - speed;
		player.setY(newYPos);
	}
	
	public void moveLeft(float speed)
	{
		float newXPos = player.getX() - speed;
		player.setX(newXPos);
	}
	
	public void moveRight(float speed)
	{
		float newXPos = player.getX() + speed;
		player.setX(newXPos);
	}
	
	public void rotate(float rotation)
	{
		player.setRot(rotation);
	}
	
}
