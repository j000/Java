/**
 * Klasa Punkt reprezentuje punkt w n-wymiarowej przestrzeni.
 */
public class Point {
    private int dimensions;
    private double[] position;

    /**
     * Metoda pozwala na ustawienie liczby wymiarów.
     * Efektem ubocznym jest ustawienie wszystkich współrzędnych
     * na 0. Metoda ta musi zostać wywołana przed setPosition
     * oraz getPosition.
     * @param dimensions liczba wymiarów
     */
    public void setNumberOfDimensions( int dimensions ) {
        this.dimensions = dimensions;
        this.position = new double[ dimensions ];
    }

    /**
     * Metoda zwraca liczbe wymiarów.
     *
     * @return liczba wymiarów
     */
    public int getNumberOfDimensions() {
        return dimensions;
    }


    /**
     * Metoda pozwala na ustawienie podanej współrzędnej
     * na określoną wartość.
     *
     * @param dimension numer wymiaru. Dowolone są liczby
     *                  od 0 do liczba wymiarów - 1
     * @param value nowa wartość dla współrzędnej dimention
     */
    public void setPosition( int dimension, double value ) {
        position[ dimension ] = value;
    }

    /**
     * Za pomocą tej metody możliwe jest pobranie informacji o
     * wartości określonej współrzędnej.
     * @param dimension numer wymiaru. Dozwolone są liczby
     *                  od 0 do liczba wymiarów - 1
     * @return wartość współrzędnej
     */
    public double getPosition( int dimension ) {
        return position[ dimension ];
    }
}
