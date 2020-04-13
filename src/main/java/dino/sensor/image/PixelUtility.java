package dino.sensor.image;

import dino.DinoConstants;

import java.awt.image.BufferedImage;

public class PixelUtility {

    private final BufferedImage image;

    public PixelUtility(BufferedImage image) {
        this.image = image;
    }

    public boolean isAnyPixelFoundAtTop(int i) {
        return isGrayPixel(i, 0);
    }

    public boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        int pixel = image.getRGB(xAxis, yAxisBottomUp) & 0xFF;
        return pixel < DinoConstants.GRAY_SCALE_PIXEL_COLOR;
    }
}
