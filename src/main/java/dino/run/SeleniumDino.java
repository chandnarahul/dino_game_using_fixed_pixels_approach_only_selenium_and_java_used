package dino.run;

import dino.DinoConstants;
import dino.sensor.DinoImageSensorInteraction;
import dino.sensor.DinoSensor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeleniumDino {
    private final WebDriver webDriver;
    private final List<DinoSensor> imageBuffers = new ArrayList<>(DinoConstants.MAX_COMMON_IMAGES);
    private Date gameStartTime;

    public SeleniumDino(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private BufferedImage takeScreenshot() throws IOException {
        WebElement element1 = webDriver.findElement(By.className("runner-canvas"));
        Rectangle rect = element1.getRect();
        return ImageIO.read(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE)).getSubimage(rect.x, rect.y, rect.width, rect.height);
    }

    public int run() {
        try {
            startGame();
            for (; ; ) {
                processImageAndTakeAction();
            }
        } catch (Throwable e) {
        } finally {
            webDriver.quit();
            return (int) (new Date().getTime() - gameStartTime.getTime());
        }
    }

    private void startGame() throws InterruptedException {
        jump();
        Thread.sleep(2000);
        gameStartTime = new Date();
    }

    private void processImageAndTakeAction() throws IOException, AWTException {
        switch (new DinoImageSensorInteraction(takeScreenshot(), imageBuffers).performAction()) {
            case CLOSER_TO_THE_GROUND:
                jump();
                break;
            case IN_THE_SKY:
                duckFromObjectInTheSky();
                break;
        }
    }

    private void jump() {
        webDriver.findElement(By.tagName("body")).sendKeys(Keys.UP);
    }

    private void duckFromObjectInTheSky() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(500);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }
}
