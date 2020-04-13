package dino.sensor;

import dino.sensor.object.ObjectLocation;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DinoSensorTest {

    @Test
    public void should_calculate_ground_object_width() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/cluster.png")));
        assertTrue(dinoSensor.isLongGroundObject());
    }

    @Test
    public void should_calculate_ground_object_at_the_end_width() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/cluster_at_end.png")));
        assertTrue(dinoSensor.isLongGroundObject());
    }


    @Test(expected = AssertionError.class)
    public void should_not_calculate_flying_object_width() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/flying.png")));
        assertTrue(dinoSensor.isLongGroundObject());
    }

    @Test
    public void should_identify_flyingObject() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/flying.png")));
        assertEquals(ObjectLocation.IN_THE_SKY, dinoSensor.objectLocation());
    }

    @Test
    public void should_return_flyingObject_distance() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/flying.png")));
        assertTrue(dinoSensor.distanceFromObject() > 0);
    }

    @Test(expected = AssertionError.class)
    public void should_fail_since_this_is_not_a_flyingObject() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/duck_bug.png")));
        assertEquals(ObjectLocation.IN_THE_SKY, dinoSensor.objectLocation());
    }

    @Test
    public void should_identify_mini_object_closer_to_the_ground_1() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/mini_objects.png")));
        assertEquals(ObjectLocation.CLOSER_TO_THE_GROUND, dinoSensor.objectLocation());
    }

    @Test
    public void should_identify_mini_object_closer_to_the_ground_2() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/game67.png")));
        assertEquals(ObjectLocation.CLOSER_TO_THE_GROUND, dinoSensor.objectLocation());
    }

    @Test
    public void sensor_distance_should_reduce_as_object_moves_closer_to_dino() throws IOException {
        int distance = Integer.MAX_VALUE;
        for (int i = 65; i <= 74; i++) {
            String pathname = "src/test/resources/movingobject/game" + i + ".png";
            DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File(pathname)));
            assertTrue(dinoSensor.distanceFromObject() < distance);
            distance = dinoSensor.distanceFromObject();
        }
    }

    @Test
    public void should_return_groundObject_distance() throws IOException {
        DinoSensor dinoSensor = new DinoSensor(ImageIO.read(new File("src/test/resources/ground_object.png")));
        assertTrue(dinoSensor.distanceFromObject() > 0);
    }
}