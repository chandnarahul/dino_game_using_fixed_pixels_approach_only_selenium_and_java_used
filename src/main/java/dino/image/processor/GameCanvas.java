package dino.image.processor;

import dino.image.processor.object.ObstacleAction;
import dino.image.processor.object.ObstacleDimension;
import dino.image.processor.object.ObstacleType;
import dino.util.Constants;
import dino.util.ImageUtility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class GameCanvas {
    private final BufferedImage image;
    private ObstacleType obstacleType = ObstacleType.NONE;
    private int groundObjectWidth = 0;
    private int objectXAxisPoint;

    public GameCanvas(BufferedImage image) {
        this.image = removeDinoFloorAndSkyFromImage(image);
        this.findObject();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        try {
            return image.getSubimage(Constants.DINO_X_AXIS, Constants.DINO_Y_AXIS, image.getWidth() - Constants.DINO_X_AXIS, 35);
        } catch (Exception ignored) {
            return image;
        }
    }

    public DataBufferByte imageDataBuffer() {
        return (DataBufferByte) image.getRaster().getDataBuffer();
    }


    private boolean isAnyPixelFoundAtBottomFrom(int currentXAxisLocation) {
        int traverseYAxis = image.getHeight() - 1;
        return new ImageUtility(image).isGrayPixel(currentXAxisLocation, traverseYAxis);
    }

    private void findObject() {
        int firstPixelFoundAt = Constants.PIXEL_NOT_FOUND;
        for (int X_AXIS = 0; X_AXIS < image.getWidth(); X_AXIS++) {
            if (firstPixelFoundAt != Constants.PIXEL_NOT_FOUND && (X_AXIS - firstPixelFoundAt) > Constants.MINIMUM_GROUP_OF_PIXELS) {
                break;
            } else if (isAnyPixelFoundAtBottomFrom(X_AXIS)) {
                firstPixelFoundAt = setFirstPixelValue(firstPixelFoundAt, X_AXIS);
                this.obstacleType = ObstacleType.CACTUS;
                this.groundObjectWidth = new ObstacleDimension(this.objectXAxisPoint, this.image).determineWidthOfTheGroundObject();
            } else if (new ImageUtility(image).isAnyPixelFoundAtTop(X_AXIS)) {
                firstPixelFoundAt = setFirstPixelValue(firstPixelFoundAt, X_AXIS);
                this.obstacleType = ObstacleType.BIRD;
            }
        }
    }

    private int setFirstPixelValue(int firstPixelFoundAt, int X_AXIS) {
        if (firstPixelFoundAt == Constants.PIXEL_NOT_FOUND) {
            this.objectXAxisPoint = X_AXIS;
            return X_AXIS;
        } else {
            return firstPixelFoundAt;
        }
    }

    public ObstacleType obstacleType() {
        return this.obstacleType;
    }

    public boolean isLongGroundObject() {
        return groundObjectWidth >= Constants.CLUSTERED_CACTUS_WIDTH;
    }

    public int getGroundObjectWidth() {
        return groundObjectWidth;
    }

    public int distanceFromObject() {
        int firstPixelWasFoundAt = this.objectXAxisPoint;
        if (isLongGroundObject()) {
            return firstPixelWasFoundAt + getGroundObjectWidth();
        } else {
            return firstPixelWasFoundAt;
        }
    }

    private boolean isObstacleClose() {
        return distanceFromObject() <= Constants.JUMP_SAFE_DISTANCE;
    }

    public ObstacleAction getNextObstacleAction() {
        if (obstacleType() == ObstacleType.CACTUS && isObstacleClose()) {
            return ObstacleAction.JUMP;
        } else if (obstacleType() == ObstacleType.BIRD && isObstacleClose()) {
            return ObstacleAction.LOWER_THE_HEAD;
        } else {
            return ObstacleAction.NONE;
        }
    }
}
