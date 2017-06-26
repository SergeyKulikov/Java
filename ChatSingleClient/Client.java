import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Клиент. Принимает и передает сообщения серверу.
 *
 * @author Sergey Kulikov
 * @version 1.0 on date 26.06.2017
 */
 
class Client {
	final int PORT = 2048;

	Socket socket;
	PrintWriter	writer;
	Scanner sc;
	
	public static void main(String[] args) {
		new Client();
	}

	Client() {
		try {
			socket = new Socket("127.0.0.1", PORT);
			System.out.println("Client has started");
			new MessageReader(socket, "server").start(); // слушаем сервер
			
			while (true) { // Вводим руками данные на клиенте и отправляем серверу
				writer = new PrintWriter(socket.getOutputStream());
				sc = new Scanner(System.in);
				
				writer.println(sc.nextLine());
				writer.flush();
			}
			
		} catch (IOException ex) {
			System.out.println("There is no connection to server");
			System.exit(-1);
		}
	}			
}