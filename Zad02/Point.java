/**
 * Klasa Punkt reprezentuje punkt w n-wymiarowej przestrzeni.
 */
public class Point {
	private int dimensions;
	private double[] position;

	public String toString()
	{
		String out = "dims: " + dimensions + ", (";
		for (int i = 0; i < dimensions; ++i) {
			out += position[i] + ",";
		}
		out += "\b)";
		return out;
	}

	public int hashCode()
	{
		return java.util.Arrays.hashCode(position);
	}

	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Point)) {
			return false;
		}
		Point test = (Point)o;
		if (test.getNumberOfDimensions() != getNumberOfDimensions())
			return false;

		final double EPS = 1e-10;
		for (int i = 0; i < getNumberOfDimensions(); ++i) {
			if (Math.abs(test.getPosition(i) - getPosition(i)) > EPS) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Metoda pozwala na ustawienie liczby wymiarów.
	 * Efektem ubocznym jest ustawienie wszystkich współrzędnych
	 * na 0. Metoda ta musi zostać wywołana przed setPosition
	 * oraz getPosition.
	 * @param dimensions liczba wymiarów
	 */
	public void setNumberOfDimensions(int dimensions)
	{
		this.dimensions = dimensions;
		this.position = new double[dimensions];
	}

	/**
	 * Metoda zwraca liczbe wymiarów.
	 *
	 * @return liczba wymiarów
	 */
	public int getNumberOfDimensions()
	{
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
	public void setPosition(int dimension, double value)
	{
		position[dimension] = value;
	}

	/**
	 * Za pomocą tej metody możliwe jest pobranie informacji o
	 * wartości określonej współrzędnej.
	 * @param dimension numer wymiaru. Dozwolone są liczby
	 *                  od 0 do liczba wymiarów - 1
	 * @return wartość współrzędnej
	 */
	public double getPosition(int dimension)
	{
		return position[dimension];
	}
}
// vim: tabstop=4 shiftwidth=0 noexpandtab
