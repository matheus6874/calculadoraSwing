package org.matheus.calc.visao;

import org.matheus.calc.modelo.Memoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Teclado extends JPanel implements java.awt.event.ActionListener {

    private final Color COR_CINZA_ESCURO = new Color(68, 68, 68);
    private final Color COR_CINZA_CLARO = new Color(99, 99, 99);
    private final Color COR_LARANJA = new Color(242, 173, 60);

    private final Map<String, JButton> botoes = new HashMap<>();

    public Teclado() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        c.gridwidth = 2;
        adicionarBotao("AC", COR_CINZA_ESCURO, c, 0, 0);
        c.gridwidth = 1;
        adicionarBotao("±", COR_CINZA_ESCURO, c, 2, 0);
        adicionarBotao("/", COR_LARANJA, c, 3, 0);

        adicionarBotao("7", COR_CINZA_CLARO, c, 0, 1);
        adicionarBotao("8", COR_CINZA_CLARO, c, 1, 1);
        adicionarBotao("9", COR_CINZA_CLARO, c, 2, 1);
        adicionarBotao("*", COR_LARANJA, c, 3, 1);

        adicionarBotao("4", COR_CINZA_CLARO, c, 0, 2);
        adicionarBotao("5", COR_CINZA_CLARO, c, 1, 2);
        adicionarBotao("6", COR_CINZA_CLARO, c, 2, 2);
        adicionarBotao("-", COR_LARANJA, c, 3, 2);

        adicionarBotao("1", COR_CINZA_CLARO, c, 0, 3);
        adicionarBotao("2", COR_CINZA_CLARO, c, 1, 3);
        adicionarBotao("3", COR_CINZA_CLARO, c, 2, 3);
        adicionarBotao("+", COR_LARANJA, c, 3, 3);

        c.gridwidth = 2;
        adicionarBotao("0", COR_CINZA_CLARO, c, 0, 4);
        c.gridwidth = 1;
        adicionarBotao(",", COR_CINZA_CLARO, c, 2, 4);
        adicionarBotao("=", COR_LARANJA, c, 3, 4);
    }

    private void adicionarBotao(String texto, Color cor, GridBagConstraints c, int x, int y) {
        c.gridx = x;
        c.gridy = y;

        Botao botao = new Botao(texto, cor);
        botao.addActionListener(this);
        add(botao, c);

        botoes.put(texto, botao);
    }

    private void dispararClique(String comando) {
        JButton botao = botoes.get(comando);
        if (botao != null) {
            botao.doClick();
        } else {
            Memoria.getInstance().processadorComando(comando);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton botao) {
            Memoria.getInstance().processadorComando(botao.getText());
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(this::instalarAtalhosNoRootPane);
    }

    private void instalarAtalhosNoRootPane() {
        JRootPane root = SwingUtilities.getRootPane(this);
        if (root == null) return;

        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        for (int i = 0; i <= 9; i++) {
            String key = String.valueOf(i);

            im.put(KeyStroke.getKeyStroke(key), "NUM_" + key);
            am.put("NUM_" + key, new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    dispararClique(key);
                }
            });

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 + i, 0), "NUM_" + key);
        }


        im.put(KeyStroke.getKeyStroke(","), "COMMA");
        im.put(KeyStroke.getKeyStroke('.'), "COMMA");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0), "COMMA");

        am.put("COMMA", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                dispararClique(",");
            }
        });


        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "AC");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "AC");

        am.put("AC", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                dispararClique("AC");
            }
        });


        im.put(KeyStroke.getKeyStroke('='), "EQ");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EQ");

        am.put("EQ", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                dispararClique("=");
            }
        });


        mapAtalho(im, am, "+", "PLUS", KeyEvent.VK_ADD, KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK);
        mapAtalho(im, am, "-", "MINUS", KeyEvent.VK_SUBTRACT, KeyEvent.VK_MINUS);
        mapAtalho(im, am, "*", "MULT", KeyEvent.VK_MULTIPLY, KeyEvent.VK_8, InputEvent.SHIFT_DOWN_MASK);
        mapAtalho(im, am, "/", "DIV", KeyEvent.VK_DIVIDE, KeyEvent.VK_SLASH);
    }

    private void mapAtalho(InputMap im, ActionMap am, String simbolo, String nome,
                           int key1, int key2) {

        im.put(KeyStroke.getKeyStroke(simbolo), nome);
        im.put(KeyStroke.getKeyStroke(key1, 0), nome);
        im.put(KeyStroke.getKeyStroke(key2, 0), nome);

        am.put(nome, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                dispararClique(simbolo);
            }
        });
    }

    private void mapAtalho(InputMap im, ActionMap am, String simbolo, String nome,
                           int key1, int key2, int modifier2) {

        im.put(KeyStroke.getKeyStroke(simbolo), nome);
        im.put(KeyStroke.getKeyStroke(key1, 0), nome);
        im.put(KeyStroke.getKeyStroke(key2, modifier2), nome);

        am.put(nome, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                dispararClique(simbolo);
            }
        });
    }
}
