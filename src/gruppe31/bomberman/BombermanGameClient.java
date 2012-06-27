
//package gruppe31.bomberman;

import java.io.*;
import java.net.*;
public class BombermanGameClient extends Thread {
	public Spiel spiel;
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	BombermanSocketMessage message;
	BombermanGameClient(Spiel spiel) { 
		this.spiel = spiel;
	}
	public void run()
	{
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("localhost", 2004);
			System.out.println("Connected to localhost in port 2004");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//3: Communicating with the server
			//sendMessage(BombermanSocketMessage.createStartGameMessage());
			sendMessage(BombermanSocketMessage.createGameStatusMessage(spiel));
			do {
				try{
					message = (BombermanSocketMessage) in.readObject();
					System.out.println("server>" + message);
					spiel.refresh(message);
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			} while(!message.equals("bye"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}

	
	public void sendMessage(BombermanSocketMessage msg) {
		try{
			out.writeObject(msg);
			//out.writeObject(spiel);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

}
