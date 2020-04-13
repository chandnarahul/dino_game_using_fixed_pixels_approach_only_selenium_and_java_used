package dino.sensor;

import dino.DinoConstants;
import dino.sensor.image.PixelUtility;
import dino.sensor.object.ObjectLocation;
import dino.sensor.object.ObjectWidth;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class DinoSensor {
    private ObjectLocation objectLocation = ObjectLocation.NO_OBJECT_DETECTED;
    private int groundObjectWidth = 0;
    private final BufferedImage image;
    private int objectXAxisPoint;

    public DinoSensor(BufferedImage image) {
        if (image.getWidth() == DinoConstants.GAME_CANVAS_WIDTH) {
            this.image = removeDinoFloorAndSkyFromImage(image);
        } else {
            this.image = image;
        }
        this.findObject();
    }

    private BufferedImage removeDinoFloorAndSkyFromImage(BufferedImage image) {
        return image.getSubimage(DinoConstants.DINO_X_AXIS, DinoConstants.DINO_Y_AXIS, image.getWidth() / 2, image.getHeight() - 291);
    }

    public DataBufferByte imageDataBuffer() {
        return (DataBufferByte) image.getRaster().getDataBuffer();
    }


    private boolean isAnyPixelFoundAtBottomFrom(int currentXAxisLocation) {
        for (int traverseYAxis = image.getHeight() - 1; traverseYAxis >= 0; traverseYAxis--) {
            if (new PixelUtility(image).isGrayPixel(currentXAxisLocation, traverseYAxis)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    private void findObject() {
        int firstPixelFoundAt = DinoConstants.PIXEL_NOT_FOUND;
        for (int X_AXIS = 0; X_AXIS < image.getWidth(); X_AXIS++) {
            if (new PixelUtility(image).isAnyPixelFoundAtTop(X_AXIS)) {
                firstPixelFoundAt = setFirstPixelValue(firstPixelFoundAt, X_AXIS);
                this.objectLocation = ObjectLocation.IN_THE_SKY;
            }
            if (isAnyPixelFoundAtBottomFrom(X_AXIS)) {
                firstPixelFoundAt = setFirstPixelValue(firstPixelFoundAt, X_AXIS);
                this.objectLocation = ObjectLocation.CLOSER_TO_THE_GROUND;
                this.groundObjectWidth = new ObjectWidth(this.objectXAxisPoint, this.image).determineWidthOfTheGroundObject();
            }
            if (firstPixelFoundAt != DinoConstants.PIXEL_NOT_FOUND && (X_AXIS - firstPixelFoundAt) > DinoConstants.PIXELS_BUFFER) {
                break;
            }
        }
    }

    private int setFirstPixelValue(int firstPixelFoundAt, int X_AXIS) {
        if (firstPixelFoundAt == DinoConstants.PIXEL_NOT_FOUND) {
            this.objectXAxisPoint = X_AXIS;
            return X_AXIS;
        } else {
            return firstPixelFoundAt;
        }
    }

    public ObjectLocation objectLocation() {
        return this.objectLocation;
    }

    protected boolean isLongGroundObject() {
        return groundObjectWidth >= DinoConstants.CLUSTERED_CACTUS_SIZE;
    }

    public int getGroundObjectWidth() {
        return groundObjectWidth;
    }

    public int distanceFromObject() {
        int firstPixelWasFoundAt = this.objectXAxisPoint;
        if (isLongGroundObject()) {
            return firstPixelWasFoundAt + getGroundObjectWidth() / 2;
        } else {
            return firstPixelWasFoundAt;
        }
    }

    public ObjectLocation performGroundAction() {
        return distanceFromObject() < DinoConstants.JUMP_SAFE_DISTANCE ? ObjectLocation.CLOSER_TO_THE_GROUND : ObjectLocation.NO_OBJECT_DETECTED;
    }

    public ObjectLocation performFlyingAction() {
        return distanceFromObject() <= DinoConstants.JUMP_SAFE_DISTANCE ? ObjectLocation.IN_THE_SKY : ObjectLocation.NO_OBJECT_DETECTED;
    }

    public ObjectLocation performAction() {
        if (objectLocation() == ObjectLocation.CLOSER_TO_THE_GROUND) {
            return performGroundAction();
        } else if (objectLocation() == ObjectLocation.IN_THE_SKY) {
            return performFlyingAction();
        } else {
            return ObjectLocation.NO_OBJECT_DETECTED;
        }
    }
}
