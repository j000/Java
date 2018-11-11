public class BusStop implements BusStopInterface {
	private String name;

	BusStop(String newName)
	{
		name = newName;
	}

	/**
	 * Metoda zwraca nazwę przystanku.
	 * @return nazwa przystanku
	 */
	public String getName()
	{
		return name;
	}
}
