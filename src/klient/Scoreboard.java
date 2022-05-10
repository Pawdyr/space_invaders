package klient;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;


/**
 * Klasa zajmująca się tablicą wyników
 */
public class Scoreboard{
    HashMap<String, Integer> scores;
    Properties prop = new Properties();

    /**
     * Konstruktor
     */
    public Scoreboard() {
        scores = new HashMap<>();

    }

    /**
     * Dodawanie rekordu do tablicy
     * @param nick nazwa gracza
     * @param score uzyskane punkty
     */
    public void addScore(String nick, int score){
        if (scores.containsKey(nick) && scores.get(nick) < score) scores.replace(nick, score);
        else scores.put(nick, score);
        scores = sortScores();
    }

    /**
     * Funkcja sortująca mapę wyników
     * @return posortowana mapa
     */
    private HashMap<String, Integer> sortScores() {
        HashMap<String, Integer> temp = new HashMap<>();
        scores.entrySet()
                .stream()
                .sorted(HashMap.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> temp.put(x.getKey(), x.getValue()));
        return temp;
    }

    /**
     * Wczytywanie plików z pliku
     * @throws IOException wyjątek rzucany w przypadku nieznaleziena pliku
     */
    public void loadScores() throws IOException {
        prop.load(new FileInputStream("../wyniki.properties"));
        prop.forEach((k,v) -> scores.put(k.toString(),Integer.parseInt(v.toString())));
        scores = sortScores();
    }

    /**
     * Zapis wyników do pliku
     * @throws IOException wyjątek rzucany w przypadku niemożliwości otwarcia pliku
     */
    public void saveScores() throws IOException {
        scores = sortScores();
        scores.forEach((k,v) -> prop.put(k, v.toString()));
        prop.store(new FileOutputStream("../wyniki.properties"), null);
    }

    /**
     * Wyświetlanie tablicy wyników
     */
    public void displayScoreboard() {
        JFrame scoreFrame = new JFrame();
        scoreFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        scoreFrame.setSize(new Dimension(Game.mcr.windowSize[1], Game.mcr.windowSize[0]));
        scoreFrame.setVisible(true);
        scoreFrame.setTitle("TABLICA WYNIKÓW");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        scores.forEach((k,v) -> listModel.addElement(k + ": " + v.toString()));

        JList<String> list = new JList<>(listModel);
        scoreFrame.add(list);
    }

}
