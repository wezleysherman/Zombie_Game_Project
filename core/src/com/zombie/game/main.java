package com.zombie.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class main extends ApplicationAdapter 
{
	SpriteBatch batch;
	TextureRegion background;
	Player mainPlayer;
	OrthographicCamera cam;
	PCControls pcController;
	
	float playerMoveSpeed = 2f;
	float playerCamBounds = 150.5f;
	
	@Override
	public void create () 
	{
		cam = new OrthographicCamera(30,30*(Gdx.graphics.getWidth()/Gdx.graphics.getHeight()));
		batch = new SpriteBatch();
		background = new TextureRegion(new Texture("grass.png"));
		mainPlayer = new Player(0, 0, 0, new TextureRegion(new Texture("player.png")), playerMoveSpeed);
		cam.setToOrtho(false, 800, 480);
		pcController = new PCControls(mainPlayer, cam);
		
	}
	
	public void updateLoop() 
	{
		updateCameraPosition();
		pcController.checkInput();
	}
	
	private void updateCameraPosition() 
	{
		Vector3 playerPos = new Vector3(mainPlayer.getX(), mainPlayer.getY(), 0);
		cam.project(playerPos);
		if(playerPos.x >= (Gdx.graphics.getWidth() - playerCamBounds)) 
		{
			cam.translate(2, 0, 0);
		} 
		else if(playerPos.x <= playerCamBounds) 
		{
			cam.translate(-2, 0, 0);
		}
		if(playerPos.y >= (Gdx.graphics.getHeight() - playerCamBounds)) 
		{
			cam.translate(0, 2, 0);
		} 
		else if(playerPos.y <= playerCamBounds) 
		{
			cam.translate(0, -2, 0);
		}
	}
	
	public void drawLoop() 
	{
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		drawMap();
		mainPlayer.drawPlayer(batch);
		
		batch.end();
	}
	
	void drawMap()
	{
		for(int i = 0; i < cam.viewportWidth/background.getRegionWidth(); i++) {
			for(int j = 0; j < cam.viewportHeight/background.getRegionHeight(); j++) {
				batch.draw(background, background.getRegionWidth()*i, background.getRegionHeight()*j);
			}
		}
	}
	
	@Override
	public void render () 
	{
		drawLoop();
		updateLoop();
	}
	
	@Override
	public void dispose () 
	{
		batch.dispose();
		//player.dispose();
	}
}
