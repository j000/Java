/**
 * Klasa reprezentuje limit ilości okrętów o danym rozmiarze. Po ustaleniu
 * limitu nie ma możliwoście jego zmiany - limit ustalany jest na etapie
 * tworzenia obiektu za pomocą konstruktora.
 */
public final class ShipSizeLimit {
    private final int[] shipSizes;

    /**
     * Konstruktor, do którego przekazywana jest tablica zawierająca informacje o
     * limicie ilości jednostek danego rozmiaru.
     * 
     * @param shipSizes tablica z limitami ilości jednostek. Z uwagia na brak
     *                  okrętów o rozmiarze 0, na pozycji shipSizes[x] znajduje się
     *                  limit dla okrętów rozmiaru x+1.
     */
    public ShipSizeLimit(int[] shipSizes) {
        this.shipSizes = new int[shipSizes.length];
        // generujemy kopię przekazanej tablicy,
        // dzięki temu wartości nie będzie można zmienić.
        System.arraycopy(shipSizes, 0, this.shipSizes, 0, shipSizes.length);
    }

    /**
     * Liczba różnych rozmiarów okrętów. Wynik zwrócony za pomocą getNumberOfSizes
     * to jednocześnie rozmiar największej jednostki.
     *
     * @return liczba rozmiarów okrętów.
     */
    final public int getNumberOfSizes() {
        return shipSizes.length;
    }

    /**
     * Maksymalna liczba okrętów o size rozmiarze. Dla size=0 metoda zawsze zwraca
     * 0. Prawidłowe rozmiary okrętów mieszczą się w przedziale od 1 do
     * getNumberOfSizes.
     * 
     * @param size rozmiar okrętu
     * @return limit ilości jednostek o danym rozmiarze.
     */
    final public int getLimit(int size) {
        if (size == 0)
            return 0;
        return shipSizes[size - 1];
    }
}
