package com.zombie.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends DynamicObject 
{
	TextureRegion playerTexture;
	TextureRegion bulletTexture;
	float playerMoveSpeed;
    public List<Bullet> bulletsInWorld = new ArrayList();
    List<Bullet> bulletsToSend = new ArrayList();
    public int playerHealth = 100;
    public int playerScore = 0;
    
	public Player(float x, float y, float rot, TextureRegion texture, float moveSpeed, TextureRegion bulletTex) 
	{
		super(x, y, rot);
		this.playerTexture = texture;
		this.playerMoveSpeed = moveSpeed;
		this.bulletTexture = bulletTex;
		loadPlayer();
		
	}

	void loadPlayer()
	{
		
	}
	
	public void drawPlayer(SpriteBatch batch) 
	{
		float xPos = getX();
		float yPos = getY();
		batch.draw(playerTexture, xPos, yPos, 0, 0, 48, 48, 1, 1, super.getRot()-90);
		
		if(bulletsInWorld.size() > 0) 
		{
			updateBullets(batch);
		}
	}
	
	private void updateBullets(SpriteBatch batch)
	{
		for(int i = 0; i < bulletsInWorld.size(); i++)
		{ 
			Bullet bul = bulletsInWorld.get(i);
			if(bul != null) 
			{
				float dist = calcDistance(bul.getPosVector(), this.getPosVector());
				if(dist > 1280)
				{
					bulletsInWorld.remove(i);
				}
				batch.draw(bulletTexture, bul.getX(), bul.getY(), 0, 0, 48, 48, 1, 1, bul.getRot()-90);
				float dirX = (float) Math.cos(Math.toRadians(bul.getRot()));
				float dirY = (float) Math.sin(Math.toRadians(bul.getRot()));
				bul.setX(bul.getX() + (dirX * 10));
				bul.setY(bul.getY() + (dirY * 10));
			}
		}
	}
	
	public float getMoveSpeed()
	{
		return playerMoveSpeed;
	}
	
	public void fireWeapon(Weapon newWeapon)
	{
		float rotationVector = this.getRot();
		Bullet newBul = new Bullet(this.getX(), this.getY(), rotationVector, true, 20);
		bulletsInWorld.add(newBul);
		bulletsToSend.add(newBul);
	}
	
}
