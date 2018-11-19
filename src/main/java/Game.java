package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * This is the uppermost part of the engine if you will.  It handles everything on a macro level.
 */
public class Game implements KeyListener
{
    /** The starting title of the game window. */
    private static final String DEFAULT_WINDOW_TITLE = "Working Title";
    /** The default sprite scale. */
    public static final int DEFAULT_SPRITE_SCALE = 1;
    /** The starting title of the game window. */
    private static final int DEFAULT_WINDOW_WIDTH = 256;
    /** The starting title of the game window. */
    private static final int DEFAULT_WINDOW_HEIGHT = 192;
    /** The width of the monitor that the game is playing on. */
    private static final int FULLSCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    /** The height of the monitor that the game is playing on. */
    private static final int FULLSCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    /** The sprite scale to be used in fullscreen. */
    private static final int FULLSCREEN_SPRITE_SCALE = FULLSCREEN_WIDTH / FULLSCREEN_HEIGHT /** stub */;
    /** The fullscreen constant for use in resizeWindow. */
    private static final int FULLSCREEN = 0;
    /** The starting icon of the game window. */
    private static final BufferedImage DEFAULT_WINDOW_ICON = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    
    /** The current scale of all Sprites in the program. */
    public static int currentSpriteScale;
    
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
    /** The game's canvas for drawing */
    private static Canvas gameCanvas;
    
    
    /**
     * Checks whether the game is fullscreen.
     * @return true if the window is fullscreen.
     */
    public static boolean isFullscreen()
    {
        return windowWidth == FULLSCREEN_WIDTH && windowHeight == FULLSCREEN_HEIGHT;
    }
    
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
            currentSpriteScale = FULLSCREEN_SPRITE_SCALE;
        }
        else if (newScale > 0 && newScale * Math.max(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT) < Math.min(FULLSCREEN_HEIGHT, FULLSCREEN_WIDTH))
        {
            gameWindow.setUndecorated(false);
            gameWindow.setExtendedState(JFrame.NORMAL);
            windowWidth = newScale * DEFAULT_WINDOW_WIDTH;
            windowHeight = newScale * DEFAULT_WINDOW_HEIGHT;
            gameWindow.setSize(windowWidth, windowHeight);
            currentSpriteScale = newScale;
        }
        else
        {
            gameWindow.setUndecorated(false);
            gameWindow.setExtendedState(JFrame.NORMAL);
            windowWidth = DEFAULT_WINDOW_WIDTH;
            windowHeight = DEFAULT_WINDOW_HEIGHT;
            gameWindow.setSize(windowWidth, windowHeight);
            currentSpriteScale = DEFAULT_SPRITE_SCALE;
        }
        try
        {
            gameCanvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
            gameCanvas.setMinimumSize(new Dimension(windowWidth, windowHeight));
            gameCanvas.setMaximumSize(new Dimension(windowWidth, windowHeight));
        }
        catch (NullPointerException exception)
        {
            System.out.println(System.currentTimeMillis() + " Game.resizeWindow: Skipped canvas resizing during initialization (caught null pointer)");
        }
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
    /** Runs when the program first launches. */
    private static void initialize()
    {
        System.out.println(System.currentTimeMillis() + " Game.initialize: Initializing...");
        //sets the values of the runtime window data
        //stub load settings for last screen size on startup
        currentSpriteScale = DEFAULT_SPRITE_SCALE;
        windowTitle = DEFAULT_WINDOW_TITLE;
        windowWidth = DEFAULT_WINDOW_WIDTH;
        windowHeight = DEFAULT_WINDOW_HEIGHT;
        try
        {
            windowIcon = ImageIO.read(new File("src/main/resources/icon.png"));
        }
        catch (java.io.IOException exception)
        {
            System.out.println("[ERROR] Failed to load window icon.");
            windowIcon = DEFAULT_WINDOW_ICON;
        }
        //initializes the game window with the default window name, sets the close operation, and sets the window icon.
        gameWindow = new JFrame(windowTitle);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setIconImage(windowIcon);
        resizeWindow(FULLSCREEN);
        gameWindow.requestFocus();
        //initializes the canvas with the previously set window size and adds it to the JFrame.
        gameCanvas = new Canvas();
        gameCanvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
        gameCanvas.setMinimumSize(new Dimension(windowWidth, windowHeight));
        gameCanvas.setMaximumSize(new Dimension(windowWidth, windowHeight));
        gameWindow.add(gameCanvas);
        //pack the game window
        gameWindow.pack();
    }
    /** When run, launches the game. */
    public static void main(String unused[])
    {
        initialize();
        System.out.println("Finished.");
    }
    
    /**
     * Detects when a key is typed.
     * @param event the event triggered by the key interaction.
     */
    @Override
    public void keyTyped(KeyEvent event)
    {
        return;
    }
    /**
     * Detects when a key is pressed.
     * @param event the event triggered by the key interaction.
     */
    @Override
    public void keyPressed(KeyEvent event)
    {
        return;
    }
    /**
     * Detects when a key is released.
     * @param event the event triggered by the key interaction.
     */
    @Override
    public void keyReleased(KeyEvent event)
    {
        return;
    }
}
