import java.util.function.Consumer;

/**
 * Główny interfejs gry
 */
public interface GameInterface {
	/**
	 * Zwraca aktualny wynik punktowy gry
	 * 
	 * @return - wynik punktowy gry
	 */
	public int getScore();

	/**
	 * Metoda zwraca okres czasu pomiędzy dwoma zmianami położenia
	 * spadających obiektów. Okres pomiędzy dwoma zmianami położenia kosza
	 * jest dwa razy krótszy.
	 *
	 * @return czas pomiędzy dwoma zmianami położenia spadających obiektów.
	 */
	public long getTickTime();

	/**
	 * Metoda zwraca szerokość planszy do gry.
	 * @return szerokość planszy
	 */
	public int getWidth();

	/**
	 * Metoda zwraca wysokość planszy do gry.
	 * @return wysokość planszy
	 */
	public int getHeight();

	/**
	 * Początkowy rozmiar kosza. Rozmiar kosza podawany jest jako liczba
	 * punktów, które zajmuje kosz. Rozmiar kosza jest zawsze nieparzysty.
	 *
	 * @return rozmiar kosza
	 */
	public int getBasketInitialSize();

	/**
	 * Metoda zwraca obiekt pozwalający na kontrolę kosza na przedmioty.
	 *
	 * @return obiekt kontrolera kosza
	 */
	public BasketControlInterface getController();

    /**
     * Metoda pozwala na zarejestrowanie obiektu, który będzie informowany
     * o każdym nowym obiekcie, który pojawi się na planszy.
     *
     * @param listener obiekt reprezentujący obiekt oczekujący
     *                 na pojawianie się na planszy nowych obiektów.
     */
	public void addNewObjectListener( Consumer<FallingObjectBasicInterface> listener );

	/**
	 * Metoda umożliwia rejestrację obiektu, który będzie informowany o
	 * zmianie rozmiaru koszyka.
	 *
	 * @param listener obiekt oczekujący na zmianę rozmiaru koszyka.
	 */
	public void addShrinkBasketListener(Consumer<Integer> listener );
}
