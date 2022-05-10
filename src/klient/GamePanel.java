package klient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa reprezentująca panel na którym rozgrywa się rozgrywka
 */
public class GamePanel extends JPanel implements ActionListener{
    /** Wyzwalacz ActionListenera */
    private final Timer timer = new Timer(Game.mcr.delay, this);
    /** Statek gracza */
    private Cannon cannon;
    /** Położenie działa statku gracza */
    private int cannonGun;
    /** Dane z plików kopnfigureacyjnych dotyczące konkretnego poziomu */
    private final LevelConfigReader lcr;
    /** Zagnieżdżona ArrayLista ze statkami przeciwników */
    private final ArrayList<ArrayList<Enemy>> enemies = new ArrayList<>();
    /** Położenie statku w stosunku do szerokości panelu */
    private double cannonRefPos;
    /** Położenie statku w stosunku do rozmiarów panelu */
    private double firstEnemyRefX, firstEnemyRefY;
    /** ArrayList z pocikami statku gracza */
    private final ArrayList<Projectile> friendlyProjectiles = new ArrayList<>();
    /** ArrayList z pocikami statków wrogich */
    private final ArrayList<Projectile> enemyProjectiles = new ArrayList<>();
    /** Po ilu ticknięciach timera strzeli działo gracza */
    private int cannonFireDelay = 0;
    /** Wynik w danym poziomie */
    private int score;
    private final Level l;
    /** Ilość przeciwników do zestrzelenia w poziomie */
    private int enemiesToKill;
    /** Flaga sprawdzająca czy wrogowie dotkneli dołu panelu */
    private boolean enemyPassedCannon = false;

    /**
     * Tworzenie panelu gry
     * @param lcr  czytnik parametrów poziomu
     * @param score  wynik z poprzedniego poziomu
     * @param l
     */
    GamePanel(LevelConfigReader lcr, int score, Level l) {
        setVisible(true);
        setFocusable(true);
        this.lcr = lcr;
        this.score = score;
        this.l = l;
    }


    /**
     * Inicjowanie wszystkich obiektów w panelu gry
     */
    public void init(int prevLevelScore, int prevLevelHealth) {
        //tworzenie działa
        setFocusable(true);
        score = prevLevelScore;
        ArrayList<Enemy> temp = new ArrayList<>();
        cannon = new Cannon(
                (getWidth()-Game.mcr.cannonSize)/2,
                getHeight()-50,
                Game.mcr.cannonSize,
                prevLevelHealth);

        cannonRefPos = cannon.x_corr/(double) getWidth();

        firstEnemyRefX = 1.0 / Game.mcr.enemiesInRow;
        firstEnemyRefY = 1.0 / (2.5 * lcr.enemyNumber);


        // tworzenie wrogów
        for (int i = 0; i < lcr.enemyNumber; i++) {
            for (int j = 0; j < Game.mcr.enemiesInRow; j++) {
                temp.add(new Enemy(
                        (int) (firstEnemyRefX * j * getWidth()),
                        (int) (firstEnemyRefY * i * getHeight()),
                        Game.mcr.enemySize));
            }
            enemies.add(new ArrayList<>(temp));
            temp.clear();
        }

        enemiesToKill = enemies.size() * enemies.get(0).size();
        timer.start();

        //reagowanie na zmianę rozmirów okna
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                cannon.y_corr = getHeight()-50;
                cannon.x_corr = (int) (getWidth() * cannonRefPos);
                cannonGun = cannon.x_corr + cannon.size/2;

                // wiersze
                for (int i = 0; i < enemies.size(); i++) {
                    // kolumny
                    for (int j = 0; j < enemies.get(i).size(); j++) {
                        enemies.get(i).get(j).setPosition(
                                (int) (firstEnemyRefX * j * getWidth()),
                                (int) (firstEnemyRefY * i * getHeight())
                        );
                    }
                }

            }
        });

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_A) cannon.moveLeft = true;

                if (key == KeyEvent.VK_D) cannon.moveRight = true;

                if (key == KeyEvent.VK_ESCAPE) {
                    timer.stop();
                    InfoPanel.setPause(true);
                }

                if (key == KeyEvent.VK_R) {
                    timer.start();
                    InfoPanel.setPause(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_A) cannon.moveLeft = false;

                if (key == KeyEvent.VK_D) cannon.moveRight = false;
            }
        });
        System.out.println("GamePanel init() called");
    }

    /** Rysowanie elementów panelu gry */
    public void paintComponent(Graphics g){
        paintCannon(g);
        paintProjectiles(g);
        paintEnemies(g);
    }
    /** Rysowanie działa */
    private void paintCannon(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(
                cannon.x_corr,
                cannon.y_corr,
                cannon.size,
                cannon.size);
        g.setColor(new Color(20, 29, 102));
        g.fillRect(
                cannonGun - 4,
                cannon.y_corr - 8,
                8,
                16);
    }

    /** Rysowanie pocisków */
    private void paintProjectiles(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Projectile p : friendlyProjectiles) {
            g.fillRect(
                    p.x_corr,
                    p.y_corr,
                    p.size,
                    p.size);
        }

        g.setColor(Color.RED);
        for (Projectile p : enemyProjectiles) {
            g.fillRect(
                    p.x_corr,
                    p.y_corr,
                    p.size,
                    p.size);
        }
    }

    /** Rysowanie wrogów */
    private void paintEnemies(Graphics g) {
        g.setColor(new Color(168,24,24));
        for (int i = 0; i < lcr.enemyNumber; i++) {
            for (int j = 0; j < Game.mcr.enemiesInRow; j++) {
                if (enemies.get(i).get(j).isAlive()) {
                    g.fillRect(
                            enemies.get(i).get(j).x_corr,
                            enemies.get(i).get(j).y_corr,
                            enemies.get(i).get(j).size,
                            enemies.get(i).get(j).size);
                }
            }
        }
    }

    /** Kod wykonywany co wywołanie przez timer */
    @Override
    public void actionPerformed(ActionEvent e) {
        cannonMovement();
        projectileMovement();
        enemyMovement();
        checkForFailConditions();
        try {
            checkForWinConditions();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        updateRefPositions();
        repaint();
    }

    /** Sprawdzanie warunków wygranej */
    private void checkForWinConditions() throws IOException {
        if (enemiesToKill == 0){
            timer.stop();
            l.winLevel(score, cannon.health);
        }
    }

    /** Sprawdzanie warunków przegranej */
    private void checkForFailConditions() {
        if (cannon.health <= 0 || enemyPassedCannon){
            timer.stop();
            l.fail(score);
        }
    }

    /** Aktualizoniwe pozycji odniesienia elementów */
    private void updateRefPositions() {
        //zmiana pozycji odniesienia elementów
        cannonGun = cannon.x_corr + cannon.size/2;
        cannonRefPos = cannon.x_corr / (float) getWidth();

        firstEnemyRefX = 1.0 / Game.mcr.enemiesInRow;
        firstEnemyRefY = 1.0 / (2.5 * lcr.enemyNumber);
    }

    /** Ruch statku gracza i kontrola ich zderzeń */
    private void cannonMovement() {
        cannonFireDelay++;

        //ruch działa
        if (cannon.x_corr < 0) cannon.x_corr = 0;
        if (cannon.x_corr > (this.getWidth() - cannon.size)) cannon.x_corr = (this.getWidth() - cannon.size);
        cannon.move();


        //strzelanie działa
        if (cannonFireDelay > Game.mcr.fireRate) {
            friendlyProjectiles.add(new Projectile(
                    cannonGun - Game.mcr.projectileSize/2,
                    cannon.y_corr,
                    Game.mcr.projectileSize,
                    "cannon"));
            cannonFireDelay = 0;
        }
    }

    /** Ruch pocisków */
    private void projectileMovement() {
        friendlyProjectiles.forEach(Projectile::move);

        for (Projectile ep : enemyProjectiles) {
            ep.move();
            if (cannon.collision(ep)) {
                cannon.reduceHealth(lcr.enemyBombDamage);
                ep.kill();
                InfoPanel.updateCannonHealth(cannon.health);
            }
        }

        friendlyProjectiles.removeIf(fp ->
                !fp.isAlive() ||
                        fp.x_corr < 0);

        enemyProjectiles.removeIf(ep ->
                !ep.isAlive() ||
                        ep.y_corr > getHeight());

    }
    /** Ruch przeciwników i kontrola ich zderzeń */
    private void enemyMovement(){
        //pętla iterująca po wierszach
        for (ArrayList<Enemy> enemyRow : enemies) {
            // pętla iterująca po kolumnach
            for (Enemy enemy : enemyRow) {
                enemy.move();
                if (enemy.y_corr > getHeight() - enemy.size) { enemyPassedCannon = true; }
                if (enemy.shoot() && enemy.isAlive()) {
                    enemyProjectiles.add(new Projectile(
                            enemy.x_corr + (enemy.size - Game.mcr.projectileSize) / 2,
                            enemy.y_corr + enemy.size,
                            Game.mcr.projectileSize,
                            "enemy"));
                }
                for (Projectile p : friendlyProjectiles) {
                    if (enemy.collision(p) && enemy.isAlive()) {
                        enemy.kill();
                        enemiesToKill--;
                        p.kill();
                        score += lcr.scoreForKill;
                        InfoPanel.updateScore(score);
                    }
                }
            }
        }
    }

}
