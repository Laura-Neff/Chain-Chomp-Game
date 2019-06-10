package ChainChomp;


//CS 170 - Emory University (framework made by Paul Oser)
//Class lecture, day 25 - Spring 2018
//
//ChainChomp.Animation is a class that implements animated images on a GCanvas.  It can
//handle either looping or one-time animations, and runs itself via an internal
//timer with an action listener.  It requires the animation frames to be stored in
//a folder with a very specific filename setup.

//Imports
import acm.graphics.GCanvas;
import acm.graphics.GCompound;
import acm.graphics.GImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

public class Animation extends GCompound {
    
	// An ChainChomp.Animation can either run once, or run repeatedly; these are
	// constants to control that behavior.
    public static final int LOOPS = 0;
    public static final int REMOVES_ITSELF_WHEN_COMPLETE = 1;
    
    // Note: frameDir is the name of the folder that (only) contains .png image 
    // files of the various frames of the animation.  They should be named
    // "frame_#_delay-#s.png" where digits appearing at the first "#" indicate 
    // the frame number (starts at zero), and the digits appearing between the 
    // "-" and "s" indicate how many seconds (possibly a decimal value) that 
    // frame should last. This folder should be located in the same location 
    // as this package.
    
    private String frameDir;
    private GImage frames[];
    private int frameDelays[];
    private boolean framesLoaded;
    private int mode;
    
    private Timer timer;
    private int currentFrame;
    private int millisecondsToNextFrame;
    
    // This method loads frames from the provided directory and stores them
    // in a GImage array.
    public void loadFrames() {
        File dir = new File(frameDir);				// Get the directory
        File[] directoryListing = dir.listFiles();	// Get the list of frame files  
        if (directoryListing != null) {				// If the directory isn't empty
            frameDelays = new int[directoryListing.length];	// Frame delays
            frames = new GImage[directoryListing.length];	// Frame images
            for (int i = 0; i < directoryListing.length; i++) {
            	// Setting up the delays and images for each animation frame
                File file = directoryListing[i];
                int frameNum = Integer.parseInt(file.getName().split("_")[1]);
                frameDelays[frameNum] = (int) (1000.0 * Double.parseDouble(
                                 file.getName().split("-")[1].split("s")[0]));
                frames[frameNum] = new GImage(frameDir + "//" + file.getName());
            }
            framesLoaded = true;
        }
    }
    
    // This method return whether or not the animation frames have been loaded
    public boolean framesLoaded() {
        return framesLoaded;
    }
    
    // The constructor takes a directory and an int representing whether the
    // animation should loop (either LOOPS or REMOVES_ITSELF_WHEN_DONE)
    public Animation(String frameDir, int mode) {
        this.frameDir = frameDir;
        this.mode = mode;
        
        if (! framesLoaded) {
            loadFrames();
        }
        
        // Sets up an internal timer object for the animation with a delay of 1
        // and an action listener to control the progression of the frames
        timer = new Timer(1,new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if ((millisecondsToNextFrame == 0)	// If it's time for next frame 
                 && (currentFrame < frames.length - 1)) {	// And there is another
                	// Get the delay until the next frame
                    millisecondsToNextFrame = frameDelays[currentFrame];
                    currentFrame++;
                    // Clear the compound
                    Animation.this.removeAll();
                    // Add the next frame to the center of the compound
                    frames[currentFrame].setLocation(
                                      -frames[currentFrame].getWidth()/2, 
                                      -frames[currentFrame].getHeight()/2);
                    Animation.this.add(frames[currentFrame]);
                }
                // If it's not time for the next frame but there is another frame
                else if (currentFrame < frames.length - 1) {
                    // Countdown to the next frame
                	millisecondsToNextFrame--;
                }
                // If it's time for the next frame but there isn't another one
                else {
                    switch (mode) {
                    // Loop to the beginning of the animation if applicable
                    case LOOPS : 
                        currentFrame = 0;
                        break;
                    // Otherwise remove this animation object from the parent canvas
                    case REMOVES_ITSELF_WHEN_COMPLETE : 
                        GCanvas c = ((GCanvas) (Animation.this.getParent()));
                        c.remove(Animation.this);
                        Animation.this.timer.stop();
                        break;
                    }
                }
            }});
        // Don't forget to start this timer!
        timer.start();
    }

}