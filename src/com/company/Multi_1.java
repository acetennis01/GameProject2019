package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Multi_1 extends JPanel implements KeyListener, MouseListener{
    public int angle1, angle2;
    public int pointX1, pointY1;
    public int pointX2, pointY2;
    public ArrayList<Integer> keyCode;
    public RockManager1 rockManager1;
    public RockManager2 rockManager2;
    public Image background, bird;
    public boolean hit;
    public int point1, point2;
    public double birdHealth;
    public int randomX, randomY;
    public Multi_1()
    {
        hit = false;

        bird = new ImageIcon("Bird.png").getImage();
        background = new ImageIcon("Background.png").getImage();

        keyCode = new ArrayList<Integer>();
        pointY1 = 650;
        pointX1 = 1100;
        pointY2 = 650;
        pointX2 = 300;

        angle1 = 90;
        angle2 = 90;

        birdHealth = 25;

        rockManager1 = new RockManager1();
        rockManager2 = new RockManager2();

        point1 = 0;
        point2 = 0;

        randomX = (int)(Math.random()*600 + 0);
        randomY = (int)(Math.random()*400 + 0);

        requestFocus();
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        requestFocusInWindow();
        if(!keyCode.contains(e.getKeyCode()))
            keyCode.add(new Integer(e.getKeyCode()));
        calculatePoints();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCode.remove(new Integer(e.getKeyCode()));
    }

    public void calculatePoints()
    {
        if(keyCode.contains(new Integer(KeyEvent.VK_RIGHT)))
            angle1 = angle1 - 4;
        if(keyCode.contains(new Integer(KeyEvent.VK_LEFT)))
            angle1 = angle1 + 4;
        if(keyCode.contains(new Integer(KeyEvent.VK_A)))
            angle2 = angle2 + 4;
        if(keyCode.contains(new Integer(KeyEvent.VK_D)))
            angle2 = angle2 - 4;
        if(keyCode.contains(new Integer(KeyEvent.VK_SHIFT)))
            rockManager1.add(new Rock(angle1));
        if(keyCode.contains(new Integer(KeyEvent.VK_SPACE)))
            rockManager2.add(new Rock2(angle2));

        pointY1 = (int)(Math.sin(Math.toRadians(angle1))*100);
        pointX1 = (int)(Math.cos(Math.toRadians(angle1))*100);
        pointY1 = 750 - pointY1;
        pointX1 = 1100 + pointX1;

        pointY2 = (int)(Math.sin(Math.toRadians(angle2))*100);
        pointX2 = (int)(Math.cos(Math.toRadians(angle2))*100);
        pointY2 = 750 - pointY2;
        pointX2 = 300 + pointX2;

        repaint();
    }
    //class used to draw the rocks based on the velocity and the angle
    class Rock
    {
        private int angle;
        public int rockX, rockY;
        private double total;
        private Timer rockTimer1;
        private boolean reflect;
        //constructor to create the Timer
        public Rock(int angle_)
        {
            rockX = 0;
            rockY = 750;
            angle = angle_;
            rockTimer1 = new Timer(5, new RockMover());
            rockTimer1.start();
            if(angle >= 90)
                reflect = true;
            else
                reflect = false;
        }
        //calculates the trajectory
        public void move()
        {
            //850 ,330 , 150, 70
            if(angle == 90)
            {
                rockY-= 4;
                if((rockX+1100<=0||rockX+1100>=1400) || (rockY<=0||rockY>=800)) {
                    rockTimer1.stop();
                    rockManager1.removeFirst();

                }
                //640 ,130 , 200, 90,
                if((rockX+1115)>=(randomX+300)&&(rockX+1115<=(randomX+500))&&((rockY-15>=(randomY+100)&&rockY-15<(randomY+180))||(rockY+15>=(randomY+100)&&rockY+15<(randomY+180))))
                {
                    //System.out.println("hit");
                    hit=true;
                    rockTimer1.stop();
                    rockManager1.removeFirst();
                    point2++;
                    randomX = (int)(Math.random()*600 + 0);
                    randomY = (int)(Math.random()*400 + 0);
                    birdHealth--;
                }
            }
            else
            {
                total = (Math.tan(Math.toRadians(angle)))*rockX;
                //System.out.println("RockX: "+ rockX +" RockY: " + rockY);
                rockY = (int)total;
                rockY=(750 - rockY);

                if((rockX+1100<=0||rockX+1100>=1400) || (rockY<=0||rockY>=800)) {
                    rockTimer1.stop();
                    rockManager1.removeFirst();

                }
                //640 ,130 , 200, 90,
                if((rockX+1115)>=(randomX+300)&&(rockX+1115<=(randomX+500))&&((rockY-15>=(randomY+100)&&rockY-15<(randomY+180))||(rockY+15>=(randomY+100)&&rockY+15<(randomY+180))))
                {
                    //System.out.println("hit");
                    hit=true;
                    rockTimer1.stop();
                    rockManager1.removeFirst();
                    point2++;
                    randomX = (int)(Math.random()*600 + 0);
                    randomY = (int)(Math.random()*400 + 0);
                    birdHealth--;
                }

                //System.out.println(prop+"");
                if(reflect)
                {
                    if(angle < 120 && angle > 60)
                        rockX--;
                    if((angle < 150 && angle > 120)||(angle < 60 && angle > 30))
                        rockX=rockX-6;
                    else
                        rockX=rockX-10;
                }

                else
                {
                    if(angle < 120 && angle > 60)
                        rockX++;
                    if((angle < 150 && angle > 120)||(angle < 60 && angle > 30))
                        rockX=rockX+6;
                    else
                        rockX=rockX+10;
                }
            }

            repaint();
        }
        // draws the rock on the panel
        public void draw(Graphics g)
        {
            g.setColor(Color.BLACK);
            //draws the rock
                g.fillOval(rockX+1090,rockY,15,15);
            //**************************************************
            g.drawString(""+rockX +" "+rockY,rockX+1100,rockY);
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
    class RockManager1
    {
        Rock[] rocks;
        int rockIndex;
        //constructor creates the array
        public RockManager1()
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
    class Rock2
    {
        private int angle;
        public int rockX, rockY;
        private double total;
        private Timer rockTimer2;
        private boolean reflect;
        //constructor to create the Timer
        public Rock2(int angle_)
        {
            rockX = 0;
            rockY = 750;
            angle = angle_;
            rockTimer2 = new Timer(5, new RockMover2());
            rockTimer2.start();
            if(angle >= 90)
                reflect = true;
            else
                reflect = false;
        }
        //calculates the trajectory
        public void move()
        {
            //850 ,330 , 150, 70
            if(angle == 90)
            {
                rockY-= 4;
                if((rockX+1100<=0||rockX+1100>=1400) || (rockY<=0||rockY>=800)) {
                    rockTimer2.stop();
                    rockManager2.removeFirst();

                }
                //640 ,130 , 200, 90,
                if((rockX+1115)>=(randomX+300)&&(rockX+1115<=(randomX+500))&&((rockY-15>=(randomY+100)&&rockY-15<(randomY+180))||(rockY+15>=(randomY+100)&&rockY+15<(randomY+180))))
                {
                    //System.out.println("hit");
                    hit=true;
                    rockTimer2.stop();
                    rockManager2.removeFirst();
                    point1++;
                    randomX = (int)(Math.random()*600 + 0);
                    randomY = (int)(Math.random()*400 + 0);
                    birdHealth--;
                }
            }
            else
            {
                total = (Math.tan(Math.toRadians(angle)))*rockX;
                //System.out.println("RockX: "+ rockX +" RockY: " + rockY);
                rockY = (int)total;
                rockY=(750 - rockY);

                if((rockX+300<=0||rockX+300>=1400) || (rockY<=0||rockY>=800)) {
                    rockTimer2.stop();
                    rockManager2.removeFirst();
                }
                if((rockX+265)>=(randomX+300)&&(rockX+265<=(randomX+500))&&((rockY-15>=(randomY+100)&&rockY-15<(randomY+180))||(rockY+15>=(randomY+100)&&rockY+15<(randomY+180))))
                {
                    //System.out.println("hit");
                    hit=true;
                    rockTimer2.stop();
                    rockManager2.removeFirst();
                    point1++;
                    randomX = (int)(Math.random()*600 + 0);
                    randomY = (int)(Math.random()*400 + 0);
                    birdHealth--;
                }

                //System.out.println(prop+"");
                if(reflect)
                {
                    if(angle < 120 && angle > 60)
                        rockX--;
                    if((angle < 150 && angle > 120)||(angle < 60 && angle > 30))
                        rockX=rockX-6;
                    else
                        rockX=rockX-10;
                }

                else
                {
                    if(angle < 120 && angle > 60)
                        rockX++;
                    if((angle < 150 && angle > 120)||(angle < 60 && angle > 30))
                        rockX=rockX+6;
                    else
                        rockX=rockX+10;
                }
            }

            repaint();
        }
        // draws the rock on the panel
        public void draw(Graphics g)
        {
            g.setColor(Color.BLACK);
            //draws the rock
            g.fillOval(rockX+290,rockY,15,15);
            //**************************************************
            g.drawString(""+rockX +" "+rockY,rockX+1100,rockY);
            //System.out.println("RockX: "+ rockX +" RockY: " + rockY);
        }

        //This class is used to call the move method
        class RockMover2 implements ActionListener
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
    class RockManager2
    {
        Rock2[] rocks;
        int rockIndex;
        //constructor creates the array
        public RockManager2()
        {
            rocks = new Rock2[10];
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
        public void add(Rock2 toAdd) {
            if (rockIndex >= rocks.length) {
                Rock2[] temp = new Rock2[rocks.length * 2];
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

    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        requestFocusInWindow();
    }

    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(background,0,0,1400,800,null);
        g.drawImage(bird,randomX+300 ,randomY+100 , 200, 90, null);

        hit = false;
        //draws the hit box of the bird
        // System.out.println(""+birdHealth);

        g2.drawRect(400, 75, 600, 25);
        g2.setColor(new Color(((int)(10*(25-birdHealth))),((int)(250-(25-birdHealth)*10)),0 ));
        g2.fillRect(400,75,(int)(600*(birdHealth/25)), 25);


        rockManager1.drawRocks(g);
        rockManager2.drawRocks(g);


        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(1100,750,pointX1,pointY1);
        g2.drawLine(300,750,pointX2,pointY2);

        g2.setFont(new Font("Serif", Font.BOLD, 25));
        g2.setColor(Color.BLUE);
        g2.drawString("Player 1", 100, 100);
        g2.setColor(Color.RED);
        g2.drawString("Player 2", 1150, 100);

        g2.setColor(Color.BLUE);
        if(point1 <= 1)
            g2.drawString("Point: " + point1, 100, 500);
        else
            g2.drawString("Points: " + point1, 100, 500);

        g2.setColor(Color.RED);
        if(point2 <= 1)
            g2.drawString("Point: " + point2, 1150, 500);
        else
            g2.drawString("Points: " + point2, 1150, 500);

    }
}
