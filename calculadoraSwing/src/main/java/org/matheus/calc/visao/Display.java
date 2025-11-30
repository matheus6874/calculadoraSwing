package org.matheus.calc.visao;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {
    private JLabel label = new JLabel();
    public Display(){
        setBackground(new Color(46,49,50));
        label = new JLabel("1234,56");
        label.setForeground(Color.white);
        label.setFont(new Font("courier",Font.PLAIN,30));

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10,25));

        add(label);
    }
}
