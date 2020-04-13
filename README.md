# This code plays the google chrome offline mode dino game

## Algorithm:
### The code uses selenium to take a screenshot
### Uses the java image buffer to process the screenshot and identify objects on ground and air
### Identifies how far the object is
### Identifies the width of the object
### Code then decides when to jump or duck

## Trying it out:
### If you want to see the code in Action then simply run RunChromeForManualExecution.java in integration tests

## Test Cases:
### All image process test cases are written using JUnit and are in dino.sensor package

## Configuration
## Game configuration like when to jump can be updated via DinoConstants.java class