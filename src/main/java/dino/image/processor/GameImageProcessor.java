package dino.image.processor;

import dino.image.processor.exception.GameOverException;
import dino.image.processor.object.ObstacleLocation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.List;

public class GameImageProcessor {

    public static final int MAX_COMMON_IMAGES = 5;
    private final GameCanvas gameCanvas;
    private final List<GameCanvas> imageBuffers;

    public GameImageProcessor(BufferedImage image, List<GameCanvas> imageBuffers) {
        this.gameCanvas = new GameCanvas(image);
        this.imageBuffers = imageBuffers;
        imageBuffers.add(gameCanvas);
        stopExecutionIfNoNewImageIsReceived();
    }

    public ObstacleLocation getNextObstacleLocation() {
        return gameCanvas.getNextObstacleLocation();
    }

    private void stopExecutionIfNoNewImageIsReceived() {
        if (imageBuffers.size() == MAX_COMMON_IMAGES) {
            throw new GameOverException("game over");
        } else if (ifUniqueImage()) {
            imageBuffers.clear();
        }
    }

    private boolean ifUniqueImage() {
        for (GameCanvas previous : imageBuffers) {
            DataBufferByte dataBufferByte = previous.imageDataBuffer();
            if (dataBufferByte.getNumBanks() > 0) {
                return !Arrays.equals(gameCanvas.imageDataBuffer().getData(0), dataBufferByte.getData(0));
            }
        }
        return Boolean.TRUE;
    }
}
