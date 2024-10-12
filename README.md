# Google Chrome Offline Mode Dino Game

## Overview
This code plays the Google Chrome offline mode Dino game.

## Current Limitations
### Issues Identified:
1. **Screenshot Accuracy:** The code works well on Debian 10, but the screenshot output is not correct on my MacBook Pro. This seems to be a Selenium driver issue, but I'm not entirely sure.
2. **Day Mode Only:** Currently, the game only runs in day mode. This can be easily fixed by averaging half the pixels in a row to determine whether it is morning or night. I plan to address this in my next iteration.
3. **Performance Delay:** Taking screenshots takes around 40 to 90 milliseconds, while image processing is under 2 milliseconds. If I implement the night mode fix, the game will likely fail because the Dino's speed will increase. By the time a decision is made, the Dino may have already hit an obstacle due to the screenshot delay.

## Algorithm
1. The code uses Selenium to capture a screenshot.
2. It employs Java's image buffer to process the screenshot and identify objects on the ground and in the air.
3. It determines the distance to the objects.
4. It measures the width of the objects.
5. The code then decides when to jump or duck.

## Getting Started
To see the code in action using the Selenium driver, follow these steps:

1. **Install Google Chrome:** Ensure you have Google Chrome installed on your machine.

2. **Download Selenium Chrome Driver:**
    - Download the appropriate version from [ChromeDriver Downloads](https://chromedriver.chromium.org/downloads).
    - You will need to know the version of Google Chrome you are using. To find it, click the three dots in the top right corner, hover over "Help," and click "About Google Chrome" to view your version.

3. **Edit `RunChromeForManualExecution.java`:**
    - Locate the file in your integration tests.

4. **Update System Properties:**
    - Modify the `System.setProperty` line in `RunChromeForManualExecution.java` to match your OS and the path where you saved the driver from Step 2:
        - **Linux:** `System.setProperty("webdriver.chrome.driver", "YOUR_DRIVER_FULL_PATH");`
        - **macOS:** `System.setProperty("webdriver.chrome.driver", "YOUR_DRIVER_FULL_PATH");`
        - **Windows:** `System.setProperty("webdriver.chrome.driver", "YOUR_DRIVER_FULL_PATH");`

5. **Run the Program:**
    - If you are using IntelliJ, simply click the green pointer next to the `public static void main` method to run the program.

## Test Cases
All image processing test cases are written using JUnit and can be found in the `dino.sensor` package.

## Configuration
Game configurations, such as the conditions for jumping, can be updated in the `DinoConstants.java` class.