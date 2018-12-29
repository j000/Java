import java.util.Arrays;

public class Point {
    private static final int DIMENSIONS = 3;
    private int[] position = new int[DIMENSIONS];

    /**
     * Metoda zwraca liczbę wymiarów obsługiwanych przez obiekt Point.
     * 
     * @return liczba obsługiwanych wymiarów.
     */
    public int getNumberOfDimensions() {
        return DIMENSIONS;
    }

    /**
     * Metoda ustawia pozycje dimension na wartość value.
     * 
     * @param dimension numer współrzędnej, której wartość jest modyfikowana
     * @param value     wartość wpisywana do współrzędnej dimension
     */
    public void setPosition(int dimension, int value) {
        position[dimension] = value;
    }

    /**
     * Metoda zwraca współrzędną dimension.
     * 
     * @param dimension numer współrzędnej, której wartość jest odczytywana
     * @return wartość współrzędnej
     */
    public int getPosition(int dimension) {
        return position[dimension];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point point = (Point) o;
        return Arrays.equals(position, point.position);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(position);
    }
}
