import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*;
import java.util.ArrayList;

// Turtle class represents turtles that the frog can ride on
public class Turtle {
 
 private int x, y, w, h, s; // Position (x, y), width, height, and speed
 Image image; // Image used for drawing the turtle
 private boolean visible = true; // Whether the turtle is currently visible
 private boolean inDivingGroup = false; // Whether this turtle belongs to a diving group

 // Constructor to initialize the turtle's properties
 public Turtle(int xx, int yy, int ww, int hh, int speed, Image img) {
  x = xx;
  y = yy;
  w = ww;
  h = hh;
  s = speed;
  image = img;
 }
 
 // Moves the turtle horizontally and wraps it around the screen if it goes out of bounds
 public void move() {
   x -= s;

   // Wrap to the right side if moving left and offscreen
   if (s > 0 && x < -w) {
        x = 824;
   // Wrap to the left side if moving right and offscreen
   } else if (s < 0 && x > 824) {
        x = -w;
   }
 }

 // Sets whether the turtle is part of a diving group
 public void setInDivingGroup(boolean group) {
     inDivingGroup = group;
 }

 // Checks if the turtle is in a diving group
 public boolean isInDivingGroup() {
     return inDivingGroup;
 }

 // Determines if the turtle should be currently visible
 public boolean isVisible() {
     return !inDivingGroup || visible; // Always visible unless part of a diving group
 }

 // Toggles the visibility state of the turtle (simulating diving)
 public void toggleVisibility() {
     if (inDivingGroup) {
         visible = !visible;
     }
 }

 // Increases the turtle's speed by 1 (used for increasing level difficulty)
 public void increaseSpeed() {
     s += 1;
 }

 // Returns the bounding rectangle for collision detection
 public Rectangle TurtleCorrdinates() {
     return new Rectangle(x, y, w, h);
 }

 // Returns the current speed of the turtle
 public int getSpeed() {
     return s;
 }

 // Draws the turtle only if it's currently visible
 public void draw(Graphics g) {
     if (isVisible()) {
         g.drawImage(image, x, y, w, h, null);
     }
 }
}
