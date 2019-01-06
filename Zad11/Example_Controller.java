public class Example_Controller implements ControllerInterface {
	@Override
	public void setGame(GameInterface gi)
	{
		// dodajemy obiekt nasłuchujący na zdarzenie pojawienia się
		// nowego spadającego obiektu
		gi.addNewObjectListener(f -> {
			System.out.println("Na planszy pojawił się nowy obiekt " + f);
			// gdy pojawia się nowy obiekt dodajemy obiekt
			// nasłuchujący na zdarzenie zmiany jego położenia
			f.addLocationListener(l -> {
				System.out.println("Obiekt " + f + " ma nową pozycję: " + l);
			});
		});
		// dodajemy jeszcze obiekt nasłuchujący na zmiany rozmiaru kosza.
		gi.addShrinkBasketListener(
			s -> { System.out.println("Nowy rozmiar kosza " + s); });
	}
}
