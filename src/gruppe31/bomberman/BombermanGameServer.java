package gruppe31.bomberman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Server Program, die klasse Thread erweitert.
 * @author gruppe 31 -Bomberman
 *
 */
public class BombermanGameServer extends Thread {

	public Spiel spiel;
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	BombermanSocketMessage message;
    /**
     * Konstruktor
     * @param spiel
     */
	BombermanGameServer(Spiel spiel) {
		this.spiel = spiel;
	}
    /**
     * Methode von Thread implementiert.
     * Hier wird auf Client gewartet und dann mit Client verbunden.
     */
	public void run() {
		try {
			//1. creating a server socket
			providerSocket = new ServerSocket(2004, 10);
			//2. Wait for connection
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			//4. The two parts communicate via the input and output streams 
			do {
				try {
					spiel.spiel = (Spiel) in.readObject();
					spiel.refresh();
				} catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
			} while(true);
		} catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try {
				if (providerSocket != null) {
					providerSocket.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param msg
	 */
	public void sendMessage(Spiel msg) {
		try {
			out.writeObject(msg);
			out.reset();
		} catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	/**
	 * Closes the socket server properly (all IO connections are closed).
	 */
	public void close() {
		try {
			
			if (providerSocket != null) {
				providerSocket.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		} catch(IOException ioException) {
			System.out.println("IOException caught while closing socket server.");
			ioException.printStackTrace();
		}
	}
}

