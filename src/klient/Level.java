package klient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Klasa reprezentująca okno poziomu
 */
public class Level extends JFrame {
    /** Nick gracza */
    private final String nick;
    /** Panel gry */
    private GamePanel gPanel;
    /** Panel z informacjami */
    private InfoPanel iPanel;
    /** Punkty gracza */
    private int score;
    /** Dane konfiguracyjne konkretnego poziomu */
    private LevelConfigReader lcr;
    /** Aktualnie uruchomiony poziom */
    private int currentLevel = 0;

    private Scoreboard scoreboard;



    /**
     * Tworzenie okna poziomu
     * @param nick  nick gracza
     * @param score   punkty z poprzedniego poziomu
     * @throws IOException -
     */
    public Level(String nick, int score, Scoreboard sc) throws IOException {

        this.nick = nick;
        this.score = score;
        this.scoreboard = sc;

        setSize(new Dimension(Game.mcr.windowSize[1],Game.mcr.windowSize[0]));
        setTitle(Game.mcr.gameName);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Przegranie poziomu
     * @param finalScore  wynik końcowy
     */
    public void fail(int finalScore) {
        JFrame failFrame = infoPopup("Przegrałeś!", "Twój wynik: " + finalScore);
        JButton button = new JButton("Akceptuj");
        failFrame.add(button);
        button.addActionListener((e) -> {
            scoreboard.addScore(nick, finalScore);
            failFrame.dispose();
            dispose();
        });
    }

    /**
     * Wygrana poziomu lub gry
     * @param score wynik po poziomie
     * @param health zdrowie po poziomie
     */
    public void winLevel(int score, int health) {
        currentLevel++;
        JButton button = new JButton("Akceptuj");
        if (currentLevel == 4) {
            JFrame winFrame = infoPopup("Wygrana!", "Wygrałeś grę!");
            winFrame.add(button);
            button.addActionListener(e -> {
                winFrame.dispose();
                this.dispose();
                scoreboard.addScore(nick, score+health);
            });
        }
        else {
            JFrame winFrame = infoPopup("Wygrana!", "Następny poziom?");
            winFrame.add(button);
            button.addActionListener(e -> {
                winFrame.dispose();
                this.getContentPane().removeAll();
                this.repaint();
                try {
                    loadNextLevel(currentLevel, score, health);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * Tworzenie okienka z informacjami po poziomie
     * @param titleInfo  nazwa okienka
     * @param info  informacja do wyświetlenia
     * @return okienko
     */
    private JFrame infoPopup(String titleInfo, String info) {
        JFrame infoPopUp = new JFrame();
        infoPopUp.setLayout(new GridLayout(2,1));
        infoPopUp.setSize(new Dimension(
                Game.mcr.windowSize[1]/3,
                Game.mcr.windowSize[0]/3));
        infoPopUp.setVisible(true);
        infoPopUp.setTitle(titleInfo);
        infoPopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        infoPopUp.setFocusable(true);

        JTextPane scoreInfo = new JTextPane();
        scoreInfo.setText(info);
        scoreInfo.setEditable(false);
        scoreInfo.setFont(new Font("Dialog",0,30));
        infoPopUp.add(scoreInfo);

        return infoPopUp;
    }

    /** Konfiguracja wyglądu planszy */
    public void configureFrameLayout(int level) throws IOException {
        //odczytanie plików konfiguracyjnych poziomu
        lcr = new LevelConfigReader(Game.mcr.levelConfigs[level]);
        setLayout(new BorderLayout());

        iPanel = new InfoPanel(
                new Dimension((int) (
                        this.getWidth()*0.25),
                        this.getHeight()),
                nick,
                level
        );

        gPanel = new GamePanel(lcr, score, this);
        gPanel.setPreferredSize(
                new Dimension((int) (
                        this.getWidth()*0.75),
                        this.getHeight())
        );

        this.add(gPanel, BorderLayout.CENTER);
        this.add(iPanel, BorderLayout.EAST);
    }

    /**
     * Ładowanie kolejnego poziomu
     * @param level poziom do załadowania
     * @param score punkty z poprzedniego poziomu
     * @param health życie z kolejnego poziomu
     * @throws IOException rzucany wyjątek
     */
    public void loadNextLevel(int level, int score, int health) throws IOException {
        configureFrameLayout(level);
        pack();
        InfoPanel.updateCannonHealth(health);
        InfoPanel.updateScore(score);
        gPanel.init(score, health);
    }
}

