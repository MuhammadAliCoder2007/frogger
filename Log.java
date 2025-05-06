import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*;
import java.util.ArrayList;

// Log class represents floating logs the frog can ride on
public class Log {
  private int x, y, w, h, s; // Position (x, y), width, height, and speed
  Image image; // Image to display the log

  // Constructor to initialize a log object
  public Log(int xx, int yy, int ww, int hh, int speed, Image img) {
    x = xx;
    y = yy;
    w = ww;
    h = hh;
    s = speed;
    image = img;
  }

  // Move the log horizontally
  public void move() {
    x -= s;

    // Wrap the log around the screen when it moves off one side
    if (s > 0 && x < -w) {
        x = 824;
    } else if (s < 0 && x > 824) {
        x = -w;
    }
  }

  // Increase the log's speed for higher levels
  public void increaseSpeed() {
    // Increase speed by a fixed amount (e.g., 1)
    s += 1;
  }

  // Get the bounding rectangle of the log for collision detection
  public Rectangle LogCorrdinates() {
    return new Rectangle(x, y, w, h);
  }

  // Get the current speed of the log
  public int getSpeed() {
    return s;
  }

  // Draw the log on the screen
  public void draw(Graphics g) {
    g.drawImage(image, x, y, w, h, null);
  }
}
