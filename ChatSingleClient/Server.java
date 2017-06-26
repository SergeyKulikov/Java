import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Сервер. Передает и принимает сообщения от одного клиента.
 *
 * @author Sergey Kulikov
 * @version 1.0 on date 26.06.2017
 */

class Server {
	final int PORT = 2048;
	
	ServerSocket server;
	Socket socket;
	PrintWriter	writer;
	Scanner sc;
	
	public static void main(String[] args) {
		new Server();
	}

	Server() {
		try {
			server = new ServerSocket(PORT);
			sc = new Scanner(System.in);
	
			System.out.println("Server has started");
			socket = server.accept();
			
			new MessageReader(socket, "client").start(); // слушаем клиента
			while (true) { // Вводим руками данные на сервере и отправляем клиенту
				writer = new PrintWriter(socket.getOutputStream());
				writer.println(sc.nextLine());
				writer.flush();
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}		
}