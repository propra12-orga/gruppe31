
<<<<<<< HEAD
package gruppe31.bomberman;
=======
//package gruppe31.bomberman;
>>>>>>> 8c42c91f2ec50a5915702a03c94232c80e1fb40c

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BombermanGameServer extends Thread {

	public Spiel spiel;
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	BombermanSocketMessage message;
	
	BombermanGameServer(Spiel spiel) {
		this.spiel = spiel;
	}

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
			//sendMessage("Connection successful");
			
			//4. The two parts communicate via the input and output streams
			do {
				try {
					message = (BombermanSocketMessage) in.readObject();
					System.out.println("client>" + message);
					spiel.refresh(message);
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
			} while(!message.equals("bye"));
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
//	void sendMessage(String msg)
//	{
//		try{
//			out.writeObject(msg);
//			out.flush();
//			System.out.println("server>" + msg);
//		}
//		catch(IOException ioException){
//			ioException.printStackTrace();
//		}
//	}
	
	void sendMessage(BombermanSocketMessage msg) {
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
	
//	public static void main(String args[])
//	{
//		BombermanGameServer server = new BombermanGameServer();
//		while(true){
//			server.run();
//		}
//	}
}

