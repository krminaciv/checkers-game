import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

     private int pieceType;  //1 za bijelu, 0 za crnu
     private boolean playing;
     private double oldX, oldY;
     
     public Piece(int pieceType, int x, int y){

          this.pieceType = pieceType;
          playing = true ? pieceType == 1 : pieceType == 0;

          move(x,y);

          Ellipse bg = new Ellipse(Client.TILE_SIZE * 0.3125, Client.TILE_SIZE * 0.26);
          bg.setFill(Color.BLACK);
          bg.setStroke(Color.BLACK);
          bg.setStrokeWidth(Client.TILE_SIZE * 0.03);
          bg.setTranslateX((Client.TILE_SIZE - Client.TILE_SIZE * 0.3125 * 2) / 2);
          bg.setTranslateY((Client.TILE_SIZE - Client.TILE_SIZE * 0.26 * 2) / 2 + Client.TILE_SIZE * 0.07);

          Ellipse ellipse = new Ellipse(Client.TILE_SIZE * 0.3125, Client.TILE_SIZE * 0.26);
          ellipse.setFill(pieceType == 1 ? Color.valueOf("#FFFFFF") : Color.valueOf("#181818"));
          ellipse.setStroke(Color.BLACK);
          ellipse.setStrokeWidth(Client.TILE_SIZE * 0.03);
          ellipse.setTranslateX((Client.TILE_SIZE - Client.TILE_SIZE * 0.3125 * 2) / 2);
          ellipse.setTranslateY((Client.TILE_SIZE - Client.TILE_SIZE * 0.26 * 2) / 2);

          getChildren().addAll(bg,ellipse);

     }

     public void move(int x, int y) {
          oldX = x * Client.TILE_SIZE;
          oldY = y * Client.TILE_SIZE;
          relocate(oldX, oldY);
     }

}
