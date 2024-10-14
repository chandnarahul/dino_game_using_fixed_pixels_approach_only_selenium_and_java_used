package dino.image.processor;

import dino.image.processor.object.ObstacleDimension;
import dino.image.processor.object.ObstacleLocation;
import dino.util.Constants;
import dino.util.ImageUtility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class GameCanvas {
    private final BufferedImage image;
    private ObstacleLocation obstacleLocation = ObstacleLocation.NO_OBJECT_DETECTED;
    private int groundObjectWidth = 0;
    private int objectXAxisPoint;

    public GameCanvas(BufferedImage image) {
        this.image = removeDinoFloorAndSkyFromImage(image);
        this.findObject();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        try {
            return image.getSubimage(Constants.DINO_X_AXIS, Constants.DINO_Y_AXIS, image.getWidth() - Constants.DINO_X_AXIS, 35);
        } catch (Exception invalidDimensions) {
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
        for (int axis = 0; axis < image.getWidth(); axis++) {
            if (ImageUtility.isAnyPixelFoundAtTop(image, axis)) {
                firstPixelFoundAt = setFirstPixelValue(firstPixelFoundAt, axis);
                this.obstacleLocation = ObstacleLocation.IN_THE_SKY;
            }
            if (ImageUtility.hasGrayPixel(image, axis)) {
                firstPixelFoundAt = setFirstPixelValue(firstPixelFoundAt, axis);
                this.obstacleLocation = ObstacleLocation.CLOSER_TO_THE_GROUND;
                this.groundObjectWidth = new ObstacleDimension(this.objectXAxisPoint, this.image).determineWidthOfTheGroundObject();
            }
            if (firstPixelFoundAt != Constants.PIXEL_NOT_FOUND && (axis - firstPixelFoundAt) > Constants.PIXELS_BUFFER) {
                break;
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

    public ObstacleLocation objectLocation() {
        return this.obstacleLocation;
    }

    public boolean isLongGroundObject() {
        return groundObjectWidth >= Constants.CLUSTERED_CACTUS_SIZE;
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

    public ObstacleLocation performGroundAction() {
        return distanceFromObject() < Constants.JUMP_SAFE_DISTANCE ? ObstacleLocation.CLOSER_TO_THE_GROUND : ObstacleLocation.NO_OBJECT_DETECTED;
    }

    public ObstacleLocation performFlyingAction() {
        return distanceFromObject() <= Constants.JUMP_SAFE_DISTANCE ? ObstacleLocation.IN_THE_SKY : ObstacleLocation.NO_OBJECT_DETECTED;
    }

    public ObstacleLocation getNextObstacleLocation() {
        if (objectLocation() == ObstacleLocation.CLOSER_TO_THE_GROUND) {
            return performGroundAction();
        } else if (objectLocation() == ObstacleLocation.IN_THE_SKY) {
            return performFlyingAction();
        } else {
            return ObstacleLocation.NO_OBJECT_DETECTED;
        }
    }
}
