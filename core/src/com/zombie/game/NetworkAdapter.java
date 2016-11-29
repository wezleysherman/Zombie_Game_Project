package com.zombie.game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class NetworkAdapter 
{
	// Create variables for the player and client.
	Player player;
	Player clientPlayer;
	// Tells us if the server is opened.
	boolean serverOpened;
	public ServerSocketHints servHints;
	public SocketHints clientHints;
	
	// Set the player and client options and set `serverOpened` to true.
	public NetworkAdapter(Player player, Player client)
	{
		this.player = player;
		this.clientPlayer = client;
		serverOpened = true;
	}
	
	// Whoever connects to the port first is automatically declared the server.
	// The next connection becomes the client.
	public void createServer() 
	{
		serverOpened = true;
		new Thread(new Runnable() // Create a new thread for the server
		{
			@Override
			public void run() 
			{
				System.out.println("Opening Server...");
				servHints = new ServerSocketHints();
				servHints.acceptTimeout = 0;
				// Attempt to open a new server on port '1979'
				// If the port is already in use then attempt to connect as the client.
				try 
				{
					ServerSocket serv = Gdx.net.newServerSocket(Protocol.TCP, 1979, servHints);
					Socket clientSocket = serv.accept(null);
					try
					{
						// If the server was created successfully enter a loop to send data between the server and client
						while(serverOpened) 
						{
							String playerSend = buildString(player) + "\n";
							String responseString = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
							clientSocket.getOutputStream().write(playerSend.getBytes());
							applyClient(responseString);
						}					
					}
					catch(IOException e) 
					{
						System.out.println("Execption!: " + e);
					}			
				} 
				catch (Exception e) 
				{
					
					createClient();
				}
			}
		}).start();
	}
	
	// Apply the data send between players to the other player object
	// Create new bullets if they are sent over
	private void applyClient(String recieveMessage)
	{
		String[] recieveData = recieveMessage.split("\\s+");
		clientPlayer.setX(Float.parseFloat(recieveData[0]));
		clientPlayer.setY(Float.parseFloat(recieveData[1]));
		clientPlayer.setRot(Float.parseFloat(recieveData[2]));
		for(int i = 3; i < recieveData.length; i++) 
		{
			float rotationVector = clientPlayer.getRot();
			Bullet newBul = new Bullet(clientPlayer.getX(), clientPlayer.getY(), rotationVector, false, 20);
			player.bulletsInWorld.add(newBul);
		}
	}
	
	// Builds the string of information to send over the server
	private String buildString(Player pObj)
	{
		String returnString = pObj.getX() + " " + pObj.getY() + " " + pObj.getRot();
		for(int i = 0; i < pObj.bulletsToSend.size(); i++) 
		{
			returnString += " B";
			pObj.bulletsToSend.remove(i);
		}
		return returnString;
	}
	
	// Creates a client to connect to the server
	// If client disconnects form the server then it automatically becomes to the server.
	public void createClient() 
	{	
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Opening Client");
				clientHints = new SocketHints();
				// Attempt to create a socket and connect to port '1979'
				Socket clientSocket = Gdx.net.newClientSocket(Protocol.TCP, "localhost", 1979, clientHints);
				try 
				{
					// If connection is successful -- send data to the server
					while(serverOpened)
					{
						String playerSend = buildString(player) + "\n";
						clientSocket.getOutputStream().write(playerSend.getBytes());
						String responseString = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
						try 
						{
							applyClient(responseString);
						} 
						catch(Exception e)
						{
							serverOpened = false;
							clientPlayer.setX(9999);
							clientPlayer.setY(9999);
							createServer();
							clientSocket.dispose();
						}
					}
				} 
				catch(IOException e) 
				{
					System.out.println("Exception!: " + e);
				}
			}
		}).start();
	}
}
