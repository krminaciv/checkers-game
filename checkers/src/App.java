import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    //TEST KLASA ZA GUI

    private Tile[][] board = new Tile[8][8];
    public static final int TILE_SIZE = 50;
    private Group tileGroup = new Group();

    public static void main(String[] args) throws Exception {

        launch(args);

    }

    // test gui

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Pane root = new Pane();
        root.setPrefSize(8*TILE_SIZE, 8*TILE_SIZE);
        root.getChildren().addAll(tileGroup);

        for(int y = 0 ; y < 8; y++){
            for(int x = 0 ; x < 8 ; x++){

                Tile tile = new Tile((x+y)%2 == 0, x,y);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);

                //dodati Piece ako trb...

            }
        }


        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Klijent");
		primaryStage.show();
        
    }
}
