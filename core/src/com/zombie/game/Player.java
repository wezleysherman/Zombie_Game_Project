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
    List<Bullet> bulletsInWorld = new ArrayList();

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
		batch.draw(playerTexture, xPos, yPos, 0, 0, 32, 32, 1, 1, super.getRot());
		BitmapFont debugRot = new BitmapFont();
		debugRot.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		double screenHeight = Gdx.graphics.getHeight();
		String debugRotString = super.getRot() + ": " + super.getX() + " ," + (screenHeight - super.getY()) + "  " + Gdx.input.getX() + ", " + Gdx.input.getY();
		debugRot.draw(batch, debugRotString, 20, 20);
		if(bulletsInWorld.size() > 0) 
		{
			updateBullets(batch);
		}
	}
	
	private void updateBullets(SpriteBatch batch)
	{
		for(Bullet bul : bulletsInWorld)
		{
			batch.draw(bulletTexture, bul.getX(), bul.getY(), 0, 0, 32, 32, 1, 1, bul.getRot());
			float dirX = (float) Math.cos(Math.toRadians(bul.getRot()));
			float dirY = (float) Math.sin(Math.toRadians(bul.getRot()));
			bul.setX(bul.getX() + (dirX * 2));
			bul.setY(bul.getY() + (dirY * 2));
		}
	}
	
	public float getMoveSpeed()
	{
		return playerMoveSpeed;
	}
	
	public void fireWeapon(Weapon newWeapon)
	{
		float rotationVector = this.getRot();
		Bullet newBul = new Bullet(this.getX(), this.getY(), rotationVector);
		bulletsInWorld.add(newBul);
	}
	
}
