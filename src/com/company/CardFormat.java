package com.company;

import javax.swing.*;

//class is used to control the Panels, using CardLayout
public class CardFormat extends JPanel
{
    //calls the level classes and add them to the cardLayout
    public CardFormat()
    {
        Main.panel.setLayout(Main.card);

        Menu menu = new Menu();
        ChoosingPlayer choosingPlayer = new ChoosingPlayer();
        Level1 level1 = new Level1();
        Level2 level2 = new Level2();
        Level3 level3 = new Level3();
        Level4 level4 = new Level4();
        Level5 level5 = new Level5();

        Multi_1 multi_1 = new Multi_1();

        Main.panel.add(menu,"Menu");
        Main.panel.add(choosingPlayer,"Choosing Player");
        Main.panel.add(level1, "Level 1");
        Main.panel.add(level2, "Level 2");
        Main.panel.add(level3,"Level 3");
        Main.panel.add(level4,"Level 4");
        Main.panel.add(level5,"Level 5");
        Main.panel.add(multi_1,"Multi_1");

        Main.card.show(Main.panel, "Menu");
    }
}