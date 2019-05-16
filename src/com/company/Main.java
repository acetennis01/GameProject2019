package com.company;
/*
Abhiram Annaluru
Period: 7
*/

import javax.swing.*;
import java.awt.*;


//This class is just used to create Frame and used for the main
public class Main extends JFrame{
    static CardLayout card = new CardLayout();
    static JPanel panel = new JPanel();
    JFrame frame = new JFrame("SlingShot Shooter") ;
    static int points;
    static CardFormat cF = new CardFormat();
    //used to create the Frame
    public Main()
    {
        panel.setLayout(card);
        points = 0;
        //CardFormat cF = new CardFormat();
        //frame = new JFrame("SlingShot Shooter");//This needs to be in the constructor
        frame.setSize( 1400, 800);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(0,0);
        frame.setResizable(true);
        //Level1 pan = new Level1();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    //used to run the program
    public static void main (String [] args)
    {
        Main JoeBobKim = new Main(); //needed to run constructor
    }
}




