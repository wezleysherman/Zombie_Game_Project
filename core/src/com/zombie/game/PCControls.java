package com.zombie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class PCControls extends Controls
{
    int inputKeyForward = Keys.W;
    int inputKeyBackward = Keys.S;
    int inputKeyLeft = Keys.A;
    int inputKeyRight = Keys.D;
    int inputKeyFire = Keys.SPACE;
    
    Camera mainCam;
	Player player;
	Vector2 mousePos;
	
	boolean alreadyConn = false;

	public PCControls(Player p, Camera cam)
	{
		super(p);
		this.player = p;
		this.mainCam = cam;
		this.mousePos = new Vector2();
	}
	
	// Method used to check for user input across the various `inputKey` variables.
	public void checkInput() 
	{
		float rotation = computeRotation();
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
		
		if(Gdx.input.isKeyJustPressed(inputKeyFire))
		{
			super.fireWeapon(new Weapon());
		}
	}
	

	private float computeRotation()
	{
		// Unproject the position vector of the mouse position relative to the camera on the screen.
		Vector3 point = mainCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		// Turn the player position into a Vector3.
		Vector3 objPos = new Vector3(player.getX(), player.getY(), 0);
		
		// Compute the rotation angle between the player and the mouse cursor.
	    float angle = (float)((Math.atan2(point.y - objPos.y, point.x - objPos.x) * (180.0F / Math.PI)));
	    
		return angle;
	}
}
