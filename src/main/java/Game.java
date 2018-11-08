package main.java;

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

/**
 * This is the uppermost part of the engine if you will.  It handles everything on a macro level.
 */
public class Game
{
    /** The starting title of the game window. */
    private static final String DEFAULT_WINDOW_TITLE = "Working Title";
    /** The starting title of the game window. */
    private static final int DEFAULT_WINDOW_WIDTH = 256;
    /** The starting title of the game window. */
    private static final int DEFAULT_WINDOW_HEIGHT = 192;
    /** The width of the monitor that the game is playing on. */
    private static final int FULLSCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    /** The height of the monitor that the game is playing on. */
    private static int FULLSCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    /** The fullscreen constant for use in resizeWindow. */
    private static final int FULLSCREEN = 0;
    /** The starting icon of the game window. */
    private static final BufferedImage DEFAULT_WINDOW_ICON = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    
    /** The width of the window as set at startup or during runtime. */
    private static int windowWidth;
    /** The height of the window as set at startup or during runtime. */
    private static int windowHeight;
    /** The current title of gameWindow. */
    private static String windowTitle;
    /** The current icon of gameWindow. */
    private static BufferedImage windowIcon;
    /** The window of the game. */
    private static JFrame gameWindow;
    
    
    
    /**
     * This method resizes gameWindow according to a provided scalar.
     * @param newScale FULLSCREEN sets the window to fullscreen, other integers set the window size to the default
     *                 parameters times this input as a scalar.  NOTE- Will not set window to a size larger than
     *                 the monitor the window is displayed on.
     */
    private static void resizeWindow(final int newScale)
    {
        gameWindow.setVisible(false);
        if (newScale == FULLSCREEN)
        {
            gameWindow.setUndecorated(true);
            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            windowWidth = FULLSCREEN_WIDTH;
            windowHeight = FULLSCREEN_HEIGHT;
        }
        else if (newScale > 0 && newScale * Math.max(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT) < Math.min(FULLSCREEN_HEIGHT, FULLSCREEN_WIDTH))
        {
            gameWindow.setUndecorated(false);
            gameWindow.setExtendedState(JFrame.NORMAL);
            windowWidth = newScale * DEFAULT_WINDOW_WIDTH;
            windowHeight = newScale * DEFAULT_WINDOW_HEIGHT;
            gameWindow.setSize(windowWidth, windowHeight);
        }
        else
        {
            gameWindow.setUndecorated(false);
            gameWindow.setExtendedState(JFrame.NORMAL);
            windowWidth = DEFAULT_WINDOW_WIDTH;
            windowHeight = DEFAULT_WINDOW_HEIGHT;
            gameWindow.setSize(windowWidth, windowHeight);
        }
        //stub set content scaling
        //stub update scaled content
        gameWindow.setVisible(true);
    }
    /** Runs when the program first launches. */
    private static void initialize()
    {
        windowTitle = DEFAULT_WINDOW_TITLE;
        windowWidth = DEFAULT_WINDOW_WIDTH;
        windowHeight = DEFAULT_WINDOW_HEIGHT;
        try
        {
            windowIcon = ImageIO.read(new File("src/main/resources/icon.png"));
        }
        catch (java.io.IOException exception)
        {
            windowIcon = DEFAULT_WINDOW_ICON;
        }
        
        gameWindow = new JFrame(windowTitle);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setIconImage(windowIcon);
        resizeWindow(1);
        //setWindowDim(windowWidth, windowHeight);
    }
    /** When run, launches the game. */
    public static void main(String unused[])
    {
        initialize();
        System.out.println("Finished.");
    }
}
