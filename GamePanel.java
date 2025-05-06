import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

// GamePanel class manages the gameplay logic, rendering, and user interactions
public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener {
   Frog frog; // Player-controlled frog
   int x = 824/2, y=788; // Initial frog position
   private long lastDiveToggle = System.currentTimeMillis(); // Time tracking for turtle diving
   
   private final int diveInterval = 2000; // Turtle dive toggle interval (2 seconds)
   Timer timer; // Game loop timer
   public static final int INTRO = 0, GAME = 1;
   private int screen = GAME; // Game state (can expand to show intro screen)
   boolean []keys; // Tracks key states for smooth input handling
   private Image frogImage,frogRight,frogDown,frogLeft,p,car1,car2,car3,car4,car5,fp,log1,log2,log3,turtle; // Game sprites
   int d = 0; // unused
   Rectangle landRect1 = new Rectangle(33, 180, 50, 50); // Final landing spots
   Rectangle landRect2 = new Rectangle(210, 180, 50, 50);
   Rectangle landRect3 = new Rectangle(387, 180, 50, 50);
   Rectangle landRect4 = new Rectangle(564, 180, 50, 50);
   Rectangle landRect5 = new Rectangle(741, 180, 50, 50);
   
   ArrayList<Car> cars = new ArrayList<>(); // Cars in the game
   ArrayList<Log> logs = new ArrayList<>(); // Logs in the river
   ArrayList<Turtle> turtles = new ArrayList<>(); // Turtles in the river
   ArrayList<Frog> landedFrogs = new ArrayList<>(); // Frogs that reached platforms
   boolean[] platformFilled = new boolean[5]; // Tracks filled final platforms

   // Constructor: Initializes game components and layout
   public GamePanel(){
     setPreferredSize(new Dimension(824, 888));
     keys = new boolean[2000]; // Allow handling of many key codes
     
     // Load images
     frogImage = new ImageIcon("frog.png").getImage();
     frogRight = new ImageIcon("frog_icon_right.png").getImage();
     frogDown = new ImageIcon("frog_icon_down.png").getImage();
     frogLeft = new ImageIcon("frog_icon_left.png").getImage();
     frog = new Frog(824 / 2, 788, frogImage, frogDown, frogLeft, frogRight);
     p = new ImageIcon("p.png").getImage();
     car1 = new ImageIcon("car1.png").getImage();
     car2 = new ImageIcon("car2.png").getImage();
     car3 = new ImageIcon("car3.png").getImage();
     car4 = new ImageIcon("car4.png").getImage();
     car5 = new ImageIcon("car5.png").getImage();
     fp = new ImageIcon("fp.png").getImage();
     log1 = new ImageIcon("log1.png").getImage();
     log2 = new ImageIcon("log2.png").getImage();
     log3 = new ImageIcon("log3.png").getImage();
     turtle = new ImageIcon("turtle.png").getImage();

     // Set up input handling
     setFocusable(true);
     requestFocus();
     addKeyListener(this);
     addMouseListener(this);

     // Add cars with alternating lanes and directions
     cars.add(new Car(824, 788-50, 80, 45, 2, car1));
     cars.add(new Car(824-300,788-50, 80, 45, 2, car1));
     cars.add(new Car(824-600, 788-50, 80, 45, 2, car1));
     cars.add(new Car(0, 788-100, 80, 45, -2, car2));
     cars.add(new Car(300,788-100, 80, 45, -2, car2));
     cars.add(new Car(600, 788-100, 80, 45, -2, car2));
     cars.add(new Car(150, 788-150, 80, 45, 2, car3));
     cars.add(new Car(450,788-150, 80, 45, 2, car3));
     cars.add(new Car(750, 788-150, 80, 45, 2, car3));
     cars.add(new Car(0, 788-200, 80, 45, -4, car4));
     cars.add(new Car(300,788-200, 80, 45, -4, car4));
     cars.add(new Car(0, 788-245, 80, 40, 2, car5));
     cars.add(new Car(300,788-245, 80, 40, 2, car5));

     // Add turtles
     turtles.add(new Turtle(0, 488-40, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(60, 488-40, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(300, 488-40, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(360, 488-40, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(600, 488-40, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(660, 488-40, 4*12,3*12,4,turtle));

     // Add logs in multiple rows
     logs.add(new Log(0, 488-40-50, 13*10,3*10,-4,log1));
     logs.add(new Log(300, 488-40-50, 13*10,3*10,-4,log1));
     logs.add(new Log(600, 488-40-50, 13*10,3*10,-4,log1));
     logs.add(new Log(0, 488-40-50-50, 28*10,3*10,-4,log2));
     logs.add(new Log(0, 488-40-50-50, 28*10,3*10,-4,log2));
     logs.add(new Log(400, 488-40-50-50, 28*10,3*10,-4,log2));
     logs.add(new Log(600, 488-40-50-50-50-50, 20*10,3*10,-4,log3));
     logs.add(new Log(0, 488-40-50-50-50-50, 20*10,3*10,-4,log3));

     // More turtles on upper row
     turtles.add(new Turtle(0, 488-40-50-50-50, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(60, 488-40-50-50-50, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(300, 488-40-50-50-50, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(360, 488-40-50-50-50, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(600, 488-40-50-50-50, 4*12,3*12,4,turtle));
     turtles.add(new Turtle(660, 488-40-50-50-50, 4*12,3*12,4,turtle));

     // Designate diving groups
     turtles.get(0).setInDivingGroup(true);
     turtles.get(1).setInDivingGroup(true);
     turtles.get(2).setInDivingGroup(true);
     turtles.get(7).setInDivingGroup(true);
     turtles.get(8).setInDivingGroup(true);
     turtles.get(9).setInDivingGroup(true);

     // Start game timer (updates every 10ms)
     timer = new Timer(10, this);
     timer.start();
 }

   private int level = 1; // Current level

   // Checks if all 5 platforms are filled
   public boolean allPlatformsFilled() {
        for (boolean filled : platformFilled) {
            if (!filled) return false;
        }
        return true;
    }

   // Mouse and key event handlers (required overrides)
   public void mouseClicked(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   public void mouseReleased(MouseEvent e){}
   @Override
   public void keyPressed(KeyEvent e){     
     keys[e.getKeyCode()] = true;
   }
   @Override
   public void keyReleased(KeyEvent e){
     keys[e.getKeyCode()] = false;
   }
   @Override
   public void keyTyped(KeyEvent e){}
   public void mousePressed(MouseEvent e){}

   // Main game loop (called on timer tick)
   @Override
   public void actionPerformed(ActionEvent e){
        // Handle key input for frog movement
        if(keys[KeyEvent.VK_LEFT]==true){
          frog.moveLeft();
          keys[KeyEvent.VK_LEFT]=false;
        }
        if(keys[KeyEvent.VK_RIGHT]){
          frog.moveRight();
          keys[KeyEvent.VK_RIGHT]=false;
        }
        if(keys[KeyEvent.VK_UP]){
          frog.moveUp();
          keys[KeyEvent.VK_UP]=false;
        }
        if(keys[KeyEvent.VK_DOWN]){
          frog.moveDown();
          keys[KeyEvent.VK_DOWN]=false;
        }

        // Move all obstacles/platforms
        for (Car c : cars) {
            c.move();
        }
        for (Turtle t : turtles) {
            t.move();
        }
        for (Log l : logs) {
            l.move();
        }

        // Collision detection
        Rectangle frogCoordinates = frog.getFrogCoordinates();
        for (Car c : cars) {
          if (frogCoordinates.intersects(c.CarCorrdinates())) {
            frog.reset();
            break;
          }
        }

        // Check if frog is on a log or turtle
        boolean onLog = false;
        for (Log l : logs) {
          if (frogCoordinates.intersects(l.LogCorrdinates())) {
            frog.moveOnLog(l.getSpeed());
            onLog = true;
            break;
          }
        }
        for (Turtle t : turtles) {
          if (t.isVisible() && frogCoordinates.intersects(t.TurtleCorrdinates())) {
            frog.moveOnLog(t.getSpeed());
            onLog = true;
            break;
          }
        }

        // Frog falls in water if on diving turtle
        for (Turtle t : turtles) {
          if (!t.isVisible() && frogCoordinates.intersects(t.TurtleCorrdinates())) {
            frog.reset();
            break;
          }
        }

        // Toggle diving turtles every few seconds
        long now = System.currentTimeMillis();
        if (now - lastDiveToggle > diveInterval) {
          for (Turtle t : turtles) {
            if (t.isInDivingGroup()) {
              t.toggleVisibility();
            }
          }
          lastDiveToggle = now;
        }

        // Check if frog landed on any final platform
        if (frogCoordinates.intersects(landRect1) || frogCoordinates.intersects(landRect2) || 
            frogCoordinates.intersects(landRect3) || frogCoordinates.intersects(landRect4) || 
            frogCoordinates.intersects(landRect5)) {

          if (frogCoordinates.intersects(landRect1) && !platformFilled[0]) {
              frog.setPosition(38, 185);
              platformFilled[0] = true;
          } else if (frogCoordinates.intersects(landRect2) && !platformFilled[1]) {
              frog.setPosition(215, 185);
              platformFilled[1] = true;
          } else if (frogCoordinates.intersects(landRect3) && !platformFilled[2]) {
              frog.setPosition(392, 185);
              platformFilled[2] = true;
          } else if (frogCoordinates.intersects(landRect4) && !platformFilled[3]) {
              frog.setPosition(569, 185);
              platformFilled[3] = true;
          } else if (frogCoordinates.intersects(landRect5) && !platformFilled[4]) {
              frog.setPosition(746, 185);
              platformFilled[4] = true;
          }

          // Add frog to landed list and reset to spawn
          landedFrogs.add(new Frog(frog.getX(), frog.getY(), frogImage, frogDown, frogLeft, frogRight));
          frog.reset();
        }

        // If not on a log or turtle and in water, reset
        if (!onLog) {
          if (frogCoordinates.y >= 60 && frogCoordinates.y < 488) {
              frog.reset();
          }
        }

        // If all platforms are filled, level up
        if (allPlatformsFilled()) {
          level++;
          for (Car c : cars) {
              c.increaseSpeed();
          }
          for (Log l : logs) {
              l.increaseSpeed();
          }
          for (Turtle t : turtles) {
              t.increaseSpeed();
          }
          Arrays.fill(platformFilled, false);
          landedFrogs.clear();
          frog.reset();
        }

        // Repaint the screen
        repaint();
   }

   // Paint the game screen
   @Override
   public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 824, 888);
        
        // Draw river
        Color cc = Color.decode("#0A0F3D");
        g.setColor(cc);
        g.fillRect(0, 140, 824, 388);
        g.drawImage(fp,0,140,824,100,null);

        // Draw platforms and road
        g.drawImage(p, 0, 788, 824,50,null);
        g.drawImage(p, 0, 488, 824,50,null);

        // Draw all game elements
        for (Log l : logs) {
            l.draw(g);
        }
        for (Car c : cars) {
            c.draw(g);
        }
        for (Turtle t : turtles) {
            t.draw(g);
        }
        frog.draw(g);

        g.setColor(Color.WHITE);
   }
}
