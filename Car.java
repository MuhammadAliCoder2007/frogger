import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*;
import java.util.ArrayList;

// Represents a car obstacle in the game
public class Car {
 
  private int x, y, w, h, s; // Position (x, y), size (width, height), and speed
  Image image; // Car image

  // Constructor to initialize the car's position, size, speed, and image
  public Car(int xx, int yy, int ww, int hh, int speed, Image img) {
    x = xx;
    y = yy;
    w = ww;
    h = hh;
    s = speed;
    image = img;
  }

  // Increases the speed of the car (used when difficulty increases)
  public void increaseSpeed() {
    s += 1;
  }

  // Moves the car across the screen
  public void move() {
    x -= s;

    // Wraps the car around the screen when it goes out of bounds
    if (s > 0 && x < -w) {
      x = 824; // Reappears from the right
    } else if (s < 0 && x > 824) {
      x = -w;  // Reappears from the left
    }
  }

  // Returns the bounding box of the car for collision detection
  public Rectangle CarCorrdinates() {
    return new Rectangle(x, y, w, h);
  }

  // Draws the car on the screen
  public void draw(Graphics g) {
    g.drawImage(image, x, y, w, h, null);
  }
}
