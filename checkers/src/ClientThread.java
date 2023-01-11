
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket client;
	private String username;
	private Server server;
	private BufferedReader input;
	private PrintWriter output;

	public ClientThread(Socket client, Server server) {
		this.client = client;
		this.server = server;
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream())); // TODO
			output = new PrintWriter(client.getOutputStream(), true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String message;
			while (true) {
				message = input.readLine();
				if(message.equals("Konektovan"))
					this.sendMessage(server.getPitanje());
				else{
					if(message.equals(server.getOdgovor()))
						this.sendMessage("Tacno ste odgovorili");
					else
						this.sendMessage("Pogresan odgovor");
				}
			}
		} catch (IOException e) {
			closeAll();
		}
	}

	public void sendMessage(String message) {
		output.println(message);
	}

	public Socket getSocket() {
		return client;
	}

	public String getUsername() {
		return username;
	}

	public void closeAll() {
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		interrupt();
	}
}