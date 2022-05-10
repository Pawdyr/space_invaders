package klient;
/**
 * Abstrakcyjna klasa istot w grze
 */
public abstract class Entity {

    protected int x_corr;
    protected int y_corr;
    protected int size;
    protected boolean alive;

    public Entity(int x_corr, int y_corr, int size) {
        this.x_corr = x_corr;
        this.y_corr = y_corr;
        this.size = size;
        this.alive = true;
    }

    /**
     * Ustawia dane położenie istoty
     * @param newXCorr  położenie w poziomie
     * @param newYCorr  położenie w pionie
     */
    public void setPosition(int newXCorr, int newYCorr) {
        x_corr = newXCorr;
        y_corr = newYCorr;
    }

    /**
     * Określanie czy istoty kolidują ze sobą
     * @param e  Istota z którą może kolidować
     * @return kolizia lub jej brak
     */
    public boolean collision(Entity e){
        return this.x_corr < e.x_corr + e.size &&
                this.x_corr + this.size > e.x_corr &&
                this.y_corr < e.y_corr + e.size &&
                this.y_corr + this.size > e.y_corr;
    }

    public void kill() { alive = false; }
    public boolean isAlive() { return alive; }

    /** Zmiana współżędnych położenia istoty zgodnie z jej drogą ruchu */
    public abstract void move();
}
