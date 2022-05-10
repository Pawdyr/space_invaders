package klient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Klasa reprezentująca menu główne gry i ustawiająca jego wygląd
 */
public class Menu extends JPanel implements ActionListener {

    private final JButton playButton;
    private final JButton scoreButton;
    private final JButton exitButton;
    private final Scoreboard scoreboard;
    private int score = 0;

    /** Ustawianie wyglądu menu */
    public Menu() throws IOException {

        this.setLayout(new GridLayout(3,1));

        playButton = new JButton(Game.mcr.menuOptions[0]);
        scoreButton = new JButton(Game.mcr.menuOptions[1]);
        exitButton = new JButton(Game.mcr.menuOptions[4]);

        playButton.addActionListener(this);
        scoreButton.addActionListener(this);
        exitButton.addActionListener(this);

        this.add(playButton);
        this.add(scoreButton);
        this.add(exitButton);

        scoreboard = new Scoreboard();

        try { scoreboard.loadScores(); }
        catch (IOException e) {System.out.println("Brak pliku z wynikami");}
    }
    /** Przypisanie akcji do guzików */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playButton){
            askAndRun();
            System.out.println("Gra");
        }
        else if(e.getSource() == scoreButton) {
            scoreboard.displayScoreboard();
        }

        else if(e.getSource() == exitButton) {
            try {
                scoreboard.saveScores();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }
    /** zapytanie o nick gracza w nowej ramce i uruchomienie poziomu */
    private void askAndRun(){
        JFrame ask = new JFrame();
        ask.setLayout(new GridLayout(3,1));
        Dimension d = new Dimension(
                Game.mcr.windowSize[1]/3,
                Game.mcr.windowSize[0]/3);
        ask.setSize(d);
        ask.setVisible(true);
        ask.setLocationRelativeTo(playButton);
        ask.setTitle(Game.mcr.gameName);
        ask.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ask.setFocusable(true);

        JTextPane askForNick = new JTextPane();
        askForNick.setText("Podaj nick: ");
        askForNick.setEditable(false);

        JTextField nickField = new JTextField();
        nickField.setFont(new Font("Dialog",0,30));
        JButton acceptButton = new JButton("Akceptuj");

        ask.add(askForNick);
        ask.add(nickField);
        ask.add(acceptButton);

        acceptButton.addActionListener(e -> {
            String nick = nickField.getText();
            ask.dispose();

            try {
                startLevel(nick);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /** włączanie kolejnego poziomu
     * @param nick  nick gracza
     */
    private void startLevel(String nick) throws IOException {
        Level l = new Level(nick, score, scoreboard);
        l.loadNextLevel(0,0,Game.mcr.playerHealth);
    }
}
