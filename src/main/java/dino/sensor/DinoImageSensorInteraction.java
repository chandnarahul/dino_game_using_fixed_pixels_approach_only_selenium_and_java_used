package dino.sensor;

import dino.sensor.exception.GameOverException;
import dino.sensor.object.ObjectLocation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.List;

public class DinoImageSensorInteraction {

    public static final int MAX_COMMON_IMAGES = 5;
    private final DinoSensor dinoSensor;
    private final List<DinoSensor> imageBuffers;

    public DinoImageSensorInteraction(BufferedImage image, List<DinoSensor> imageBuffers) {
        this.dinoSensor = new DinoSensor(image);
        this.imageBuffers = imageBuffers;
        imageBuffers.add(dinoSensor);
        stopExecutionIfNoNewImageIsReceived();
    }

    public ObjectLocation performAction() {
        return dinoSensor.performAction();
    }

    private void stopExecutionIfNoNewImageIsReceived() {
        if (imageBuffers.size() == MAX_COMMON_IMAGES) {
            throw new GameOverException("game over");
        } else if (ifUniqueImage()) {
            imageBuffers.clear();
        }
    }

    private boolean ifUniqueImage() {
        for (DinoSensor previous : imageBuffers) {
            DataBufferByte dataBufferByte = previous.imageDataBuffer();
            for (int dataBanks = 0; dataBanks < dataBufferByte.getNumBanks(); dataBanks++) {
                if (Arrays.equals(dinoSensor.imageDataBuffer().getData(dataBanks), dataBufferByte.getData(dataBanks))) {
                    return Boolean.FALSE;
                } else {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.TRUE;
    }
}
