package dino.run;

import dino.image.processor.GameCanvas;
import dino.image.processor.GameImageProcessor;
import dino.util.Constants;
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
    private final List<GameCanvas> imageBuffers = new ArrayList<>(Constants.MAX_COMMON_IMAGES);
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
            while (true) {
                processImageAndTakeAction();
            }
        } catch (Throwable ignored) {
        } finally {
            webDriver.quit();
        }
        return (int) (new Date().getTime() - gameStartTime.getTime());
    }

    private void startGame() throws InterruptedException {
        jump();
        Thread.sleep(2000);
        gameStartTime = new Date();
    }

    private void processImageAndTakeAction() throws Exception {
        switch (new GameImageProcessor(takeScreenshot(), imageBuffers).getNextObstacleLocation()) {
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
