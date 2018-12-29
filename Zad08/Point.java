import java.util.Arrays;

public class Point {
    private final int DIMENSIONS;
    private final int[] position;;

    public Point(int dimensions) {
        DIMENSIONS = dimensions;
        position = new int[DIMENSIONS];
    }

    /**
     * Metoda ustawia pozycje dimention na wartość value.
     * 
     * @param dimention numer współrzędnej, której wartość jest modyfikowana
     * @param value     wartość wpisywana do współrzędnej dimention
     */
    public void setPosition(int dimention, int value) {
        position[dimention] = value;
    }

    /**
     * Metoda zwraca współrzędną dimention.
     * 
     * @param dimention numer współrzędnej, której wartość jest odczytywana
     * @return wartość współrzędnej
     */
    public int getPosition(int dimention) {
        return position[dimention];
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
