package org.matheus.calc.visao;

import org.matheus.calc.modelo.Memoria;
import org.matheus.calc.modelo.MemoriaObservador;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel implements MemoriaObservador {
    private JLabel label = new JLabel();

    public Display(){
        Memoria.getInstance().adicionarObservador(this);
        setBackground(new Color(46,49,50));
        label = new JLabel(Memoria.getInstance().getTextAtual() );
        label.setForeground(Color.white);
        label.setFont(new Font("courier",Font.PLAIN,30));

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10,25));

        add(label);
    }

    @Override
    public void valorAlterado(String novoValor) {
        label.setText(novoValor);
    }
}
