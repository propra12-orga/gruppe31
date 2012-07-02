package gruppe31.bomberman;

import java.io.*;
import java.net.*;
/**
 * 
 * @author gruppe 31 -Bomberman
 * ClientProgram, die Thread Klasse erweitert
 *
 */
public class BombermanGameClient extends Thread {
	public Spiel spiel;
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	BombermanSocketMessage message;
	/**
	 * Konstruktor
	 * @param spiel
	 */
	BombermanGameClient(Spiel spiel) { 
		this.spiel = spiel;
	}
	/**
	 * Methode von Thread implementiert.
	 * Hier wird mit Server verbunden.
	 */
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
			sendMessage(this.spiel);
			do {
				try {
					spiel.refresh((Spiel) in.readObject());
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			} while(true);
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally {
			//4: Closing connection
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (requestSocket != null) {
					requestSocket.close();
				}
			} catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
    /**
     * schickt message to Server.
     * @param msg
     */
	public void sendMessage(Spiel msg) {
		try{
			out.writeObject(msg);
			out.reset();
		} catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	/**
	 * Socket Server sind geschlossen
	 * 
	 */
	public void close() {
		try {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (requestSocket != null) {
				requestSocket.close();
			}
		} catch(IOException ioException) {
			System.out.println("IOException caught while closing socket server.");
			ioException.printStackTrace();
		}
	}
}
