import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class BusLine implements BusLineInterface {
	private BusStopInterface[] busStops;

	BusLine(BusStopInterface[] newBusStops)
	{
		busStops = newBusStops;
	}

	/**
	 * Metoda zwraca liczbę przystanków, które wchodzą w jej skład.
	 *
	 * @return liczba przystanków danej lini
	 */
	public int getNumberOfBusStops()
	{
		return busStops.length;
	}

	/**
	 * Metoda zwraca obiekt reprezentujący przystanek o podanym numerze. Prawidłowe
	 * numery przystanów mieszczą się w przedziale od 0 do getNumberOfBusStops()-1.
	 * Tylko podanie błednego numeru przystanku spowoduje zwrócenie null.
	 *
	 * @param number numer przystanku
	 * @return obiekt reprezentujący przystanek o numerze number
	 */
	public BusStopInterface getBusStop(int number)
	{
		if (number < 0 || number >= getNumberOfBusStops())
			throw new IllegalArgumentException("Wrong number");

		return busStops[number];
	}
}
