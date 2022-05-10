package klient;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Klasa odczytująca wartości z plików konfiguracyjnych szczegółowych dla danego poziomu
 * i udostepniające je tym poziomom
 */
public class LevelConfigReader {
    private final Properties p;

    /** Numer poziomu */
    public String level;
    /** Ilość wrogów w poziomie */
    public int enemyNumber;
    /** Obrażenia wrogich pocisków */
    public int enemyBombDamage;
    /** prędkość wrogów */
    public int enemyMovementTime;
    /** Częstotliwość strzelania */
    public int bombFreq;
    /** Ilość uleczeń */
    public int heal;
    /** Ilość szybkostrzelności */
    public int fastShoot;
    /** Punkty za zabicie przeciwnika */
    public int scoreForKill;

    /**
     * Tworzenie odczytywacza
     * @param path  cieżka do danych
     * @throws IOException
     */
    public LevelConfigReader(String path) throws IOException {
        FileReader reader = new FileReader(path);
        p = new Properties();
        p.load(reader);

        System.out.println("Level Config Reader object created.");

        getProperties();
    }

    /** Czytaanie danych */
    private void getProperties(){
        level = p.getProperty("frameTitle");level = p.getProperty("frameTitle");
        enemyNumber = Integer.parseInt(p.getProperty("enemy_number"));
        enemyBombDamage = Integer.parseInt(p.getProperty("enemy_bomb_damage"));
        enemyMovementTime = Integer.parseInt(p.getProperty("enemy_movement_time"));
        bombFreq = Integer.parseInt(p.getProperty("bomb_frequency"));
        heal = Integer.parseInt(p.getProperty("health_kit"));
        fastShoot = Integer.parseInt(p.getProperty("fast_shooting"));
        scoreForKill = Integer.parseInt(p.getProperty("score_for_kill"));
    }
}
