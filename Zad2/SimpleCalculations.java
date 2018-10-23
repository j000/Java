// Jarosław Rymut
// Zadanie 02
class SimpleCalculations {
	/**
	 * Metoda zwraca tablicę zawierającą point obiektów typu Point. Punkty
	 * znajdujące się w tablicy mają znajdować się w równych odległościach
	 * na odcinku łączącym firstPoint i secondPoint. Odległość pomiędzy
	 * punktami ma być taka sama jak odległość pomiędzy
	 * firstPoint a pierwszym z wygenerowanych punktów oraz
	 * secondPoint a ostatnim z wygenerowanych punktów.
	 *
	 * @param firstPoint  pierwszy punkt z dwóch, pomiędzy którymi należy wygenerować te
	 *                    zwracane w wyniku
	 * @param secondPoint drugi punkt z dwóch,  pomiędzy którymi należy wygenerować te
	 *                    zwracane w wyniku
	 * @param points      liczba punktów w tablicy będącej wynikiem pracy metody
	 * @return tablica obiektów typu Point znajdujących się równych odległościach pomiędzy
	 * firstPoint a secondPoint.
	 */
	public Point[] equidistantPoints(
		Point firstPoint, Point secondPoint, int points)
	{
		if (firstPoint == null || secondPoint == null || points == 0)
			return null;

		Point[] output = new Point[points];
		for (int loopCounter = 0; loopCounter < points; ++loopCounter) {
			output[loopCounter] = new Point();
			output[loopCounter].setNumberOfDimensions(
				firstPoint.getNumberOfDimensions());
		}

		for (int dimension = 0; dimension < firstPoint.getNumberOfDimensions();
			 ++dimension) {
			double step = (secondPoint.getPosition(dimension)
						   - firstPoint.getPosition(dimension))
				/ (points + 1);
			for (int j = 0; j < points; ++j) {
				output[j].setPosition(
					dimension,
					(j + 1) * step + firstPoint.getPosition(dimension));
			}
		}

		return output;
	}

	/**
	 * Metoda zwraca położenie środka geometrycznego wyliczonego na podstawie
	 * przekazanego zbioru punktów. Środek geometryczny to średnia
	 * arytmetyczna położeń poszczególnych punktów.
	 * <a href="https://pl.wikipedia.org/wiki/%C5%9Arodek_masy">Środek geometryczny</a>
	 *
	 * @param points zbiór punktów
	 * @return środek geometryczny zbioru points
	 */
	public Point geometricCenter(Point[] points)
	{
		if (points == null || points.length == 0)
			return null;

		Point result = new Point();
		result.setNumberOfDimensions(points[0].getNumberOfDimensions());

		for (int dimension = 0; dimension < result.getNumberOfDimensions();
			 ++dimension) {
			double coordinate = 0;
			for (int p = 0; p < points.length; ++p) {
				coordinate += points[p].getPosition(dimension) / points.length;
			}
			result.setPosition(dimension, coordinate);
		}
		return result;
	}

	private double square(double x)
	{
		return x * x;
	}

	/**
	 * Metoda zwraca obiekt klasy Point reprezentujący punkt znajdujący się na prostej
	 * przechodzącej przez firstPoint oraz secondPoint w odległości
	 * distance od punktu secondPoint.
	 *
	 * @param firstPoint  pierwszy punkt
	 * @param secondPoint drugi punkt
	 * @param distance    odleglosc od punktu secondPoint w jakiej ma sie znajdowac punkt bedacy wynikie
	 *                    pracy metody
	 * @return punkt znajdujacy się w odległości distance od secondPoint na prostej przechodzącej
	 * przez firstPoint i secondPoint.
	 */
	public Point next(Point firstPoint, Point secondPoint, double distance)
	{
		if (firstPoint == null || secondPoint == null)
			return null;

		if (distance == 0)
			return secondPoint;

		Point result = new Point();
		result.setNumberOfDimensions(firstPoint.getNumberOfDimensions());

		double length = 0;
		for (int dimension = 0; dimension < result.getNumberOfDimensions();
			 ++dimension) {
			length += square(
				secondPoint.getPosition(dimension)
				- firstPoint.getPosition(dimension));
		}
		length = Math.sqrt(length);
		for (int dimension = 0; dimension < result.getNumberOfDimensions();
			 ++dimension) {
			result.setPosition(
				dimension,
				secondPoint.getPosition(dimension)
					+ distance
						* (secondPoint.getPosition(dimension)
						   - firstPoint.getPosition(dimension))
						/ length);
		}
		return result;
	}
}
// vim: tabstop=4 shiftwidth=0 noexpandtab
