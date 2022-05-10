package klient;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Klasa pobierająca dane z plików konfiguracyjnych wspólnych w całej grze
 * i udostępniające je dla innych częśc programu
 */
public class MenuConfigReader {

    private final Properties p;
    /** opóźnienie gry */
    public int delay;
    /** nazwa gry */
    public String gameName;
    /** Ilość poziomów gry */
    public int levelCount;
    /** Rozmiar statków wroga */
    public int enemySize;
    /** Rozmiar statku gracza */
    public int cannonSize;
    /** Rozmiar pocisków */
    public int projectileSize;
    /** Życie gracza */
    public int playerHealth;
    /** Prędkość statku gracza */
    public int cannonSpeed;
    /** Częstotliwosć strzelania */
    public int fireRate;
    /** Rozmiry okna gry: height - 0, width - 1 */
    public int[] windowSize;
    /** Ścieżki do danych konfiguracyjnych poziomów */
    public String[] levelConfigs;
    /** Opcje menu */
    public String[] menuOptions;
    /** Autorzy gry */
    public String[] authors;
    /** Prędkość pociksów */
    public int projectileSpeed;
    /**  Ilość wrogów w rzędzie */
    public int enemiesInRow;

    /**
     * Tworzenie odczytywacza
     * @param path  ścieżka do danych
     * @throws IOException
     */
    public MenuConfigReader(String path) throws IOException {
        FileReader reader = new FileReader(path);
        p = new Properties();
        p.load(reader);

        System.out.println("ConfigReader object created");

        getProperties();
    }

    /** Czytanie danych */
    private void getProperties() {
        delay = Integer.parseInt(p.getProperty("delay"));
        gameName = p.getProperty("game");
        levelCount = Short.parseShort(p.getProperty("level_count"));
        enemySize = Integer.parseInt(p.getProperty("enemy_size"));
        cannonSize = Integer.parseInt(p.getProperty("cannon_size"));
        projectileSize = Integer.parseInt(p.getProperty("projectile_size"));
        playerHealth = Integer.parseInt(p.getProperty("player_health"));
        cannonSpeed = Integer.parseInt(p.getProperty("cannon_speed"));
        fireRate = Integer.parseInt(p.getProperty("cannon_fire_rate"));
        projectileSpeed = Integer.parseInt(p.getProperty("projectile_speed"));
        enemiesInRow = Integer.parseInt(p.getProperty("enemies_in_row"));

        final String author1 = p.getProperty("author1");
        final String author2 = p.getProperty("author2");

        authors = new String[]{author1, author2};

        final int height = Integer.parseInt(p.getProperty("window_height"));
        final int width = Integer.parseInt(p.getProperty("window_width"));

        windowSize = new int[]{height, width};

        final String lv1_config = p.getProperty("level1_config_path");
        final String lv2_config = p.getProperty("level2_config_path");
        final String lv3_config = p.getProperty("level3_config_path");
        final String lv4_config = p.getProperty("level4_config_path");
        final String lv5_config = p.getProperty("level5_config_path");

        levelConfigs = new String[]{lv1_config, lv2_config, lv3_config, lv4_config, lv5_config};

        final String o_start = p.getProperty("option_start");
        final String o_settings = p.getProperty("option_settings");
        final String o_scoreboard = p.getProperty("option_score");
        final String o_rules = p.getProperty("option_rules");
        final String o_exit = p.getProperty("option_exit");

        menuOptions = new String[]{o_start, o_scoreboard, o_rules, o_settings, o_exit};
    }
}
