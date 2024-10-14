package dino.util;

import java.awt.image.BufferedImage;

public class ImageUtility {

    private final BufferedImage image;

    public ImageUtility(BufferedImage image) {
        this.image = image;
    }

    public boolean isAnyPixelFoundAtTop(int i) {
        return isGrayPixel(i, 0);
    }

    public boolean isGrayPixel(int xAxis, int yAxisBottomUp) {
        int pixel = image.getRGB(xAxis, yAxisBottomUp) & 0xFF;
        return pixel < Constants.GRAY_SCALE_PIXEL_COLOR;
    }
}
