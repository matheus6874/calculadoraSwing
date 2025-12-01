package org.matheus.calc.visao;

import javax.swing.*;
import java.awt.*;

public class Botao extends JButton {

    private final Color corOriginal;
    private final Color corFlash = new Color(180, 180, 180);

    public Botao(String texto, Color cor){
        setText(texto);
        setFont(new Font("courier", Font.PLAIN, 25));
        setOpaque(true);
        setBackground(cor);
        setForeground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.corOriginal = cor;
    }

    @Override
    public void doClick(int pressTime) {
        flash();
        super.doClick(pressTime);
    }

    @Override
    public void doClick() {
        flash();
        super.doClick();
    }

    private void flash() {
        setBackground(corFlash);

        Timer timer = new Timer(120, e -> setBackground(corOriginal));
        timer.setRepeats(false);
        timer.start();
    }
}
