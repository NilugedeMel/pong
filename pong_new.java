//  import libraries
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class pong_new extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

        //  global variables
        //  object variables
        private Image db_image;
        private Graphics dbg;
        Image background;

        //  instance variables
        private int speed;
        Boolean game = false;

        int current_line;
        int x_pos = 30;
        int y_pos = 100;
        int x_speed = 1;
        int y_speed = 1;
        int radius = 10;
        int applet_size_x = 400;
        int applet_size_y = 300;
        int mouse_x = 0;
        int mouse_y = 0;
        int score = 0;
        int life = 3;

        public void init () {
                setBackground (Color.orange);

                background = getImage (getCodeBase (), "bg.gif");

                current_line = 10;

                addKeyListener (this);
                addMouseListener (this);
                addMouseMotionListener (this);
        }

        public void start () {
                Thread th = new Thread (this);
                th.start ();
        }

        public void stop () {

        }

        public void keyPressed (KeyEvent e) {
                if (e.getKeyChar() == 'r'){
                        x_pos = 0;
                        y_pos = 0;
                        x_speed = 1;
                        y_speed = 1;
                }
                current_line += 20;
        }

        public void keyReleased (KeyEvent e) {

        }

        public void keyTyped (KeyEvent e) {

        }

        public void mouseClicked (MouseEvent e) {

        }

        public void mouseEntered (MouseEvent e) {

        }

        public void mouseExited (MouseEvent e) {

        }

        public void mousePressed (MouseEvent e) {
                if (game == false) {
                        x_speed = 1;
                        y_speed = -1;
                        game = true;
                }
                if (life == 0) {
                        x_pos = 0;
                        y_pos = 0;
                        x_speed = 1;
                        y_speed = 1;
                        life = 3;
                        score = 0;
                        game = false;
                }
        }

        public void mouseReleased (MouseEvent e) {

        }

        public void mouseMoved (MouseEvent e) {
                mouse_x = e.getX ();
                mouse_y = e.getY ();
        }

        public void mouseDragged (MouseEvent e) {

        }


        public void destroy () {

        }

        public void run () {
                Thread.currentThread ().setPriority (Thread.MIN_PRIORITY);

                while (true) {
                        if (x_pos > applet_size_x - radius) {
                                if (x_speed == 1) {
                                        x_speed = -1;
                                }
                                if (x_speed == 2){
                                        x_speed = -2;
                                }
                        }
                        else if (x_pos < radius) {
                                if (x_speed == -1) {
                                        x_speed = 1;
                                }
                                if (x_speed == -2){
                                        x_speed = 2;
                                }
                        }
                        else if (y_pos < radius) {
                                y_speed = 1;
                        }
                        else if (y_pos > applet_size_y - radius) {
                                death ();
                        }
                        if (game == true) {


                                x_pos += x_speed;
                                y_pos += y_speed;

                                if (y_pos >= 280 - (radius)) {
                                                if ((x_pos + (radius) >= mouse_x - 40) && (x_pos+(radius) <= mouse_x - 30)) {
                                                        score++;
                                                        y_speed =- 1;
                                                        x_speed =- 2;
                                                        java.awt.Toolkit.getDefaultToolkit().beep();

                                                }
                                                if ((x_pos + (radius) >= mouse_x - 30) && (x_pos + (radius) <= mouse_x)) {
                                                        score++;
                                                        y_speed =- 1;
                                                        x_speed =- 1;
                                                        java.awt.Toolkit.getDefaultToolkit().beep();

                                                }
                                                if ((x_pos + (radius) >= mouse_x) && (x_pos + (radius) <= mouse_x + 30)) {
                                                        score++;
                                                        y_speed =- 1;
                                                        x_speed = 1;
                                                        java.awt.Toolkit.getDefaultToolkit ().beep ();

                                                }
                                                if ((x_pos + (radius) >= mouse_x + 30) && (x_pos+(radius) <= mouse_x + 40)) {
                                                        score++;
                                                        y_speed =- 1;
                                                        x_speed = 2;
                                                        java.awt.Toolkit.getDefaultToolkit ().beep ();
                                                }
                                }
                        }

                        if (game == false) {
                                x_pos = mouse_x;
                                y_pos = 279 - radius;
                        }

                        repaint ();

                        try {
                                Thread.sleep (5);
                        }
                        catch (InterruptedException ex) {
                                // do nothing
                        }
                        Thread.currentThread ().setPriority (Thread.MAX_PRIORITY);
                }
        }

        public void update (Graphics g) {
                if (db_image == null) {
                        db_image = createImage (this.getSize().width, this.getSize().height);
                        dbg = db_image.getGraphics ();
                }

                dbg.setColor (getBackground ());
                dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);

                dbg.setColor (getForeground ());
                paint (dbg);

                g.drawImage (db_image, 0, 0, this);
        }

        public void paint (Graphics g)
        {
                if (life > 0) {
                        g.drawImage (background, 0, 0, this);

                        g.setColor (Color.YELLOW);

                        g.fillOval (x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);

                        g.setColor (new Color(12, 123, 140));

                        g.fillRect (mouse_x - 40, 280, 80, 10);

                        g.setFont (new Font ("Comic Sans MS", Font.PLAIN, 20));

                        g.setColor (Color.YELLOW);

                        g.drawString ("Score: "+score, 5, 20);

                        g.drawString ("Life: "+life, 330, 20);
                }
                if (life==0) {
                        g.setColor (Color.RED);
                        g.fillRect (0, 0, 400, 400);
                        g.setColor (Color.RED);
                        g.setFont (new Font ("Times New Roman", Font.BOLD, 48));
                        g.drawString ("GAME OVER", 50, 150);
                        g.setFont (new Font ("Times New Roman", Font.PLAIN, 12));
                        g.drawString ("To Try Again, Press Screen", 130, 160);
                        g.drawString ("Your Score Was: "+score, 155, 170);
                }
        }

        public void death () {
                game = false;
                y_pos = 279 - radius;
                life --;

        }
}
