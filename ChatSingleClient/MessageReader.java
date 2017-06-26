import java.net.*;
import java.io.*;

/**
 * Слушатель сообщений для сервера или клиента.
 *
 * @author Sergey Kulikov
 * @version 1.0 on date 26.06.2017
 */
 
public class MessageReader extends Thread {
	BufferedReader reader;
	Socket socket;
	String name;
	long count;
		
	public MessageReader(Socket clientSocket, String name) {
		socket = clientSocket;
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.name = name;
			this.count = 0;
		} catch (Exception ex) { 
			ex.printStackTrace(); 
		}
	}
		
	@Override
	public void run() {
		String message;
		try {
			while ((message = reader.readLine()) != null){
				System.out.println("Message from "+name+" ("+(++count)+"): " + message);
			}				
		} catch (Exception ex) {
			System.out.println("Disconnected from the "+name+".");
		}
	}
}
