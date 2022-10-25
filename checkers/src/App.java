import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,500,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Klijent");
		primaryStage.show();
        
    }
}
