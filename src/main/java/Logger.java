package main.java;

public class Logger
{
    //logging
    /** String form of the log codes used by Game.log. */
    private static final String[] LOG_CODES = {"   /   ", "WARNING", "  LOG  ", " TRACE "};
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
     * Default constructor.
     * @param initDebugMode the specified debug mode for the logger.
     */
    public Logger(int initDebugMode)
    {
        debugMode = initDebugMode;
    }
    
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
            tempOut += "|" + location + " == " + message;
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
            if (indent == 1)
            {
                tempOut += "\\";
            }
            else
            {
                tempOut += "*";
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
