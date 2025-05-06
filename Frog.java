import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*;
import java.util.ArrayList;

// Represents the player-controlled frog in the game
public class Frog {
  private int x, y, d; // x and y positions, and d stores direction (0: up, 1: left, 2: down, 3: right)
  private Image imageLeft, imageRight, imageDown, imageUp; // Frog images for each direction
  private final int width = 50;  // The width of the frog
  private final int height = 50; // The height of the frog

  // Constructor initializes position and directional images
  public Frog(int xx, int yy, Image up, Image down, Image left, Image right) {
    x = xx;
    y = yy;
    imageLeft = left;
    imageRight = right;
    imageDown = down;
    imageUp = up;
  }

  // Movement methods update position and direction
  public void moveLeft() {
    x -= 50;
    d = 1; // Left
  }

  public void moveRight() {
    x += 50;
    d = 3; // Right
  }

  public void moveUp() {
    y -= 50;
    d = 0; // Up
  }

  public void moveDown() {
    y += 50;
    d = 2; // Down
  }

  // Returns frog's collision box
  public Rectangle getFrogCoordinates() {
    return new Rectangle(x, y, 50, 50);
  }

  // Getters for position
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  // Resets frog to starting position (used after death or new level)
  public void reset() {
    x = 824 / 2;
    y = 788;
    d = 0;
  }

  // Sets frog's exact position
  public void setPosition(int xx, int yy) {
    this.x = xx;
    this.y = yy;
  }

  // Returns dimensions
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  // Moves the frog along with a log's horizontal motion
  public void moveOnLog(int xx) {
    x -= xx;
  }

  // Draws the frog based on the current direction
  public void draw(Graphics g) {
    if (d == 0) {
      g.drawImage(imageUp, x, y, 50, 50, null);
    } else if (d == 1) {
      g.drawImage(imageLeft, x, y, 50, 50, null);
    } else if (d == 2) {
      g.drawImage(imageDown, x, y, 50, 50, null);
    } else if (d == 3) {
      g.drawImage(imageRight, x, y, 50, 50, null);
    }
  }
}
