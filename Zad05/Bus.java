public class Bus implements BusInterface {
	private int busNumber;

	Bus(int number)
	{
		busNumber = number;
	}

	/**
	 * Metoda zwraca numer autobusu
	 * @return numer autobusu
	 */
	public int getBusNumber()
	{
		return busNumber;
	}
}
