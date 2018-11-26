import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// JUnit4
import static org.junit.Assert.*;
import org.junit.Test;

// Junit5 (hopefully)
// import org.junit.jupiter.api.*;

class Position implements PositionInterface {
	final int col;
	final int row;
	public Position(int _col, int _row)
	{
		col = _col;
		row = _row;
	}
	@Override
	public int getCol()
	{
		return col;
	}
	@Override
	public int getRow()
	{
		return row;
	}
}

public class Tests {
	@Test
	public void test_example()
	{
		PathFinderInterface tested = PathFinderEnum.LEFT_HAND_TRAFFIC;
		int map[][] = {{0, 0, 0, 0},
			{0, 1, 2, 0},
			{0, 0, 2, 0},
			{0, 0, 5, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0}};
		// mapa musi być wczytana w tą stronę
		assertThat("Mapa nie miesza wierszy i kolumn", map[6][3], is(0));
		tested.setMap(map);

		PositionInterface[] out
			= tested.getShortestRoute(new Position(1, 1), new Position(4, 2));
		assertThat(out.length, is(5));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[1].getCol(), is(1));
		assertThat(out[1].getRow(), is(2));
		assertThat(out[2].getCol(), is(2));
		assertThat(out[2].getRow(), is(2));
		assertThat(out[3].getCol(), is(3));
		assertThat(out[3].getRow(), is(2));
		assertThat(out[4].getCol(), is(4));
		assertThat(out[4].getRow(), is(2));

		out = tested.getEasiestRoute(new Position(1, 1), new Position(4, 2));
		assertThat(out.length, is(5));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[1].getCol(), is(1));
		assertThat(out[1].getRow(), is(2));
		assertThat(out[2].getCol(), is(2));
		assertThat(out[2].getRow(), is(2));
		assertThat(out[3].getCol(), is(3));
		assertThat(out[3].getRow(), is(2));
		assertThat(out[4].getCol(), is(4));
		assertThat(out[4].getRow(), is(2));

		out = tested.getFastestRoute(new Position(1, 1), new Position(4, 2));
		assertThat(out.length, is(5));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[1].getCol(), is(1));
		assertThat(out[1].getRow(), is(2));
		assertThat(out[2].getCol(), is(2));
		assertThat(out[2].getRow(), is(2));
		assertThat(out[3].getCol(), is(3));
		assertThat(out[3].getRow(), is(2));
		assertThat(out[4].getCol(), is(4));
		assertThat(out[4].getRow(), is(2));

		out = tested.getShortestRoute(new Position(4, 2), new Position(1, 1));
		assertThat(out.length, is(5));
		assertThat(out[0].getCol(), is(4));
		assertThat(out[0].getRow(), is(2));
		assertThat(out[1].getCol(), is(3));
		assertThat(out[1].getRow(), is(2));
		assertThat(out[2].getCol(), is(2));
		assertThat(out[2].getRow(), is(2));
		assertThat(out[3].getCol(), is(1));
		assertThat(out[3].getRow(), is(2));
		assertThat(out[4].getCol(), is(1));
		assertThat(out[4].getRow(), is(1));
	}
	@Test
	public void test_intersections_left()
	{
		PositionInterface[] out;
		int map[][] = {{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 0, 0},
			{0, 0, 1, 0, 1, 0, 0},
			{0, 0, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}};
		PathFinderInterface tested = PathFinderEnum.LEFT_HAND_TRAFFIC;
		tested.setMap(map);

		out = tested.getEasiestRoute(new Position(1, 1), new Position(3, 5));
		assertThat("Answer exists", out, is(notNullValue()));
		assertThat(out.length, is(7));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[6].getCol(), is(3));
		assertThat(out[6].getRow(), is(5));
		assertThat(out[3].getCol(), is(1));
		assertThat(out[3].getRow(), is(4));
	}
	@Test
	public void test_intersections_right()
	{
		PositionInterface[] out;
		int map[][] = {{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 0, 0},
			{0, 0, 1, 0, 1, 0, 0},
			{0, 0, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}};
		PathFinderInterface tested = PathFinderEnum.RIGHT_HAND_TRAFFIC;
		tested.setMap(map);

		out = tested.getEasiestRoute(new Position(1, 1), new Position(3, 5));
		assertThat("Answer exists", out, is(notNullValue()));
		assertThat(out.length, is(7));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[6].getCol(), is(3));
		assertThat(out[6].getRow(), is(5));
		assertThat(out[3].getCol(), is(3));
		assertThat(out[3].getRow(), is(2));
	}
	@Test
	public void test1()
	{
		PathFinderInterface tested = PathFinderEnum.LEFT_HAND_TRAFFIC;
		int map[][] = {{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 0, 0, 0},
			{0, 9, 0, 1, 0, 0, 0},
			{0, 9, 9, 1, 9, 9, 0},
			{0, 0, 0, 1, 0, 9, 0},
			{0, 0, 0, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}};
		tested.setMap(map);
		PositionInterface[] out;

		out = tested.getShortestRoute(new Position(1, 1), new Position(6, 5));
		assertThat(out.length, is(10));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[2].getCol(), is(3));
		assertThat(out[2].getRow(), is(1));
		assertThat(out[6].getCol(), is(4));
		assertThat(out[6].getRow(), is(4));
		assertThat(out[9].getCol(), is(6));
		assertThat(out[9].getRow(), is(5));

		out = tested.getFastestRoute(new Position(1, 1), new Position(6, 5));
		assertThat(out.length, is(10));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[2].getCol(), is(2));
		assertThat(out[2].getRow(), is(2));
		assertThat(out[6].getCol(), is(5));
		assertThat(out[6].getRow(), is(3));
		assertThat(out[9].getCol(), is(6));
		assertThat(out[9].getRow(), is(5));

		out = tested.getShortestRoute(new Position(5, 3), new Position(1, 1));
		assertThat(out.length, is(7));
		assertThat(out[0].getCol(), is(5));
		assertThat(out[0].getRow(), is(3));
		assertThat("Lewo i wprost jest lepsze niż wprost i prawo",
			out[3].getCol(),
			is(4));
		assertThat("Lewo i wprost jest lepsze niż wprost i prawo",
			out[3].getRow(),
			is(1));
		assertThat(out[6].getCol(), is(1));
		assertThat(out[6].getRow(), is(1));
	}
	@Test
	public void test2()
	{
		PathFinderInterface tested = PathFinderEnum.RIGHT_HAND_TRAFFIC;
		int map[][] = {{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 0, 0, 0},
			{0, 9, 0, 1, 0, 0, 0},
			{0, 9, 9, 1, 9, 9, 0},
			{0, 0, 0, 1, 0, 9, 0},
			{0, 0, 0, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}};
		tested.setMap(map);
		PositionInterface[] out;

		out = tested.getShortestRoute(new Position(1, 1), new Position(6, 5));
		assertThat(out.length, is(10));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[2].getCol(), is(3));
		assertThat(out[2].getRow(), is(1));
		assertThat(out[6].getCol(), is(4));
		assertThat(out[6].getRow(), is(4));
		assertThat(out[9].getCol(), is(6));
		assertThat(out[9].getRow(), is(5));

		out = tested.getFastestRoute(new Position(1, 1), new Position(6, 5));
		assertThat(out.length, is(10));
		assertThat(out[0].getCol(), is(1));
		assertThat(out[0].getRow(), is(1));
		assertThat(out[2].getCol(), is(2));
		assertThat(out[2].getRow(), is(2));
		assertThat(out[6].getCol(), is(5));
		assertThat(out[6].getRow(), is(3));
		assertThat(out[9].getCol(), is(6));
		assertThat(out[9].getRow(), is(5));

		out = tested.getShortestRoute(new Position(5, 3), new Position(1, 1));
		assertThat(out.length, is(7));
		assertThat(out[0].getCol(), is(5));
		assertThat(out[0].getRow(), is(3));
		assertThat("Wprost i prawo jest lepsze niż lewo i wprost",
			out[3].getCol(),
			is(2));
		assertThat("Wprost i prawo jest lepsze niż lewo i wprost",
			out[3].getRow(),
			is(3));
		assertThat(out[6].getCol(), is(1));
		assertThat(out[6].getRow(), is(1));
	}
	@Test
	public void test_easy()
	{
		PathFinderInterface tested = PathFinderEnum.RIGHT_HAND_TRAFFIC;
		int map[][] = {{0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0},
			{0, 1, 1, 1, 1, 0},
			{0, 0, 1, 0, 1, 0},
			{0, 0, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0}};
		tested.setMap(map);
		PositionInterface[] out;

		out = tested.getEasiestRoute(new Position(2, 1), new Position(1, 2));
		assertThat(out, is(notNullValue()));
		assertThat("go straight twice", out.length, is(11));

		out = tested.getEasiestRoute(new Position(1, 2), new Position(2, 1));
		assertThat(out, is(notNullValue()));
		assertThat("go straight twice", out.length, is(11));

		out = tested.getFastestRoute(new Position(1, 2), new Position(2, 1));
		assertThat(out, is(notNullValue()));
		assertThat(out.length, is(3));

		out = tested.getFastestRoute(new Position(2, 1), new Position(1, 2));
		assertThat(out, is(notNullValue()));
		assertThat(out.length, is(3));

		out = tested.getShortestRoute(new Position(1, 2), new Position(2, 1));
		assertThat(out, is(notNullValue()));
		assertThat(out.length, is(3));

		out = tested.getShortestRoute(new Position(2, 1), new Position(1, 2));
		assertThat(out, is(notNullValue()));
		assertThat(out.length, is(3));
	}
}

