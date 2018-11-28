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
public class Game implements Runnable
{
    //variables
    /** The starting title of the game window. */
    private static final String DEFAULT_WINDOW_TITLE = "Working Title";
    /** The default display scale. */
    private static final int DEFAULT_SPRITE_SCALE = 1;
    /** The starting title of the game window. */
    private static final int DEFAULT_WINDOW_WIDTH = 256;
    /** The starting title of the game window. */
    private static final int DEFAULT_WINDOW_HEIGHT = 192;
    /** The width of the monitor that the game is playing on. */
    private final int FULLSCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    /** The height of the monitor that the game is playing on. */
    private final int FULLSCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    /** The display scale to be used in fullscreen. */
    private final int FULLSCREEN_SPRITE_SCALE = FULLSCREEN_WIDTH / FULLSCREEN_HEIGHT /** stub */;
    /** The fullscreen constant for use in resizeWindow. */
    private static final int FULLSCREEN = 0;
    /** The starting icon of the game window. */
    private final BufferedImage DEFAULT_WINDOW_ICON = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    
    /** The current scale of all Sprites in the program. */
    private int currentSpriteScale;
    
    /** The width of the window as set at startup or during runtime. */
    private int windowWidth;
    /** The height of the window as set at startup or during runtime. */
    private int windowHeight;
    /** The current title of gameWindow. */
    private String windowTitle;
    /** The current icon of gameWindow. */
    private BufferedImage windowIcon;
    
    /** The game thread. */
    private Thread gameThread;
    /** Whether or not the thread is running. */
    private boolean running = false;
    
    /** The window of the game. */
    private JFrame gameWindow;
    /** The game's canvas for drawing. */
    private Canvas gameCanvas;
    /** The canvas's buffer strategy. */
    private BufferStrategy gameBufferStrategy;
    /** */
    private Graphics2D graphics;
    
    //constructors
    /** Default constructor, sets a clean debug profile. */
    public Game() {
        debugMode = LOG_VOID;
    }
    /** Debug constructor, sets the passed debug profile. */
    public Game(int profile) {
        debugMode = profile;
    }
    
    //aux methods
    /**
     * This method resizes gameWindow according to a provided scalar.
     * @param newScale FULLSCREEN sets the window to fullscreen, other integers set the window size to the default
     *                 parameters times this input as a scalar.  NOTE- Will not set window to a size larger than
     *                 the monitor the window is displayed on.
     */
    private void resizeWindow(final int newScale)
    {
        log("src.main.java.Game.resizeWindow", LOG_DEFAULT, "Entering resizeWindow.", LOG_ENTERING);
        gameWindow.setVisible(false);
        if (newScale == FULLSCREEN)
        //sets the scale to its fullscreen size
        //stub add non-square window countermeasures
        {
            gameWindow.setUndecorated(true);
            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            windowWidth = FULLSCREEN_WIDTH;
            windowHeight = FULLSCREEN_HEIGHT;
            currentSpriteScale = FULLSCREEN_SPRITE_SCALE;
        }
        else if (newScale > 0 && newScale * Math.max(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT) < Math.min(FULLSCREEN_HEIGHT, FULLSCREEN_WIDTH))
        //sets the scaled window if it is within the bounds of the monitor
        {
            gameWindow.setUndecorated(false);
            gameWindow.setExtendedState(JFrame.NORMAL);
            windowWidth = newScale * DEFAULT_WINDOW_WIDTH;
            windowHeight = newScale * DEFAULT_WINDOW_HEIGHT;
            gameWindow.setSize(windowWidth, windowHeight);
            currentSpriteScale = newScale;
        }
        else
        //sets the scale to the default size
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
            log("src.main.java.Game.resizeWindow", LOG_WARNING, "Caught null pointer and skipped canvas changes while resizing gameWindow.");
        }
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        log("src.main.java.Game.resizeWindow", LOG_DEFAULT, "Exiting resizeWindow.", LOG_EXITING);
    }
    
    //core methods
    /** Runs when the program first launches. */
    private void init()
    {
        log("src.main.java.Game.init", LOG_DEFAULT, "Entered init.", LOG_ENTERING);
        
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
            log("src.main.java.game.init", LOG_WARNING, "Failed to load window icon.");
            windowIcon = DEFAULT_WINDOW_ICON;
        }
        //initializes the game window with the default window name, sets the close operation, and sets the window icon.
        gameWindow = new JFrame(windowTitle);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setIconImage(windowIcon);
        this.resizeWindow(2);
        gameWindow.requestFocus();
        //initializes the canvas with the previously set window size and adds it to the JFrame.
        gameCanvas = new Canvas();
        gameCanvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
        gameCanvas.setMinimumSize(new Dimension(windowWidth, windowHeight));
        gameCanvas.setMaximumSize(new Dimension(windowWidth, windowHeight));
        gameWindow.add(gameCanvas);
        //packs the window.
        gameWindow.pack();
        log("src.main.java.Game.init", LOG_DEFAULT, "Exited init.", LOG_EXITING);
    }
    /** Updates local variables. */
    private void tick()
    {
        log("src.main.java.Game.tick", LOG_TRACE,"Entered tick.");
        
        
    }
    /** Sends everything to the canvas to be rendered. */
    private void draw()
    {
        log("src.main.java.Game.draw", LOG_TRACE,"Entered draw.");
        //checks buffer strategy and creates a new one if there are none found.
        gameBufferStrategy = gameCanvas.getBufferStrategy();
        if (gameBufferStrategy == null)
        {
            gameCanvas.createBufferStrategy(3);
            return;
        }
        graphics = (Graphics2D) gameBufferStrategy.getDrawGraphics();
        try
        {
            //stub testing
            graphics.setBackground(Color.BLACK);
            graphics.clearRect(0,0, windowWidth, windowHeight);
            graphics.fillRect(1, 1, 64, 64);
            graphics.fillRect(64, 64, 128, 128);
        }
        finally
        {
            //saves the changes to graphics and pops the current canvas and wipes graphics.
            gameBufferStrategy.show();
            graphics.dispose();
        }
    }
    
    /** When run, launches the game. */
    public void run()
    {
        log("src.main.java.Game.run", LOG_DEFAULT,"Started.");
        
        //initialize the game
        init();
    
        //starts the game
        start();
        
        //the game's entire body, repetition of this loop
        while (running)
        {
            tick();
            draw();
        }
        
        // stops the game
        stop();
        
        log("src.main.java.Game.run", LOG_DEFAULT,"Finished.");
    }
    /** Starts the thread. */
    public synchronized void start()
    {
        if (!running)
        {
            log("src.main.java.Game.start", LOG_DEFAULT,"Starting thread...");
            running = true;
            gameThread = new Thread(this);
            return;
        }
        log("src.main.java.Game.start", LOG_WARNING,"Method called while Game was running");
    }
    /** Stops the thread. */
    public synchronized void stop()
    {
        if (running)
        {
            log("src.main.java.Game.stop", LOG_DEFAULT,"Stopping thread...");
            running = false;
            try
            {
                gameThread.join();
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
            return;
        }
        log("src.main.java.Game.stop", LOG_WARNING,"Method called while Game was not running");
    }
    
    //debug logging
    /** String form of the log codes used by Game.log. */
    public static final String[] LOG_CODES = {"   /   ", "WARNING", "  LOG  ", " TRACE "};
    /** The code for an often repeated trace. */
    public static final int LOG_TRACE = 3;
    /** The code for a basic log message. */
    public static final int LOG_DEFAULT = 2;
    /** The code for a potentially problematic event. */
    public static final int LOG_WARNING = 1;
    /** The code for no traces at all. */
    public static final int LOG_VOID = 0;
    
    /** The code for entering a block in the debug logger. */
    public static final int LOG_ENTERING = 1;
    /** The code for exiting a block in the debug logger. */
    public static final int LOG_EXITING = -1;
    
    /** The current debug mode of the Game. */
    private int debugMode;
    /** The current indentation level of the debug logger. */
    private int currentLogIndent;
    
    /**
     * Logs an event in console for debug purposes.
     * @param location the location in the project that the call originates from.
     * @param type the type of event being logged.
     * @param message the update message.
     */
    public void log(String location, int type, String message)
    {
        if (debugMode >= type)
        {
            String tempOut = "[" + System.currentTimeMillis() + "] [" + LOG_CODES[type] + "] @ ";
            for (int i = 0; i < currentLogIndent; i++)
            {
                tempOut += " ";
            }
            tempOut += location + " == " + message;
            System.out.println(tempOut);
        }
    }
    /**
     * Logs an event in console for debug purposes.
     * @param location the location in the project that the call originates from.
     * @param type the type of event being logged.
     * @param message the update message.
     * @param indent ENTERING if entering a method, EXITING if exiting a method.
     */
    public void log(String location, int type, String message, int indent)
    {
        if (debugMode >= type)
        {
            String tempOut = "[" + System.currentTimeMillis() + "] [" + LOG_CODES[type] + "] @ ";
            for (int i = 0; i < currentLogIndent; i++)
            {
                tempOut += " ";
            }
            tempOut += location + " == " + message;
            System.out.println(tempOut);
        }
        if (indent == LOG_ENTERING || indent == LOG_EXITING)
        {
            currentLogIndent += indent;
        }
    }
}
