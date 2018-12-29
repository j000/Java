/**
 * Klasa reprezentująca polożenie w dwuwymiarowej przestrzeni.
 */
public class Position2D {
	private final int x;
	private final int y;

	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Zwraca współrzędna X punktu
	 * 
	 * @return - współrzędna x punktu
	 */
	public int getX() {
		return x;
	}

	/**
	 * Zwraca współrzędna Y punktu
	 * 
	 * @return - współrzędna y punktu
	 */
	public int getY() {
		return y;
	}

	/**
	 * Klonuje punkt.
	 * 
	 * @return - zwraca obiekt o identycznych współrzędnych jak ten użyty do
	 *         klonowania
	 */
	@Override
	public Position2D clone() {
		return new Position2D(this.getX(), this.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Porownuje dwa obiekty typu punkt. Są równe jeśli współrzędne x i y się
	 * pokrywają
	 * 
	 * @return - prawda jesli dostarczony obiekt jest punktem i wspolrzedne obu
	 *         porownywanych punktow sa identyczne
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position2D other = (Position2D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position2D{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
