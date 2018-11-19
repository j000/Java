import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// JUnit4
import static org.junit.Assert.*;
import org.junit.Test;

// Junit5 (hopefully)
// import org.junit.jupiter.api.*;

// other stuff...
import java.util.HashMap;

public class Tests {
	@Test
	public void test_example()
	{
		Bus bus100 = new Bus(100);
		Bus bus200 = new Bus(200);
		Bus bus300 = new Bus(300);

		assertThat(bus100.getBusNumber(), is(100));
		assertThat(bus200.getBusNumber(), is(200));
		assertThat(bus300.getBusNumber(), is(300));

		HashMap<Character, BusStop> busStops = new HashMap<>();
		for (char loop = 'A'; loop <= 'O'; ++loop) {
			busStops.put(loop, new BusStop(String.valueOf(loop)));
		}

		assertThat(busStops.get('A').getName(), is("A"));
		assertThat(busStops.get('O').getName(), is("O"));
		assertThat(busStops.get('P'), is(nullValue()));

		BusLineInterface liniaCzerwona
			= new BusLine(new BusStopInterface[] {busStops.get('A'),
				busStops.get('B'),
				// busStops.get('C'),
				new BusStop("C"),
				busStops.get('D'),
				busStops.get('E'),
				busStops.get('F'),
				busStops.get('G')});

		BusLineInterface liniaZielona
			= new BusLine(new BusStopInterface[] {busStops.get('H'),
				busStops.get('I'),
				busStops.get('C'),
				busStops.get('J'),
				busStops.get('K')});

		BusLineInterface liniaNiebieska
			= new BusLine(new BusStopInterface[] {busStops.get('L'),
				busStops.get('E'),
				busStops.get('M'),
				busStops.get('N'),
				busStops.get('O')});

		PathFinderInterface tested = new PathFinder();
		assertThat(
			"0 solutions before find()", tested.getNumerOfSolutions(), is(0));

		tested.addLine(liniaCzerwona, bus100);
		tested.addLine(liniaZielona, bus200);
		tested.addLine(liniaNiebieska, bus300);

		assertThat(
			"0 solutions before find()", tested.getNumerOfSolutions(), is(0));

		tested.find(busStops.get('I'), busStops.get('N'), 2);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(6));
		assertThat(
			tested.getBusStop(0, 0).getName(), is(busStops.get('I').getName()));
		assertThat(
			tested.getBusStop(0, 1).getName(), is(busStops.get('C').getName()));
		assertThat(
			tested.getBusStop(0, 2).getName(), is(busStops.get('D').getName()));
		assertThat(
			tested.getBusStop(0, 3).getName(), is(busStops.get('E').getName()));
		assertThat(
			tested.getBusStop(0, 4).getName(), is(busStops.get('M').getName()));
		assertThat(
			tested.getBusStop(0, 5).getName(), is(busStops.get('N').getName()));
		assertThat(
			tested.getBus(0, 0).getBusNumber(), is(bus200.getBusNumber()));
		assertThat(
			tested.getBus(0, 1).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 2).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 3).getBusNumber(), is(bus300.getBusNumber()));
		assertThat(
			tested.getBus(0, 4).getBusNumber(), is(bus300.getBusNumber()));
		assertThat(
			tested.getBus(0, 4).getBusNumber(), is(new Bus(300).getBusNumber()));

		////////////////////

		tested.find(busStops.get('I'), busStops.get('N'), 1);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('I'), busStops.get('N'), 3);
		assertThat(tested.getNumerOfSolutions(), is(0));

		////////////////////

		tested.find(busStops.get('C'), busStops.get('E'), 1);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('C'), busStops.get('E'), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(3));
		assertThat(
			tested.getBus(0, 0).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 1).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBusStop(0, 0).getName(), is(busStops.get('C').getName()));
		assertThat(
			tested.getBusStop(0, 1).getName(), is(busStops.get('D').getName()));
		assertThat(
			tested.getBusStop(0, 2).getName(), is(busStops.get('E').getName()));

		tested.find(busStops.get('E'), busStops.get('C'), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(3));
		assertThat(
			tested.getBus(0, 0).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 1).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBusStop(0, 0).getName(), is(busStops.get('E').getName()));
		assertThat(
			tested.getBusStop(0, 1).getName(), is(busStops.get('D').getName()));
		assertThat(
			tested.getBusStop(0, 2).getName(), is(busStops.get('C').getName()));

		tested.find(new BusStop("E"), busStops.get('C'), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));
		tested.find(busStops.get('E'), new BusStop("C"), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));
		tested.find(new BusStop("E"), new BusStop("C"), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));

		////////////////////

		tested.find(busStops.get('I'), busStops.get('J'), 1);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('I'), busStops.get('J'), 2);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('I'), busStops.get('J'), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(3));
	}

	@Test
	public void test_example2()
	{
		Bus bus100 = new Bus(100);
		Bus bus200 = new Bus(200);
		Bus bus300 = new Bus(300);
		Bus bus400 = new Bus(400);

		HashMap<Character, BusStop> busStops = new HashMap<>();
		for (char loop = 'A'; loop <= 'T'; ++loop) {
			busStops.put(loop, new BusStop(String.valueOf(loop)));
		}

		BusLineInterface liniaCzerwona
			= new BusLine(new BusStopInterface[] {busStops.get('A'),
				busStops.get('B'),
				busStops.get('C'),
				busStops.get('D'),
				busStops.get('E'),
				busStops.get('F'),
				busStops.get('G')});

		BusLineInterface liniaZielona
			= new BusLine(new BusStopInterface[] {busStops.get('H'),
				busStops.get('I'),
				busStops.get('C'),
				busStops.get('J'),
				busStops.get('K')});

		BusLineInterface liniaNiebieska
			= new BusLine(new BusStopInterface[] {busStops.get('L'),
				busStops.get('E'),
				busStops.get('M'),
				busStops.get('N'),
				busStops.get('O')});

		BusLineInterface liniaPomaranczowa
			= new BusLine(new BusStopInterface[] {busStops.get('P'),
				busStops.get('R'),
				busStops.get('J'),
				busStops.get('S'),
				busStops.get('M'),
				busStops.get('N'),
				busStops.get('T')});

		PathFinderInterface tested = new PathFinder();
		assertThat(
			"0 solutions before find()", tested.getNumerOfSolutions(), is(0));

		tested.addLine(liniaCzerwona, bus100);
		tested.addLine(liniaZielona, bus200);
		tested.addLine(liniaNiebieska, bus300);
		tested.addLine(liniaPomaranczowa, bus400);

		assertThat(
			"0 solutions before find()", tested.getNumerOfSolutions(), is(0));

		tested.find(busStops.get('I'), busStops.get('O'), 0);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('I'), busStops.get('O'), 1);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('I'), busStops.get('O'), 2);
		assertThat(tested.getNumerOfSolutions(), is(greaterThan(0)));

		tested.find(busStops.get('M'), busStops.get('N'), 0);
		assertThat(tested.getNumerOfSolutions(), is(greaterThan(0)));
	}

	@Test
	public void test_loops()
	{
		Bus bus100 = new Bus(100);
		Bus bus200 = new Bus(200);
		Bus bus300 = new Bus(300);

		assertThat(bus100.getBusNumber(), is(100));
		assertThat(bus200.getBusNumber(), is(200));
		assertThat(bus300.getBusNumber(), is(300));

		HashMap<Character, BusStop> busStops = new HashMap<>();
		for (char loop = 'A'; loop <= 'I'; ++loop) {
			busStops.put(loop, new BusStop(String.valueOf(loop)));
		}

		assertThat(busStops.get('A').getName(), is("A"));
		assertThat(busStops.get('I').getName(), is("I"));
		assertThat(busStops.get('J'), is(nullValue()));

		BusLineInterface liniaCzerwona
			= new BusLine(new BusStopInterface[] {busStops.get('A'),
				busStops.get('B'),
				busStops.get('C'),
				busStops.get('D')});

		BusLineInterface liniaZielona
			= new BusLine(new BusStopInterface[] {busStops.get('E'),
				busStops.get('B'),
				busStops.get('F'),
				busStops.get('G')});

		BusLineInterface liniaNiebieska
			= new BusLine(new BusStopInterface[] {busStops.get('H'),
				busStops.get('C'),
				busStops.get('F'),
				busStops.get('I')});

		PathFinderInterface tested = new PathFinder();
		assertThat(
			"0 solutions before find()", tested.getNumerOfSolutions(), is(0));

		tested.addLine(liniaCzerwona, bus100);
		tested.addLine(liniaZielona, bus200);
		tested.addLine(liniaNiebieska, bus300);

		assertThat(
			"0 solutions before find()", tested.getNumerOfSolutions(), is(0));

		tested.find(busStops.get('A'), busStops.get('H'), 0);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('A'), busStops.get('H'), 1);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(4));
		assertThat(
			tested.getBus(0, 0).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 1).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 2).getBusNumber(), is(bus300.getBusNumber()));
		assertThat(
			tested.getBusStop(0, 0).getName(), is(busStops.get('A').getName()));
		assertThat(
			tested.getBusStop(0, 1).getName(), is(busStops.get('B').getName()));
		assertThat(
			tested.getBusStop(0, 2).getName(), is(busStops.get('C').getName()));
		assertThat(
			tested.getBusStop(0, 3).getName(), is(busStops.get('H').getName()));

		tested.find(busStops.get('A'), busStops.get('H'), 2);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(5));
		assertThat(
			tested.getBus(0, 0).getBusNumber(), is(bus100.getBusNumber()));
		assertThat(
			tested.getBus(0, 1).getBusNumber(), is(bus200.getBusNumber()));
		assertThat(
			tested.getBus(0, 2).getBusNumber(), is(bus300.getBusNumber()));
		assertThat(
			tested.getBus(0, 3).getBusNumber(), is(bus300.getBusNumber()));
		assertThat(
			tested.getBusStop(0, 0).getName(), is(busStops.get('A').getName()));
		assertThat(
			tested.getBusStop(0, 1).getName(), is(busStops.get('B').getName()));
		assertThat(
			tested.getBusStop(0, 2).getName(), is(busStops.get('F').getName()));
		assertThat(
			tested.getBusStop(0, 3).getName(), is(busStops.get('C').getName()));
		assertThat(
			tested.getBusStop(0, 4).getName(), is(busStops.get('H').getName()));

		tested.find(busStops.get('A'), busStops.get('H'), 3);
		assertThat(tested.getNumerOfSolutions(), is(0));

		////////////////////

		tested.find(busStops.get('A'), busStops.get('B'), 0);
		assertThat(tested.getNumerOfSolutions(), is(1));
		assertThat(tested.getBusStops(0), is(2));
		tested.find(busStops.get('A'), busStops.get('B'), 1);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('A'), busStops.get('B'), 2);
		assertThat(tested.getNumerOfSolutions(), is(0));
		tested.find(busStops.get('A'), busStops.get('B'), 3);
		assertThat("Don't reuse buses!", tested.getNumerOfSolutions(), is(0));
	}
}
