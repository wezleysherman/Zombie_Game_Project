package com.zombie.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class main extends ApplicationAdapter 
{
	// Various variables for graphics and networking
	SpriteBatch spBatch;
	TextureRegion background;
	TextureRegion wall;
	Player mainPlayer;
	Player clientPlayer;
	OrthographicCamera cam;
	PCControls pcController;
	NetworkAdapter netAdapt;
    // initial player health
	int MAX_HEALTH = 100;
	
	// Colliders
	int BULLET_WIDTH_COL = 48;
	int BULLET_HEIGHT_COL = 48;
	int PLAYER_WIDTH_COL = 48;
	int PLAYER_HEIGHT_COL = 48;
	
	//Player variables for speed and camera bounds
	float playerMoveSpeed = 2f;
	float playerCamBounds = 150.5f;
	
	boolean gameStarted = false;
	boolean gameEnded = false;
	
	// Main engine method to initialize all graphics and server/client connections
	@Override
	public void create () 
	{
		cam = new OrthographicCamera(30,30*(Gdx.graphics.getWidth()/Gdx.graphics.getHeight()));
		spBatch = new SpriteBatch();
		background = new TextureRegion(new Texture("grass.png"));
		wall = new TextureRegion(new Texture("brick.png"));
		mainPlayer = new Player(0, 0, 0, new TextureRegion(new Texture("player.png")), playerMoveSpeed, new TextureRegion(new Texture("bullet.png")));
		clientPlayer = new Player(-9999, -9999, 0, new TextureRegion(new Texture("zombie.png")), playerMoveSpeed, new TextureRegion(new Texture("bullet.png")));
		cam.setToOrtho(false, 800, 480);
		pcController = new PCControls(mainPlayer, cam);
		netAdapt = new NetworkAdapter(mainPlayer, clientPlayer);
		
		netAdapt.createServer();
	}
	
	// Update camera position, check for inputs, and check for health/damage
	public void updateLoop() 
	{
		updateCameraPosition();
		pcController.checkInput();
		checkPlayerHealths();
		checkBulletsPos();
	}
	
	// Check player health, if the player dies then reset their position to (0, 0)
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

		if(clientPlayer.playerScore >= 20 || mainPlayer.playerScore >= 20)
		{
			gameEnded = true;
		}
	}
	
	// Loop through the bullets in the world and check to see if they have collided with the player or client, 
	// then determine if damage needs to be taken.
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
				mainPlayer.playerHealth -= bul.getAmmoDamage();
				mainPlayer.bulletsInWorld.remove(i);
			} 
			if(clientX < (bulX+BULLET_WIDTH_COL) && clientX > (bulX-BULLET_WIDTH_COL) &&
					clientY < (bulY+BULLET_HEIGHT_COL) && clientY > (bulY-BULLET_WIDTH_COL) && bul.getFriendly())
			{
				clientPlayer.playerHealth -= bul.getAmmoDamage();
				mainPlayer.bulletsInWorld.remove(i);
			}
		}
	}
	
	// Translate the camera if the player moves outside of the camera's bounding box.
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
	
	// Loop used to draw the players and the map.
	public void drawLoop() 
	{
		Vector3 textCoords = new Vector3(10, 10, 0);
		cam.unproject(textCoords);
		cam.update();
		spBatch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(25/255f, 25/255f, 112/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spBatch.begin();
		drawMap();
		// If the game has started and not ended -- draw the players and hud
		if(gameStarted == true && gameEnded == false)
		{
			mainPlayer.drawPlayer(spBatch);
			clientPlayer.drawPlayer(spBatch);
			BitmapFont hudFont = new BitmapFont();
			hudFont.draw(spBatch, "Health: " + mainPlayer.playerHealth + "\nScore: " + mainPlayer.playerScore, textCoords.x, textCoords.y);
		}
		else if(gameEnded == false)
		{
			// main menu -- enter to continue -- escape to quit
			BitmapFont menuFont = new BitmapFont();
			menuFont.draw(spBatch, "Press 'ENTER' to start \n\n\n Press 'ESC' to exit",Gdx.app.getGraphics().getWidth()/2-50, Gdx.app.getGraphics().getHeight()/2+75);
			if(Gdx.input.isKeyPressed(Keys.ENTER))
			{
				gameStarted = true;
			}
			else if(Gdx.input.isKeyPressed(Keys.ESCAPE))
			{
				Gdx.app.exit();
			}
		}
		else if(gameEnded == true)
		{
			// If one of the players reaches a score of 20, display the end game dialogue
			BitmapFont gameOverFont = new BitmapFont();
			if(mainPlayer.playerScore == 20)
			{
				gameOverFont.draw(spBatch, "Game over! \n You win!!! \n\n Press 'Space' to restart",Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2+75);
			}
			else
			{
				gameOverFont.draw(spBatch, "Game over! \n You lose!!! \n\n Press 'Space' to restart",Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2+75);
			}
			
			if(Gdx.input.isKeyPressed(Keys.SPACE))
			{
				mainPlayer.playerScore = 0;
				clientPlayer.playerScore = 0;
				gameEnded = false;
			}
		}
		spBatch.end();
	}

	void drawMap()
	{
		//Due to the way the map is generated, these draws are needed for all corners except for the bottom left.
		spBatch.draw(wall, -64, 512);
		spBatch.draw(wall, 832, -64);
		spBatch.draw(wall, 832, 512);
		
		//Iterates through the length and width of the map to create grass and walls. Starts at -1 for the walls.
		for(int i = -1; i < cam.viewportWidth/background.getRegionWidth(); i++) {
			for(int j = -1; j < cam.viewportHeight/background.getRegionHeight(); j++) {
				
				//If the x or y position of the map is 0, then it is a border, and a wall should be drawn instead.
				if ( i < 0 || j < 0)
				{
					//Draws walls on left and bottom.
					spBatch.draw(wall, wall.getRegionWidth()*i, wall.getRegionHeight()*j);
				}
				else
				{
					//Draws the grass, and also the walls on the right and top.
					spBatch.draw(background, background.getRegionWidth()*i, background.getRegionHeight()*j);
					spBatch.draw(wall, wall.getRegionWidth()*i, 512);
					spBatch.draw(wall, 832, wall.getRegionHeight()*j);
				}
			}
		}
		
	}
	
	// Render the game, and execute the draw loop and update loop
	@Override
	public void render () 
	{
		drawLoop();
		// Only update the game if it is not the main menu and the game has not ended.
		if(gameStarted == true && gameEnded == false) 
		{
			updateLoop();
		}
	}
	
	// Dispose of the game
	@Override
	public void dispose () 
	{
		spBatch.dispose();
	}
}