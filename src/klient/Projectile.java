package klient;

/**
 * Klasa reprezentująca pocisk
 */

public class Projectile extends Entity{

    private final String origin;

    /**
     * Tworzenie pocisku
     * @param x  pozycja w poziomie
     * @param y  pozycja w pionie
     * @param size  rozmiar pocisku
     * @param origin  kto wystrzelił pocisk
     */
    public Projectile(int x, int y, int size, String origin){
        super(x,y,size);
        this.origin = origin;
    }

    @Override
    public void move() {
        if (origin.equals("cannon")) this.y_corr -= Game.mcr.projectileSpeed;
        if (origin.equals("enemy"))  this.y_corr += Game.mcr.projectileSpeed;
    }
}
