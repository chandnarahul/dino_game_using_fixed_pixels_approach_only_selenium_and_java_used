# This code plays the google chrome offline mode dino game - First Try Using Pixels Only

# There is one problem however, this code is screen resolution dependent, if your laptop is old and the screenshot image.width() is 500 then it will work.

## Algorithm:
#### The code uses selenium to take a screenshot
#### Uses the java image buffer to process the screenshot and identify objects on ground and air
#### Identifies how far the object is
#### Identifies the width of the object
#### Code then decides when to jump or duck

## Trying it out:
#### If you want to see the code in action by running Selenium driver then :
#### 1. Make sure you have Google Chrome installed

#### 2. Make sure you have Selenium Google Chrome driver or else please download it from https://chromedriver.chromium.org/downloads
#####   For download you will need to know which Google Chrome version is running on your machine.
#####   You can check your chrome version by clicking on three dots at the top right corner then in the menu, move mouse down to "Help" then click on "About Google Chrome" to see Chrome version 

#### 3. Edit RunChromeForManualExecution.java present in integration tests

#### 4. In RunChromeForManualExecution.java Update System.setProperty according to your OS and the path to wherever you have downloaded or saved the driver from Step 2
#####   Linux : System.setProperty("webdriver.gecko.driver", "YOUR_DRIVER_FULL_PATH");
#####   OSX : System.setProperty("webdriver.chrome.driver", "YOUR_DRIVER_FULL_PATH");
#####   Windows : System.setProperty("webdriver.chrome.driver", "YOUR_DRIVER_FULL_PATH");

#### 5. If you are using Intellij then simple click on green pointer next to the public static void main method to run the program
 
## Test Cases:
#### All image process test cases are written using JUnit and are in dino.sensor package

## Configuration
#### Game configuration like when to jump can be updated via DinoConstants.java class
