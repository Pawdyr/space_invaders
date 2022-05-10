package klient;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa reprezentująca panel z informacjami
 */
public class InfoPanel extends JPanel{

    private static final JTextPane scorePane = new JTextPane();
    private static final JTextPane healthPane = new JTextPane();
    private static final JTextPane pausePane = new JTextPane();
    private int score;
    private int health;

    /**
     * Tworzenie panelu z informacjami
     * @param d  rozmiar panelu
     * @param nick_  nick gracza
     * @param level_  uruchomiony poziom
     */
    public InfoPanel(Dimension d, String nick_, int level_) {
        setLayout(new GridLayout(5,1));
        setPreferredSize(d);
        setFocusable(false);
        Font font = new Font("Dialog", Font.PLAIN, 20);

        //wyświetlanie nicku
        JTextPane nickPane = new JTextPane();
        nickPane.setEditable(false);
        nickPane.setFont(font);
        nickPane.setText("GRACZ: "+ nick_);

        //wyświetlanie poziomu
        JTextPane levelPane = new JTextPane();
        levelPane.setEditable(false);
        levelPane.setFont(font);
        levelPane.setText("POZIOM: "+ (level_+1)+"/5");

        //wyświetlanie punktacji
        scorePane.setEditable(false);
        scorePane.setFont(font);
        scorePane.setText("PUNKTY: " + score);

        //wyświetlanie punktów życia
        healthPane.setEditable(false);
        healthPane.setFont(font);
        healthPane.setText("ŻYCIE: " + health + "/100");

        //informacja o pauzie
        pausePane.setEditable(false);
        pausePane.setFont(font);
        pausePane.setText("ESC - pauza");

        add(nickPane);
        add(levelPane);
        add(scorePane);
        add(healthPane);
        add(pausePane);
    }

    /**
     * Aktualizacja życia gracza
     * @param health  nowe życie
     */
    public static void updateCannonHealth(int health) { healthPane.setText("ŻYCIE: " + health + "/100"); }

    /**
     * Aktualizacja wyniku
     * @param score  nowy wynik
     */
    public static void updateScore(int score) {
        scorePane.setText("PUNKTY: " + score);
    }

    /**
     * Wyświetlanie informacji o pauzie
     * @param on pauza włączona czy nie
     */
    public static void setPause(boolean on) {
        if (on) pausePane.setText("PAUZA - R by wznowić");
        if (!on) pausePane.setText("ESC - pauza");
    }

    public void init(int initScore, int initHealth) {
        health = initHealth;
        score = initScore;
    }

}
