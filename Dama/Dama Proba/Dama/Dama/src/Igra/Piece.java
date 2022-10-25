package Igra;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

    private PieceType type;
    private boolean playing;

    private double mouseX, mouseY;
    private double oldX, oldY;

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;
        if(this.getType() == PieceType.WHITE)
            this.playing = true;
        else    
            this.playing = false;

        move(x, y);

        Ellipse bg = new Ellipse(CheckersApp.TILE_SIZE * 0.3125, CheckersApp.TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(CheckersApp.TILE_SIZE * 0.03);

        bg.setTranslateX((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * 0.26 * 2) / 2 + CheckersApp.TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(CheckersApp.TILE_SIZE * 0.3125, CheckersApp.TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.RED
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(CheckersApp.TILE_SIZE * 0.03);

        ellipse.setTranslateX((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((CheckersApp.TILE_SIZE - CheckersApp.TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);
        // if(this.isPlaying()){
            setOnMousePressed(e -> {
                mouseX = e.getSceneX();
                mouseY = e.getSceneY();
            });

            setOnMouseDragged(e -> {
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            });
        // }
    }

    public void move(int x, int y) {
        oldX = x * CheckersApp.TILE_SIZE;
        oldY = y * CheckersApp.TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}