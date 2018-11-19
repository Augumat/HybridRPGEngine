package test.java;

import main.java.Game;

public class GameTest
{
    public static void main(String[] args) {
        System.out.println("BEGIN TEST");
        int debugMode = Integer.parseInt(args[0]);
        Game test = new Game(debugMode);
        test.run();
        System.out.println("END TEST");
    }
}
