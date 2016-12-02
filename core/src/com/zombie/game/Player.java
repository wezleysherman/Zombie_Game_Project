package com.zombie.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends DynamicObject 
{
	// Various variable initializers
	TextureRegion playerTexture;
	TextureRegion bulletTexture;
	float playerMoveSpeed;
    public List<Bullet> bulletsInWorld = new ArrayList();
    List<Bullet> bulletsToSend = new ArrayList();
    public int playerHealth = 100;
    public int playerScore = 0;
    
	public Player(float x, float y, float rot, TextureRegion texture, float moveSpeed, TextureRegion bulletTex) 
	{
		super(x, y, rot); //create dynamic object for player object
		this.playerTexture = texture; //set texture for player
		this.playerMoveSpeed = moveSpeed; //set player move speed
		this.bulletTexture = bulletTex; //set bullet texture
	}
	
	public void drawPlayer(SpriteBatch batch) 
	{
		float xPos = getX(); //get player object's x coordinate
		float yPos = getY(); //get player boject's y coordinate
		batch.draw(playerTexture, xPos, yPos, 0, 0, 32, 32, 1, 1, super.getRot()-90); //draw player at position with rotation
		
		if(bulletsInWorld.size() > 0) //if there are bullets in world
		{
			updateBullets(batch);//update positions of bullets in world
		}
	}
	
	private void updateBullets(SpriteBatch batch)
	{
		for(int i = 0; i < bulletsInWorld.size(); i++)  //iterate through bullets present in world
		{ 
			Bullet bul = bulletsInWorld.get(i); //retrieve bullet object from list
			if(bul != null) 
			{
				float dist = calcDistance(bul.getPosVector(), this.getPosVector());  //get distance between bullet and player object
				if(dist > 1280)//if bullet is a total distance of 1280 from the player
				{
					bulletsInWorld.remove(i);  //remove bullet from world
				}
				batch.draw(bulletTexture, bul.getX(), bul.getY(), 0, 0, 32, 32, 1, 1, bul.getRot()-90);  //create bullet visual
				// Add velocity to the bullet
				float radX = (float) (bul.getRot() * (Math.PI/180)); // Convert the rotations from euler to radians
				float radY = (float) (bul.getRot() * (Math.PI/180)); // Convert the rotations from euler to radians
				float bDirX = (float) Math.cos(radX);   //calculate change in x position
				float bDirY = (float) Math.sin(radY);   //calculate change in y position
				bul.setX(bul.getX() + (bDirX * 10)); //update x position
				bul.setY(bul.getY() + (bDirY * 10)); //update y position
			}
		}
	}
	
	public float getMoveSpeed()
	{
		return playerMoveSpeed; //return move speed of player
	}
	
	public void fireWeapon(Weapon newWeapon)
	{
		float rotationVector = this.getRot();     //get rotation of player object, set as rotation of bullet
		Bullet newBul = new Bullet(this.getX(), this.getY(), rotationVector, true, 20);   //create bullet at position of player and rotation of player
		bulletsInWorld.add(newBul);   //add bullet to world
		bulletsToSend.add(newBul);    //add bullet to send list
	}
}
