package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/** This is the uppermost part of the engine if you will.  It handles everything on a macro level. */
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
    
    /** The current instance's Logger. */
    private Logger l;
    
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
    /** The graphics object used to paint the canvas. */
    private Graphics2D g;
    
    //constructors
    /** Default constructor, sets a clean debug profile. */
    public Game() {
        l = new Logger(Logger.LOG_VOID);
    }
    /** Debug constructor, sets the passed debug profile. */
    public Game(int profile) {
       l = new Logger(profile);
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
        l.log("src.main.java.Game.resizeWindow", l.LOG_DEFAULT, "Entering resizeWindow.", l.LOG_ENTERING);
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
        //stub this is inaccurate vvv
        else if (newScale > 1 && newScale * Math.max(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT) < Math.min(FULLSCREEN_HEIGHT, FULLSCREEN_WIDTH))
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
            l.log("src.main.java.Game.resizeWindow", l.LOG_WARNING, "Caught null pointer and skipped canvas changes while resizing gameWindow.");
        }
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        l.log("src.main.java.Game.resizeWindow", l.LOG_DEFAULT, "Exiting resizeWindow.", l.LOG_EXITING);
    }
    
    //core methods
    /** Runs when the program first launches. */
    private void init()
    {
        l.log("src.main.java.Game.init", l.LOG_DEFAULT, "Entered init.", l.LOG_ENTERING);
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
            l.log("src.main.java.game.init", l.LOG_WARNING, "Failed to load window icon.");
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
        l.log("src.main.java.Game.init", l.LOG_DEFAULT, "Exited init.", l.LOG_EXITING);
    }
    /** Updates local variables. */
    private void tick()
    {
        l.log("src.main.java.Game.tick", l.LOG_TRACE,"Entered tick.", l.LOG_ENTERING);
    
        l.log("src.main.java.Game.tick", l.LOG_TRACE,"Exited tick.", l.LOG_EXITING);
    }
    /** Sends everything to the canvas to be rendered. */
    private void pushCanvas()
    {
        l.log("src.main.java.Game.pushCanvas", l.LOG_TRACE,"Entered pushCanvas.", l.LOG_ENTERING);
        //checks buffer strategy and creates a new one if there are none found.
        gameBufferStrategy = gameCanvas.getBufferStrategy();
        if (gameBufferStrategy == null)
        {
            gameCanvas.createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) gameBufferStrategy.getDrawGraphics();
        try
        {
            //draws to the buffer canvas
            draw();
        }
        finally //always triggers regardless of exceptions.
        {
            //saves the changes to graphics and pops the current canvas and wipes graphics.
            gameBufferStrategy.show();
            g.dispose();
        }
        l.log("src.main.java.Game.pushCanvas", l.LOG_TRACE,"Exited pushCanvas.", l.LOG_EXITING);
    }
    /** Where all of the actual rendering to the buffer canvas takes place. */
    private void draw()
    {
        //stub testing
        g.setBackground(Color.BLACK);
        g.clearRect(0,0, windowWidth, windowHeight);
        g.fillRect(1 * currentSpriteScale, 1 * currentSpriteScale, 32 * currentSpriteScale, 32 * currentSpriteScale);
        g.fillRect(32 * currentSpriteScale, 32 * currentSpriteScale, 64 * currentSpriteScale, 64 * currentSpriteScale);
        g.fillRect(96 * currentSpriteScale, 96 * currentSpriteScale, 128 * currentSpriteScale, 128 * currentSpriteScale);
    }
    
    /** When run, launches the game. */
    public void run()
    {
        l.log("src.main.java.Game.run", l.LOG_DEFAULT,"Started.");
        
        //initialize the game
        init();
    
        //starts the game
        start();
        
        //the game's entire body, repetition of this loop
        while (running)
        {
            tick();
            pushCanvas();
        }
        
        // stops the game
        stop();
        
        l.log("src.main.java.Game.run", l.LOG_DEFAULT,"Finished.");
    }
    /** Starts the thread. */
    public synchronized void start()
    {
        if (!running)
        {
            l.log("src.main.java.Game.start", l.LOG_DEFAULT,"Starting thread...");
            running = true;
            gameThread = new Thread(this);
            return;
        }
        l.log("src.main.java.Game.start", l.LOG_WARNING,"Method called while Game was running");
    }
    /** Stops the thread. */
    public synchronized void stop()
    {
        if (running)
        {
            l.log("src.main.java.Game.stop", l.LOG_DEFAULT,"Stopping thread...");
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
        l.log("src.main.java.Game.stop", l.LOG_WARNING,"Method called while Game was not running");
    }
}
