
public interface ControllerInterface {
	/**
	 * Przekazuje referencje do obiektu gry. Od tej chwili
	 * gracz może sterować koszykiem do przechwytywania obiektów.
	 *
	 * @param gi referencja do obiektu gru
	 */
	public void setGame(GameInterface gi);
}
