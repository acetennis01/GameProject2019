package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosingPlayer extends JPanel implements ActionListener {
    public JButton singlePlayer, multiPlayer;
    public ChoosingPlayer()
    {
        setLayout(new GridLayout(2,1));

        singlePlayer = new JButton("Single Player");
        multiPlayer = new JButton("Multi Player");

        singlePlayer.setActionCommand("SinglePlayer");
        singlePlayer.addActionListener(this);

        multiPlayer.setActionCommand("MultiPlayer");
        multiPlayer.addActionListener(this);

        add(singlePlayer);
        add(multiPlayer);
    }
    public void actionPerformed(ActionEvent e)
    {
        //String command = e.getActionCommand();
        //label.setText("Button 2");
        if(e.getActionCommand().equals("SinglePlayer")) {
            Main.card.show(Main.panel, "Level 1");

        }
        if(e.getActionCommand().equals("MultiPlayer")){
            Main.card.show(Main.panel,"Multi_1");
            requestFocusInWindow();
        }



    }
}
