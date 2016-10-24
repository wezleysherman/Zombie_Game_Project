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
	Player player;
	Player clientPlayer;
	boolean serverOpened;

	public NetworkAdapter(Player player, Player client)
	{
		this.player = player;
		this.clientPlayer = client;
		serverOpened = true;
	}
	
	public void createServer() 
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				System.out.println("Opening Server...");
				ServerSocketHints servHints = new ServerSocketHints();
				servHints.acceptTimeout = 0;
				try {
					ServerSocket serv = Gdx.net.newServerSocket(Protocol.TCP, 1969, servHints);
					Socket clientSocket = serv.accept(null);
					try
					{
						while(serverOpened) {
							String playerSend = buildString(player) + "\n";
							String response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
							clientSocket.getOutputStream().write(playerSend.getBytes());
							applyClient(response);
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
	
	private void applyClient(String recieveMessage)
	{
		String[] recieveData = recieveMessage.split("\\s+");
		clientPlayer.setX(Float.parseFloat(recieveData[0]));
		clientPlayer.setY(Float.parseFloat(recieveData[1]));
		clientPlayer.setRot(Float.parseFloat(recieveData[2]));
	}
	
	private String buildString(Player pObj)
	{
		return pObj.getX() + " " + pObj.getY() + " " + pObj.getRot();
	}
	
	public void createClient() 
	{	
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				System.out.println("Opening Client");
				SocketHints clientHints = new SocketHints();

				Socket clientSocket = Gdx.net.newClientSocket(Protocol.TCP, "localhost", 1969, clientHints);
				try 
				{
					while(serverOpened) {
						String playerSend = buildString(player) + "\n";
						clientSocket.getOutputStream().write(playerSend.getBytes());
						String response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
						applyClient(response);
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
