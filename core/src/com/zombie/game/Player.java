package com.zombie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends DynamicObject 
{
	TextureRegion playerTexture;
	float playerMoveSpeed;
	
	public Player(float x, float y, float rot, TextureRegion texture, float moveSpeed) 
	{
		super(x, y, rot);
		this.playerTexture = texture;
		this.playerMoveSpeed = moveSpeed;
		loadPlayer();
	}

	void loadPlayer()
	{
		
	}
	
	public void drawPlayer(SpriteBatch batch) 
	{
		float xPos = getX();
		float yPos = getY();
		//batch.draw(playerTexture, xPos, yPos, super.getRot());
		batch.draw(playerTexture, xPos, yPos, 0, 0, 32, 32, 1, 1, super.getRot());
		//
		BitmapFont debugRot = new BitmapFont();
		debugRot.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		double screenHeight = Gdx.graphics.getHeight();
		String debugRotString = super.getRot() + ": " + super.getX() + " ," + (screenHeight - super.getY()) + "  " + Gdx.input.getX() + ", " + Gdx.input.getY();
		debugRot.draw(batch, debugRotString, 20, 20);
		//
	}
	
	public float getMoveSpeed()
	{
		return playerMoveSpeed;
	}
}
