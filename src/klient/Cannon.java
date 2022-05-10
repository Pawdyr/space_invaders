package klient;

/**
 * Klasa reprezenyująca statek gracza
 */
public class Cannon extends Entity {
    public boolean moveRight;
    public boolean moveLeft;
    public int health;

    /**
     * Tworzenie działa
     * @param x położenie w poziomie
     * @param y położenie w pionie
     * @param size rozmiar działa
     */
    public Cannon(int x, int y, int size, int h) {
        super(x,y,size);

        moveLeft = false;
        moveRight = false;
        health = h;
    }

    /** Zmniejszanie ilości życia statku
     * @param healthPoints - ilość punktów do odjęcia
     */
    public void reduceHealth(int healthPoints) {
        health -= healthPoints;
    }

    @Override
    public void move() {
        if (moveRight) x_corr += Game.mcr.cannonSpeed;
        if (moveLeft) x_corr -= Game.mcr.cannonSpeed;
    }
}
