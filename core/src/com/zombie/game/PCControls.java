package com.zombie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class PCControls extends Controls
{
    int inputKeyForward = Keys.W;
    int inputKeyBackward = Keys.S;
    int inputKeyLeft = Keys.A;
    int inputKeyRight = Keys.D;
    Camera mainCam;
	Player player;
	Vector2 mousePos;
	
	public PCControls(Player p, Camera cam)
	{
		super(p);
		this.player = p;
		this.mainCam = cam;
		this.mousePos = new Vector2();
	}
	
	public void checkInput() 
	{
		float rotation = computeRotation(mousePos, player.getPosVector());
		player.setRot(rotation);
		if(Gdx.input.isKeyPressed(inputKeyForward))
		{
			super.moveUp(player.getMoveSpeed());
		}
		else if(Gdx.input.isKeyPressed(inputKeyBackward))
		{
			super.moveDown(player.getMoveSpeed());
		}
		
		if(Gdx.input.isKeyPressed(inputKeyLeft))
		{
			super.moveLeft(player.getMoveSpeed());
		}
		else if(Gdx.input.isKeyPressed(inputKeyRight))
		{
			super.moveRight(player.getMoveSpeed());
		}
	}
	
	private float computeRotation(Vector2 obj, Vector2 tar)
	{
		Vector3 point = mainCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 objPos = mainCam.unproject(new Vector3(tar.x, tar.y, 0));
		double screenHeight = Gdx.graphics.getHeight();

	    float angle = (float)((Math.atan2(point.y - (screenHeight - objPos.y), point.x - objPos.x) * (180/Math.PI))+90);
	    
		return angle;
	}
}
