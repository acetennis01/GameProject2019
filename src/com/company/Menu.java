package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// class for the opening page
public class Menu extends JPanel implements ActionListener
{
    JButton start, instructions, leaderBoard;
    public Image bird, slingshot, background;
    public JLabel welcome, north2, north3;
    public JPanel center, north;
    public Timer rockTimer;
    public int rockX, rockY;
    //constructor for the Menu class, add all the buttons and the background
    public Menu()
    {
        //frame.setSize(1000, 800);
        RockMover rockMover = new RockMover();
        rockTimer = new Timer(1, rockMover);
        rockTimer.start();

        rockX =0;
        rockY =0;
        setLayout(new BorderLayout());
        GridLayout grid = new GridLayout(5,1);
        center = new JPanel();
        center.setLayout(grid);

        start = new JButton("Start");
        start.setActionCommand("start");
        start.addActionListener(this);

        FillerCenter fillerCenter1 = new FillerCenter();
        FillerCenter fillerCenter2 = new FillerCenter();
        FillerCenter fillerCenter3 = new FillerCenter();
        FillerCenter fillerCenter4 = new FillerCenter();

        fillerCenter1.setOpaque(false);
        fillerCenter2.setOpaque(false);
        fillerCenter3.setOpaque(false);
        fillerCenter4.setOpaque(false);

        center.add(start);
        center.add(fillerCenter1);
        center.add(fillerCenter2);
        center.add(fillerCenter3);
        center.add(fillerCenter4);

        slingshot = new ImageIcon("SlingShot.png").getImage();
        bird = new ImageIcon("Bird.png").getImage();
        background = new ImageIcon("Background.png").getImage();

        north = new JPanel();
        north.setPreferredSize(new Dimension(800, 140));
        north.setLayout(new GridLayout(3,1));

        Font f = new Font("TimesRoman", 75, 50);

        north2 = new JLabel();
        north3 = new JLabel();

        welcome = new JLabel("",JLabel.CENTER);
        welcome.setFont(f);
        welcome.setText("SlingShot Shooter");

        start = new JButton("Start");
        start.setActionCommand("start");
        start.addActionListener(this);

        north.setOpaque(false);
        north2.setOpaque(false);
        north2.setOpaque(false);
        welcome.setOpaque(false);

        center.setOpaque(false);

        north.add(north2);
        north.add(welcome);
        north.add(north3);

        add(north,BorderLayout.NORTH);
        add(new JLabel(),BorderLayout.SOUTH);
        add(new FillerSide(),BorderLayout.WEST);
        add(new FillerSide(),BorderLayout.EAST);
        add(center, BorderLayout.CENTER);
    }

    //class for the rock trajectory
    class RockMover implements ActionListener
    {
        private int angle, velocity;
        private double num, denom, tan, total;
        //takes in the angle and the velocity as a parameter
        public RockMover()
        {
            angle = 35;
            velocity = 130;

        }
        //used to calculate the next point on the cartesian plane and modified to fit the computer plane
        public void actionPerformed(ActionEvent e)
        {
            num=(9.8*Math.pow(rockX,2));
            denom=(2*Math.pow((velocity*Math.cos(Math.toRadians(angle))),2));
            tan = (Math.tan(Math.toRadians(angle))*rockX);
            total = -(num/denom) + tan;
            rockY = (int)total;
            //System.out.println("RockX: "+ rockX +" RockY: " + rockY);
            rockY=550-rockY;

            if(rockX>=1400)
                rockX =0;

            //System.out.print("Soy aqui");
            repaint();
            //System.out.println(prop+"");
            rockX=rockX+3;
        }
    }

    //used to put the Listener on the buttons
    public void actionPerformed(ActionEvent evt)
    {
        //what I do when the start button is pressed
        if(evt.getActionCommand().equals("start"))
        {
            Main.card.show(Main.panel, "Choosing Player");

        }
    }
    //Filler JLabel for the empty spaces for formatting(East and West)
    class FillerSide extends JLabel
    {
        //constructor is used to simply set the size of the JLabel
        public FillerSide()
        {
            setPreferredSize(new Dimension(333,750));
        }
    }
    //Filler JLabel for the empty spaces for formatting(Center)
    class FillerCenter extends JLabel
    {
        //constructor is used to simply set the size of the JLabel
        public FillerCenter()
        {
            setPreferredSize(new Dimension(333,150));
        }
    }
    //this method is used to draw the rocks, slingshot, and the bird
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background,0,0,1500,800,null);
        //draws the slingshot
        if(rockX <= 3)
        {
            try{

                Thread.sleep(1000);
                g.fillOval(rockX+15,rockY,15,15);
            }
            catch (InterruptedException e)
            {
                System.err.print("");
            }
        }
        else
            g.fillOval(rockX+15,rockY,15,15);
        g.drawImage(slingshot,15,495,90,100,null);

        //draws the bird
        g.drawImage(bird,1240 ,330 , 200, 90, null);
        //for the rock
        //g.fillOval(rockX+15,rockY,15,15);

    }
}

