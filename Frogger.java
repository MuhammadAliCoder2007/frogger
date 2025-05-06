//Muhammad ALi

// * Description:
/*
 * This is a classic arcade-style game based on the original Frogger concept.
 * The objective of the game is to navigate a frog from the bottom of the screen
 * to one of the designated safe platforms at the top. The player must avoid
 * various hazards such as moving cars, water, and diving turtles, while
 * strategically using floating logs and visible turtles to cross the river
 * 
 for commenting, turtle disasapeairing, unlimited level, landRect, online help was used
/*/


import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*;
import java.util.ArrayList;


/**
 * Frogger - Main JFrame Class
 * 
 * This class sets up the main game window for the Frogger game.
 * It initializes and displays the GamePanel where the actual gameplay occurs.
 */
class Frogger extends JFrame{ 
 GamePanel game;
  
  public Frogger() {
    // Set the title of the game window
    super("Frogger ");
    
    // Initialize the game panel and add it to the JFrame
    game = new GamePanel();
    add(game);

    // Allow the game window to be resized
    setResizable(true);

    // Adjust the window size based on preferred sizes of components
    pack();

    // Close the application when the window is closed
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Make the window visible
    setVisible(true);
  }
  
  public static void main(String[]args) {
    // Create an instance of the Frogger game
    Frogger eg1 = new Frogger(); 

    // Set the title of the game window
    eg1.setTitle("Frogger");
  }
}


