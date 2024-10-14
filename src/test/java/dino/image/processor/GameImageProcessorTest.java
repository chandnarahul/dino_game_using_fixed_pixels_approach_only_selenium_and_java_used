package dino.image.processor;

import dino.image.processor.GameImageProcessor;
import dino.image.processor.GameCanvas;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class GameImageProcessorTest {

    @Test(expected = RuntimeException.class)
    public void should_identify_that_game_is_over() throws IOException {
        ArrayList<GameCanvas> imageBuffers = new ArrayList<>();
        for (int i = 0; i < GameImageProcessor.MAX_COMMON_IMAGES; i++) {
            new GameImageProcessor(ImageIO.read(GameCanvasTest.class.getResourceAsStream("/game_over.png")), imageBuffers);
        }
    }
}