package klient;

/**
 * Klasa reprezentująca statki wrogów
 */
public class Enemy extends Entity {

    private int sinArg = 0;
    private int moveDownCounter = 0;

    /**
     * Tworzenie przeciwnika
     * @param x  położenie w poziomie
     * @param y  położenie w pionie
     * @param size rozmiar statku
     */
    Enemy(int x, int y, int size) {
        super(x,y,size);
    }

    @Override
    public void move() {
        moveDownCounter++;
        if (moveDownCounter == 7) {
            y_corr += 1;
            moveDownCounter = 0;
        }

        sinArg += 2.25;
        // nie mogłem zrozumieć tego javowego sinusa ale przy takich wartościach działa
        x_corr += (1.4 * Math.sin(Math.toRadians(sinArg)) + 0.5);
    }

    /** Funkcja określająca kiedy wróg strzela */
    public boolean shoot(){
        return Math.random() >= 0.995;
    }
}
