import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Client extends Application {

     static Socket socket;
	static BufferedReader in;
	static PrintWriter out;

	static int playerID = -1;
	static Player me, opponent;

	//GUI
	private Tile[][] board = new Tile[8][8];
     public static final int TILE_SIZE = 50;
     private Group tileGroup = new Group();
	private Group pieceGroup = new Group();

     public static void main(String[] args) {
		
          try {

               socket = new Socket("localhost", 7777);
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
							System.out.println("Server: " + msg);
							
							if(msg.startsWith("conn")){
								String temp[] = msg.split("-");
								//System.out.println("moj player id je " + temp[1]);
								playerID = Integer.parseInt(temp[1]);
								me = new Player(Integer.parseInt(temp[1]));
								if(me.playerID == 1)
									opponent = new Player(2);
								else if(me.playerID == 2)
									opponent = new Player(1);
							}



							msg = in.readLine();
						}
						out.close();
						socket.close();
						
					} catch(IOException e) {
						System.out.println(e);
					}
					
				}
				
			});
			
			receiver.start();

          } catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		

		launch(args);
          
     }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Pane root = new Pane();
       	root.setPrefSize(8*TILE_SIZE, 8*TILE_SIZE);
        	root.getChildren().addAll(tileGroup, pieceGroup);

        	for(int y = 0 ; y < 8; y++){
            	for(int x = 0 ; x < 8 ; x++){

				Tile tile = new Tile((x+y)%2 == 0, x,y);
				board[x][y] = tile;
				tileGroup.getChildren().add(tile);

				Piece piece = null;

				if(y <= 2 && (x+y)%2 != 0){
					piece = new Piece(0,x,y);
				}
				if(y >= 5 && (x+y)%2 != 0){
					piece = new Piece(1,x,y);
				}
				if(piece != null){
					tile.setPiece(piece);
					pieceGroup.getChildren().add(piece);
				}

            	}
        	}


        	Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Klijent " + me.playerID);
		primaryStage.show();
		
	}


	
     
}
