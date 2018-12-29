/**
 * Interfejs kontrolera kosza
 */
public interface BasketControlInterface {
    /**
     * Przesunięcie kosza w lewo
     */
    public void moveLeft();

    /**
     * Przesunięcie kosza w prawo
     */
    public void moveRight();

    /**
     * Aktualna pozycja środka kosza.
     * @return położenie zajmowane przez środek kosza
     */
    public Position2D getPosition();
}
