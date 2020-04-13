package dino.sensor.object;

import dino.DinoConstants;
import dino.sensor.image.PixelUtility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ObjectWidth {
    private final int objectXAxisPoint;
    private final BufferedImage image;

    public ObjectWidth(int objectXAxisPoint, BufferedImage image) {
        this.objectXAxisPoint = objectXAxisPoint;
        this.image = image;
    }

    public int determineWidthOfTheGroundObject() {
        int pixelNotFound = 0;
        int lastPixelFound = 0;
        for (int i = this.objectXAxisPoint; i < image.getWidth(); i++) {
            if (new PixelUtility(image).isGrayPixel(i, image.getHeight() - 1)) {
                lastPixelFound = i;
                pixelNotFound = 0;
            } else {
                pixelNotFound++;
            }
            if (pixelNotFound > DinoConstants.PIXELS_BUFFER) {
                return calculateObjectWidth(lastPixelFound);
            }
        }
        return calculateObjectWidth(lastPixelFound);
    }

    private int calculateObjectWidth(int lastPixelFound) {
        extractObjectAsImage(lastPixelFound);
        return lastPixelFound - this.objectXAxisPoint;
    }

    private void extractObjectAsImage(int lastPixelFound) {
        try {
            if (DinoConstants.IN_DEBUG_MODE && lastPixelFound != 0) {
                ImageIO.write(image.getSubimage(this.objectXAxisPoint, 0, lastPixelFound - this.objectXAxisPoint, 30), "png", new File("images/block.png"));
            }
        } catch (Exception e) {

        }
    }
}
