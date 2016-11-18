package com.zombie.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class main extends ApplicationAdapter 
{
	SpriteBatch batch;
	TextureRegion background;
	Player mainPlayer;
	Player clientPlayer;
	OrthographicCamera cam;
	PCControls pcController;
	NetworkAdapter netAdapt;

	int MAX_HEALTH = 100;
	// Colliders
	int BULLET_WIDTH_COL = 48;
	int BULLET_HEIGHT_COL = 48;
	int PLAYER_WIDTH_COL = 48;
	int PLAYER_HEIGHT_COL = 48;
	
	float playerMoveSpeed = 2f;
	float playerCamBounds = 150.5f;

	@Override
	public void create () 
	{
		cam = new OrthographicCamera(30,30*(Gdx.graphics.getWidth()/Gdx.graphics.getHeight()));
		batch = new SpriteBatch();
		background = new TextureRegion(new Texture("grass.png"));
		mainPlayer = new Player(0, 0, 0, new TextureRegion(new Texture("player.png")), playerMoveSpeed, new TextureRegion(new Texture("bullet.png")));
		clientPlayer = new Player(-9999, -9999, 0, new TextureRegion(new Texture("zombie.png")), playerMoveSpeed, new TextureRegion(new Texture("bullet.png")));
		cam.setToOrtho(false, 800, 480);
		pcController = new PCControls(mainPlayer, cam);
		netAdapt = new NetworkAdapter(mainPlayer, clientPlayer);
		
		netAdapt.createServer();
		
	}
	
	public void updateLoop() 
	{
		updateCameraPosition();
		pcController.checkInput();
		checkPlayerHealths();
		checkBulletsPos();
	}
	
	private void checkPlayerHealths()
	{
		if(mainPlayer.playerHealth <= 0) 
		{
			clientPlayer.playerScore ++;
			mainPlayer.playerHealth = MAX_HEALTH;
			mainPlayer.setX(0);
			mainPlayer.setY(0);
		}
		
		if(clientPlayer.playerHealth <= 0)
		{
			mainPlayer.playerScore++;
			clientPlayer.playerHealth = MAX_HEALTH;
		}
	}
	
	private void checkBulletsPos()
	{
		for(int i = 0; i < mainPlayer.bulletsInWorld.size(); i++)
		{ 
			Bullet bul = mainPlayer.bulletsInWorld.get(i);
			Vector3 bulletProj = cam.project(new Vector3(bul.getX(), bul.getY(), 0));
			Vector3 playProj = cam.project(new Vector3(mainPlayer.getX(), mainPlayer.getY(), 0));
			Vector3 clientProj = cam.project(new Vector3(clientPlayer.getX(), clientPlayer.getY(), 0));
			
			float bulX = bulletProj.x;
			float bulY = bulletProj.y;
			float playX = playProj.x;
			float playY = playProj.y;
			float clientX = clientProj.x;
			float clientY = clientProj.y;
			
			if(playX < (bulX+BULLET_WIDTH_COL) && playX > (bulX-BULLET_WIDTH_COL) &&
						playY < (bulY+BULLET_HEIGHT_COL) && playY > (bulY-BULLET_WIDTH_COL) && !bul.getFriendly())
			{
				mainPlayer.playerHealth -= bul.getDamage();
				mainPlayer.bulletsInWorld.remove(i);
			} 
			if(clientX < (bulX+BULLET_WIDTH_COL) && clientX > (bulX-BULLET_WIDTH_COL) &&
					clientY < (bulY+BULLET_HEIGHT_COL) && clientY > (bulY-BULLET_WIDTH_COL) && bul.getFriendly())
			{
				clientPlayer.playerHealth -= bul.getDamage();
				mainPlayer.bulletsInWorld.remove(i);
			}
		}
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
		Vector3 textCoords = new Vector3(10, 10, 0);
		cam.unproject(textCoords);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		drawMap();
		mainPlayer.drawPlayer(batch);
		clientPlayer.drawPlayer(batch);
		BitmapFont hudFont = new BitmapFont();
		hudFont.draw(batch, "Health: " + mainPlayer.playerHealth + "\nScore: " + mainPlayer.playerScore, textCoords.x, textCoords.y);
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
	}
}
