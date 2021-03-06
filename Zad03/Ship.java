import java.util.Vector;

/**
 * Klasa reprezentująca okręt o określonym rozmiarze. Obiekty posiadają pole
 * shipwreck, które pozwala określić stan w jakim okręt się znajduje.
 */
public class Ship {
	private boolean shipwreck;
	private int size;
	private static ShipSizeLimit limit;
	private static Vector<Ship>[] otherShips;

	private Ship(int _size)
	{
		size = _size;
	}

	/**
	 * Metoda pozwala na ustalenie limitu ilości jednostek. Wywołanie metody
	 * setRules powoduje rozpoczęcie generowanie obiektów Ship od stanu
	 * oznaczającego brak okrętów.
	 *
	 * @param limit obiekt klasy ShipSizeLimit zawierający limity liczby
	 *     okrętów.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void setLimit(ShipSizeLimit limit)
	{
		Ship.limit = limit;
		otherShips = new Vector[limit.getNumberOfSizes() + 1];
		// C++: for(Vector<Ship>& i : otherShips) { i = new Vector<Ship>(); }
		for (int i = 0; i < otherShips.length; ++i) {
			otherShips[i] = new Vector<Ship>();
		}
	}

	/**
	 * Metoda zwraca określoną liczbę okrętów danego rozmiaru. Metoda nie
	 * pozwala na posiadanie przez użytkownika większej liczby sprawnych
	 * obiektów Ship niż wynosi limit. Przekroczenie liczby okrętów danego
	 * rozmiaru powoduje, że metoda getShip zwraca null. Limit liczby okrętów
	 * ustalany jest za pośrednictwem obiektu klasy ShipSizeLimit. Wywołanie
	 * metody przed ustaleniem limitu poprzez setLimit powoduje, że metoda
	 * zawsze zwraca null. Wywołanie metody dla wielkości size, która nie
	 * posiada limitu zawsze kończy się zwróceniem null.
	 *
	 * @param size rozmiar okrętu, który chce otrzymać użytkownik
	 * @return Metoda zwraca null gdy zostanie wywołana przed ustaleniem limitu
	 *         okrętów za pomocą sertLimit lub, gdy wyprodukowanie nowego
	 *         obiektu-okrętu spowoduje przekroczenie limitu. Metoda zwraca
	 * obiekt reprezentujący okręt o podanym rozmiarze, gdy limit wytworzonych
	 *         okrętów nie jest przekroczony, oraz, gdy liczba sprawnych okrętów
	 * o danym rozmiarze jest mniejsza od limitu.
	 */
	public static Ship getShip(int size)
	{
		// nie zainicjalizowany
		// lub coś innego nie tak
		if (limit == null || otherShips == null || size < 0
			|| size >= otherShips.length || otherShips[size] == null)
			return null;

		// ponad limit
		if (otherShips[size].size() >= limit.getLimit(size))
			return null;

		// tworzymy statek
		Ship brandNewShinyShip = new Ship(size);
		otherShips[size].add(brandNewShinyShip);

		return brandNewShinyShip;
	}

	/**
	 * Po wywołaniu tej metody okręt staje się wrakiem. Nie ma możliwości jego
	 * naprawy.
	 */
	public void shipwreck()
	{
		otherShips[size].remove(this);
		shipwreck = true;
	}

	/**
	 * Test, który pozwala sprawdzić czy okręt jest już wrakiem.
	 *
	 * @return true - wrak, false - okręt nadal sprawny
	 */
	public boolean isShipwreck()
	{
		return shipwreck;
	}
}
// vim: tabstop=4 shiftwidth=0 noexpandtab
