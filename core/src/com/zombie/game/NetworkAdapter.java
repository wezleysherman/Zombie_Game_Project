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
	public void createServer() 
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				ServerSocketHints servHints = new ServerSocketHints();
				servHints.acceptTimeout = 0;
				ServerSocket serv = Gdx.net.newServerSocket(Protocol.TCP, 1111, servHints);
				Socket clientSocket = serv.accept(null);
				try
				{
					String messageRecieve = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
					// clientSocker.getOutputStream().write("asd".getBytes()));
				}
				catch(IOException e) 
				{
					System.out.println("Execption!: " + e);
				}
			}
		}).start();
	}
	
	public void createClient() 
	{	
		SocketHints clientHints = new SocketHints();

		Socket clientSocket = Gdx.net.newClientSocket(Protocol.TCP, "localhost", 1111, clientHints);
		try 
		{
			//client.getOutputStream().write("".getBytes());
			 String response = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine();
		} 
		catch(IOException e) 
		{
			System.out.println("Exception!: " + e);
		}
	}
}
