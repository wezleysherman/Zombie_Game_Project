package com.zombie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
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
		
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			super.fireWeapon(new Weapon());
		}
		Vector3 point = mainCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

		if(Gdx.input.isKeyPressed(Keys.L))
		{
			System.out.println("Mouse Pos: (" + point.x + ", " + point.y + ") Player: (" + player.getX() + ", " + player.getY()+") + " + rotation);
		
		}
	}
	

	private float computeRotation()
	{
		Vector3 point = mainCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 objPos = new Vector3(player.getX(), player.getY(), 0);
		
	    float angle = (float)((Math.atan2(point.y - objPos.y, point.x - objPos.x) * (180/Math.PI)));
	    
		return angle;
	}
}
