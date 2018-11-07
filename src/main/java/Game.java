package main.java;

import javax.swing.*;
import java.awt.image.*;
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
    /** The starting icon of the game window. */
    private static final BufferedImage DEFAULT_WINDOW_ICON =
            new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    
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
     * Sets the dimensions of the window.
     * <p>
     *     The parameters for this method will only ever be (//stub some set values like "small, medium, large")
     * </p>
     *
     * @param newWidth The new width that windowWidth will be set to.
     * @param newHeight The new height that windowHeight will be set to.
     */
    private static void setWindowDim(int newWidth, int newHeight)
    {
        windowWidth = newWidth;
        windowHeight = newHeight;
        //stub set content scaling
        //stub update scaled content
        gameWindow.setSize(windowWidth, windowHeight);
        setWindowBorders(true);
    }
    /**
     * Sets the borders of the window.
     * <p>
     *     Passing True will add window borders, False will set the window to borderless.
     * </p>
     *
     * @param isBorderless True for window borders, False for borderless.
     */
    private static void setWindowBorders(boolean isBorderless)
    {
        //stub implement method
    }
    /**
     * (//stub add javadoc)
     */
    private static void setFullscreen()
    {
        //stub set content scaling
        //stub update scaled content
        gameWindow.setSize(windowWidth, windowHeight);
        setWindowBorders(false);
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
        gameWindow.setVisible(true);
        setWindowDim(windowWidth, windowHeight);
    }
    /** When run, launches the game. */
    public static void main(String unused[])
    {
        initialize();
        //stub testing
        while (gameWindow != null)
        {
            System.out.println("running...");
        }
        System.out.println("finished.");
    }
}
