package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
//class for level 3
public class Level4 extends JPanel implements MouseMotionListener, MouseListener, ActionListener
{
    public final int yInt;
    public int birdY;
    public int getX, getY;
    public int prop;
    public int counter, rocksLeft;
    public Timer rockTimer, birdTimer;
    public JSlider powerSlider;
    public boolean shot, hit;
    public boolean goingdown, goingup;
    public Image bird, slingshot, homePic, homePicOG, background;
    public JLabel rocksLeftLabel, levelLabel, pointLabel;
    public JPanel north, northLeft;
    public JButton homeButton;
    public RockManager rockManager;

    //used to add images, sliders, labels,and buttons to the Panel
    public Level4()
    {
        //frame.setSize(1000, 800);
        setLayout(new BorderLayout());
        setBackground(Color.CYAN);
        prop = 3;
        counter = 0;
        //points = 0;
        rocksLeft = 5;
        hit = false;
        shot = false;
        yInt = 550;

        //birdX = 1280;
        birdY = 52;

        hit = false;
        shot = false;

        goingup = false;
        goingdown = true;

        BirdMover birdMover = new BirdMover();
        birdTimer = new Timer(3, birdMover);
        birdTimer.start();


        homePicOG = new ImageIcon("HomeButtons.png").getImage();
        bird = new ImageIcon("Bird.png").getImage();
        slingshot = new ImageIcon("SlingShot.png").getImage();
        background = new ImageIcon("Background.png").getImage();

        north = new JPanel(new GridLayout(1,4));
        north.setOpaque(false);
        northLeft = new JPanel(new GridLayout(1,5));
        northLeft.setOpaque(false);

        //south = new JPanel()

        powerSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 3);
        powerSlider.setMajorTickSpacing(5);
        powerSlider.setMinorTickSpacing(1);
        powerSlider.setForeground(Color.BLACK);
        powerSlider.setPaintTicks(true);
        powerSlider.setLabelTable(powerSlider.createStandardLabels(1));
        powerSlider.setPaintLabels(true);
        powerSlider.addChangeListener(new Bozo());
        powerSlider.setOpaque(false);
        powerSlider.setBackground(Color.WHITE);


        homePic = homePicOG.getScaledInstance(44, 44, Image.SCALE_DEFAULT);

        homeButton = new JButton();
        homeButton.setActionCommand("Home");
        try {
            homeButton.setIcon(new ImageIcon(homePic));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        homeButton.setPreferredSize(new Dimension(40,40));
        rocksLeftLabel = new JLabel("Rocks Left: 5",JLabel.CENTER);
        rocksLeftLabel.setPreferredSize(new Dimension(355,40));
        levelLabel = new JLabel("Level: 4", JLabel.CENTER);
        levelLabel.setPreferredSize(new Dimension(355,40));
        rocksLeftLabel.setOpaque(false);
        pointLabel = new JLabel("Points: "+ Main.points, JLabel.CENTER);
        pointLabel.setOpaque(false);

        homeButton.setOpaque(false);
        homeButton.addActionListener(this);

        northLeft.add(homeButton);
        northLeft.add(new JLabel());
        northLeft.add(new JLabel());
        northLeft.add(new JLabel());
        northLeft.add(new JLabel());

        //rocksLeftLabel.setOpaque(false);
        //north.setBorderPainted();

        north.add(northLeft);
        north.add(levelLabel);
        north.add(rocksLeftLabel);
        north.add(powerSlider);

        rocksLeftLabel.setPreferredSize(new Dimension(355,40));
        levelLabel.setPreferredSize(new Dimension(355,40));

        add(north, BorderLayout.NORTH);

        addMouseMotionListener(this);
        addMouseListener(this);

        rockManager = new RockManager();
    }
    //This class is used to put a listener on the slider and get the values that the use gives
    class Bozo implements ChangeListener
    {
        //changes the velocity
        public void stateChanged(ChangeEvent e)
        {
            prop = powerSlider.getValue();
        }
    }
    //class to move the bird up and down
    class BirdMover implements ActionListener
    {
        //call this method in the Timer
        public void actionPerformed(ActionEvent e)
        {
            if(hit)
            {
                birdTimer.stop();
            }
            if(goingdown)
            {
                //System.out.println("At Going down");
                birdY++;
                if(birdY>=610)
                {
                    birdY=610;
                    goingdown = false;
                }
            }
            else if(!goingdown)
            {
                //System.out.println("At Going up");
                birdY--;
                if(birdY<=135)
                {
                    birdY=135;
                    //goingup = false;
                    goingdown = true;
                }
            }
            //System.out.println("Going Up: "+goingup+" Going down: "+goingdown);
            repaint();
        }
    }
    //class used to draw the rocks based on the velocity and the angle
    class Rock
    {
        private int angle, velocity;
        public int rockX, rockY, slingPos;
        private double num, denom, tan, total;
        private Timer rockTimer1;
        //constructor to create the Timer
        public Rock(int angle_)
        {
            rockX = 0;
            rockY = 500;
            angle = angle_;
            rockTimer1 = new Timer(1, new RockMover());
            rockTimer1.start();
            velocity = (prop-1)*25 + 50;
        }
        //calculates the trajectory
        public void move()
        {
            //850 ,330 , 150, 70
            num=(9.8*Math.pow(rockX,2));
            denom=(2*Math.pow((velocity*Math.cos(Math.toRadians(angle))),2));
            tan = (Math.tan(Math.toRadians(angle))*rockX);
            total = -(num/denom) + tan;
            rockY = (int)total;
            //System.out.println("RockX: "+ rockX +" RockY: " + rockY);
            rockY=yInt-rockY;

            if(rockX>=1400 || rockY>800) {
                rockTimer1.stop();
                rockManager.removeFirst();
                //System.out.println("Level 1 timer stopped");
            }
            if((rockX+15)>=1280&&(rockX+15<=1440)&&((rockY-15>=(birdY)&&rockY-15<(birdY+90))||(rockY+15>=(birdY)&&rockY+15<(birdY+90))))
            {
                System.out.println("hit");
                hit=true;
                rockTimer1.stop();
                rockManager.removeFirst();
            }
          /*
          if(((rockX+15>=1250&&rockX<=1400)&&(rockY>=330&&rockY<=400))||
                  rockX+15>=1250&&(rockY-15>=330&&rockY-15<=400))
          {
              hit=true;
              rockTimer.stop();

          }
          */
            //System.out.println(prop+"");
            rockX=rockX+prop;
        }
        // draws the rock on the panel
        public void draw(Graphics g)
        {
            g.setColor(Color.BLACK);
            //draws the rock
            if(shot)
                g.fillOval(rockX+50,rockY,15,15);
            //**************************************************
            g.drawString(""+rockX +" "+rockY,rockX,rockY);
            //System.out.println("RockX: "+ rockX +" RockY: " + rockY);
        }

        //This class is used to call the move method
        class RockMover implements ActionListener
        {

            //used to call the move method
            public void actionPerformed(ActionEvent e)
            {
                move();
                //System.out.println("Level 1 X: " + rockX +  " Y: " + rockY + "prop : " + prop);
                repaint();
            }
        }
    }
    //class is used to manage the rocks(because there can be more than one rock on the panel)
    class RockManager
    {
        Rock[] rocks;
        int rockIndex;
        //constructor creates the array
        public RockManager()
        {
            rocks = new Rock[10];
            rockIndex = 0;
        }
        //calls the draw method based on the rock
        public void drawRocks(Graphics g)
        {
            for (int i = 0; i < rockIndex; i++)
            {
                rocks[i].draw(g);
            }
        }
        //add the newly made rock to the array
        public void add(Rock toAdd) {
            if (rockIndex >= rocks.length) {
                Rock[] temp = new Rock[rocks.length * 2];
                for (int i = 0; i < rocks.length; i++) temp[i] = rocks[i];
                rocks = temp;
            }

            rocks[rockIndex] = toAdd;
            rockIndex++;
        }
        // remove the first rbc and decrease the current index by one
        public void removeFirst() {
            rocks[0] = null;
            for (int i = 0; i < rockIndex - 1; i++) {
                rocks[i] = rocks[i + 1];
            }
            rockIndex--;
        }
    }

    //Submethod of MouseMotionListener
    public void mouseDragged(MouseEvent e) { }
    //Submethod of MouseMotionListener, and is used to get the point if the mouse to calculate the angle
    public void mouseMoved(MouseEvent e)
    {
        getX = e.getX();
        getY = e.getY();

        repaint();
        //System.out.println("Mouse Moved X: "+getX+" Y: "+getY);
    }
    //Submethod of MouseListener
    public void mouseEntered(MouseEvent e) { }
    //Submethod of MouseListener
    public void mouseExited(MouseEvent e) { }
    //Submethod of MouseListener, used to make the rock move once the mouse is clicked
    public void mousePressed(MouseEvent e)
    {
        if(counter < 5)
        {
            System.out.println("Pressed");

            if (rockTimer != null)
                rockTimer.stop();
            shot = true;
            hit = false;
            int angle = (int)(Math.toDegrees(Math.atan2(((double)(yInt-e.getY())),(double)(e.getX()))));
            //System.out.println(angle+"");
            rockManager.add(new Rock(angle));
            counter++;
            rocksLeft--;
            repaint();
        }
    }
    //Submethod of MouseListener
    public void mouseClicked(MouseEvent e) { }
    //Submethod of MouseListener
    public void mouseReleased(MouseEvent e) { }
    //used to draw the all components of the game including the background, rock, and change the text of the Labels
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background,0,0,1400,800,null);
        g.setColor(Color.BLACK);
        rockManager.drawRocks(g);
        g.drawString("X: "+getX+" Y: "+getY, getX,getY);
        //**************************************************
        //draws the slingshot
        g.drawImage(slingshot,15,495,90,100,null);
        //draws the bird
        g.drawImage(bird,1240 ,birdY , 200, 90, null);
        //draws the hit box of the bird
        //g.drawRect(850,330,150,70);
        Font f = new Font("Serif", Font.BOLD, 100);
        g.setFont(f);
        //writes the text when the bird is hit
        if(hit) {
            g.drawString("You hit the bird", 233, 509);
            new Thread(new Runnable() {
                @Override
                //sleeping the thread to make the text above last for longer
                public void run()
                {
                    try
                    {
                        Thread.sleep(4000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(900);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    Main.card.show(Main.panel,"Level 5");

                    hit = false;
                    counter = 0;
                    rocksLeft = 5;
                }
            }).start();
        }


        rocksLeftLabel.setText("Rocks Left: "+ rocksLeft);
        pointLabel.setText("Points: "+ Main.points);
        //233 509
        //System.out.println("X: "+getX+" Y: "+getY);
        //rocksLeftLabel, levelLabel, pointLabel
    }
    //Used to go back to the menu page
    public void actionPerformed(ActionEvent e)
    {
        //String command = e.getActionCommand();
        //label.setText("Button 2");
        if(e.getActionCommand().equals("Home"))
            Main.card.show(Main.panel,"Menu");

    }
}
