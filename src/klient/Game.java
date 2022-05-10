package klient;

import javax.swing.JFrame;
import java.io.IOException;

/**
 * Główna klasa gry z funkcją main
 * @author Paweł Dyrda
 * @version 1.0.0
 */
public class Game extends JFrame {
    /** obiekt z wczytanymi parametrami z plików konfiguracyjnych */
    static public MenuConfigReader mcr;

    /** tworzenie okna menu */
    public Game() throws IOException {
        add(new Menu());
        setTitle(mcr.gameName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(mcr.windowSize[1],mcr.windowSize[0]);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    public static void main(String[] args) throws IOException {
        mcr = new MenuConfigReader("..\\client_conf\\conf.properties");
        new Game();
    }
}
