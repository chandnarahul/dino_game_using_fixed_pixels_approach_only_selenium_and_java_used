package dino.image.processor.object;

import dino.util.Constants;
import dino.util.ImageUtility;

import java.awt.image.BufferedImage;

public class ObstacleDimension {
    private final int objectXAxisPoint;
    private final BufferedImage image;

    public ObstacleDimension(int objectXAxisPoint, BufferedImage image) {
        this.objectXAxisPoint = objectXAxisPoint;
        this.image = image;
    }

    public int determineWidthOfTheGroundObject() {
        int pixelNotFound = 0;
        int lastPixelFound = 0;
        for (int i = this.objectXAxisPoint; i < image.getWidth(); i++) {
            if (new ImageUtility(image).isGrayPixel(i, image.getHeight() - 1)) {
                lastPixelFound = i;
                pixelNotFound = 0;
            } else {
                pixelNotFound++;
            }
            if (pixelNotFound > Constants.PIXELS_BUFFER) {
                return calculateObjectWidth(lastPixelFound);
            }
        }
        return calculateObjectWidth(lastPixelFound);
    }

    private int calculateObjectWidth(int lastPixelFound) {
        return lastPixelFound - this.objectXAxisPoint;
    }
}