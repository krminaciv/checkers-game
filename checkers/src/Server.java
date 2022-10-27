import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

     static ServerSocket serverSocket;
	static Socket socket;
	static BufferedReader in;
	static PrintWriter out;

	static int numOfPlayers = 0;
	static Player player1, player2;

     public static void main(String[] args) {
          
          try {

			serverSocket = new ServerSocket(7777);
			System.out.println("server starting...");
			
			while(numOfPlayers <= 2){
				numOfPlayers++;

				socket = serverSocket.accept();
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				Scanner scanner = new Scanner(System.in);

				if(numOfPlayers == 1){
					System.err.println("player 1 connected.");
					player1 = new Player(1);
					out.println("conn-1");
					out.flush();
				} else if(numOfPlayers == 2){
					System.err.println("player 2 is connected.");
					player2 = new Player(2);
					out.println("conn-2");
					out.flush();
				}
			}
			
			
			System.out.println("server started...");
			
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Scanner scanner = new Scanner(System.in);


               //slanje podataka
			Thread sender = new Thread(new Runnable() {

				String msg;
				@Override
				public void run() {
                         msg = scanner.nextLine();
					while(true) {

						out.println(msg);
						out.flush();
						msg = scanner.nextLine();
						
					}
					
				}
				
			});
			
			sender.start();



               //primanje podataka
			Thread receiver = new Thread(new Runnable() {

				String msg;
				@Override
				public void run() {
					
					try {
						
						msg = in.readLine();
						while(msg != null) {
							System.out.println("Client: " + msg);
							msg = in.readLine();
						}
						out.close();
						socket.close();
						serverSocket.close();
						
					} catch(IOException e) {
						System.out.println(e);
					}
					
				}
				
			});
			
			receiver.start();


          } catch (IOException e) {
			System.out.println(e);
		}

     }
     
}
