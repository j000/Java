import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

class PathFinder implements PathFinderInterface {
	static final boolean debug = false;

	class Connection {
		public final BusStopInterface stop;
		public final BusInterface bus;

		Connection(BusStopInterface newStop, BusInterface newBus)
		{
			stop = newStop;
			bus = newBus;
		}

		@Override
		public String toString()
		{
			return (bus == null ? "null" : bus.getBusNumber()) + " "
				+ (stop == null ? "null" : stop.getName());
		}
	}

	private class DFSearcher {
		private Stack<BusInterface> usedBuses = new Stack<>();
		private Set<BusStopInterface> visitedStops = new HashSet<>();
		private Stack<Connection> currentSolution = new Stack<>();

		DFSearcher(BusStopInterface from, BusStopInterface to, int transfers)
		{
			currentSolution.add(0, new Connection(from, null));
			DFSHelper(from, to, transfers);
		}

		public void DFSHelper(
			BusStopInterface from, BusStopInterface to, int transfers)
		{
			if (transfers < 0)
				return;

			boolean transfered = false;

			visitedStops.add(from);

			if (debug) {
				System.out.println(" ### At " + from.getName());
				// System.out.println(currentSolution);
			}

			for (Connection destination : graph.get(from)) {
				// if (!usedBuses.empty() && usedBuses.peek() != destination.bus
				// 	&& usedBuses.contains(destination.bus)) {
				// 	if (debug)
				// 		System.out.println("Not getting into the same bus");
				// 	continue;
				// }

				if (visitedStops.contains(destination.stop))
					continue;

				if (debug)
					System.out.println(from.getName() + ": Checking to "
						+ destination.stop.getName());

				if (!usedBuses.empty() && usedBuses.peek() != destination.bus) {
					transfered = true;
					--transfers;
					if (debug)
						System.out.println(" *** I just transfered *** ");
				}
				usedBuses.push(destination.bus);

				currentSolution.push(destination);

				if (destination.stop == to) {
					if (debug) {
						System.out.println(
							" *** Found *** transfers: " + transfers + " *** ");
						System.out.println(currentSolution);
					}
					if (transfers == 0) {
						solutions.add(new ArrayList<>(currentSolution));
					}
				} else {
					DFSHelper(destination.stop, to, transfers);
				}

				currentSolution.pop();

				visitedStops.remove(destination.stop);

				usedBuses.pop();
				if (transfered) {
					++transfers;
				}
			}
		}
	}

	private List<List<Connection>> solutions;
	private Map<BusStopInterface, List<Connection>> graph
		= new HashMap<BusStopInterface, List<Connection>>();

	/**
	 * Metoda dodaje linię autobusową do serwisu. Ten sam autobus
	 * obsługuje linię w obu kierunkach.
	 * @param line linia autobusowa
	 * @param bus autobus, który ją obsługuje
	 */
	@Override
	public void addLine(BusLineInterface line, BusInterface bus)
	{
		if (line == null)
			throw new IllegalArgumentException("Please provide a line");

		if (bus == null)
			throw new IllegalArgumentException("Please provide a bus");

		if (line.getNumberOfBusStops() < 2)
			throw new IllegalArgumentException("Line has at least 2 stops");

		BusStopInterface from = line.getBusStop(0);
		graph.putIfAbsent(from, new ArrayList<Connection>());

		for (int loop = 1; loop < line.getNumberOfBusStops(); ++loop) {
			BusStopInterface to = line.getBusStop(loop);
			graph.putIfAbsent(to, new ArrayList<Connection>());

			graph.get(from).add(new Connection(to, bus));
			graph.get(to).add(new Connection(from, bus));

			from = to;
		}
	}

	/**
	 * Metoda zleca znalezienie połączenia autobusowego
	 * prowadzącego od przystanku from do przystanku to
	 * z uwzględnieniem podanej liczby przesiadek.
	 * Liczba przesiadek równa zero oznacza, że poszukiwane jest
	 * połączenie bezpośrednie.
	 * @param from przystanek początkowy
	 * @param to przystanek końcowy
	 * @param transfers liczba przesiadek
	 */
	@Override
	public void find(BusStopInterface from, BusStopInterface to, int transfers)
	{
		solutions = new ArrayList<>();

		DFSearcher tmp = new DFSearcher(from, to, transfers);

		// System.out.println(solutions);
	}

	/**
	 * Liczba odnalezionych rozwiązań.
	 * @return liczba rozwiązań. Przed wykonaniem metody find
	 * metoda zwraca zawsze 0.
	 */
	@Override
	public int getNumerOfSolutions()
	{
		if (solutions == null)
			return 0;

		return solutions.size();
	}

	/**
	 * Liczba przystanków autobusowych należących do rozwiązania
	 * o podanym numerze. Przystanek o numerze 0 to przystanek, od
	 * którego rozpoczynana jest podróż (from). Przystanek o numerze
	 * getNumerOfSolutions()-1 to przystanek końcowy (to).
	 * @param solution numer rozwiązania
	 * @return liczba przystanków.
	 */
	@Override
	public int getBusStops(int solution)
	{
		return solutions.get(solution).size();
	}

	/**
	 * Metoda zwraca przystanek o numerze busStop w rozwiązaniu
	 * o numerze solution.
	 * @param solution numer rozwiązania
	 * @param busStop numer przystanku w obrębie danego rozwiązania
	 * @return przystanek o podanych numerach identyfikacyjnych
	 */
	@Override
	public BusStopInterface getBusStop(int solution, int busStop)
	{
		return solutions.get(solution).get(busStop).stop;
	}

	/**
	 * Dla wszystkich przystanków poza ostatnim, metoda zwraca autobus, który
	 * obsługuje połączenie z przystanku o numerze busStop do następnego.
	 * Dla przystanku ostatniego, autobus, który obsługiwał przejazd
	 * z przystanku busStop-2 do busStop-1 (czyli ostatniego).
	 * @param solution numer rozwiązania
	 * @param busStop numer przystanku w obrębie danego rozwiązania
	 * @return autobus, którym pasażer odjeżdża z danego przystanku lub
	 * w przypadku przystanku docelowego, autobus, z którego pasażer
	 * na tym przystanku wysiadł.
	 */
	@Override
	public BusInterface getBus(int solution, int busStop)
	{
		return solutions.get(solution).get(busStop + 1).bus;
	}
}
