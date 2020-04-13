package dino.sensor.exception;

public class GameOverException extends RuntimeException {
    public GameOverException(String game_over) {
        super(game_over);
    }
}
