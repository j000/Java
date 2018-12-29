import java.util.function.Consumer;

/**
 * Interfejs pozwala na identyfikację obiektu
 */
public interface FallingObjectBasicInterface {
    /**
     * Metoda zwraca unikalny numer identyfikacyjny obiektu.
     * @return unikalny numer identyfikacyjny
     */
    public long getUniqID();

    /**
     * Metoda pozwala na sprawdzenie czy przechwycenie obiektu jest obowiązkowe.
     * @return true - obiekt musi zostać przechwycony, false - przechwycenie obiektu
     * nie jest wymagane.
     */
    public boolean mandatoryInterception();

    /**
     * Metoda zwraca wartość punktową obiektu.
     * @return liczba punktów, którą można zdobyć przechwytując obiekt
     */
    public long getValue();

    /**
     * Metoda zwraca początkowe położenie obiektu
     * @return początkowe położenie obiektu
     */
    public Position2D getInitialLocation();

    /**
     * Metoda rejestruje obiekt, który zostanie poinformowany
     * o zmianie położenia tego obiektu.
     *
     * @param listener referencja od obiektu, który będzie
     *                 informowany o zmienie położenia
     */
    public void addLocationListener( Consumer<Position2D> listener );

}
