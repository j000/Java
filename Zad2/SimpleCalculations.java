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
		return null; // oczywiście, do zmiany, ale bez tej linijki kompilator sygnalizuje błąd
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
		return null; // oczywiście, do zmiany, ale bez tej linijki kompilator sygnalizuje błąd
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
		return null; // oczywiście, do zmiany, ale bez tej linijki kompilator sygnalizuje błąd
	}
}
// vim: tabstop=4 shiftwidth=0 noexpandtab
