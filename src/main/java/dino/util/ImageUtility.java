package dino.util;

import java.awt.image.BufferedImage;

public class ImageUtility {

    public static boolean isAnyPixelFoundAtTop(BufferedImage image, int yAxis) {
        return hasGrayPixel(image, yAxis);
    }

    public static boolean hasGrayPixel(BufferedImage image, int yAxis) {
        int xAxis = image.getHeight() - 1;
        int pixel = image.getRGB(xAxis, yAxis) & 0xFF;
        return pixel < Constants.GRAY_SCALE_PIXEL_COLOR;
    }
}
