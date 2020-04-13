package integration.tests;

import dino.run.SeleniumDino;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RunChromeForManualExecution {

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("chrome://dino");
        webDriver.manage().window().setSize(new Dimension(500, 450));
        Thread.sleep(1000);
        new SeleniumDino(webDriver).run();
    }
}