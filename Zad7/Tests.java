import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {
	private Point make_point(int x, int y, int z)
	{
		Point out = new Point();
		out.setPosition(0, x);
		out.setPosition(1, y);
		out.setPosition(2, z);
		return out;
	}

	@Test
	public void test_example()
	{
		List<Point> x;
		GeometricShapeInterface tested = new GeometricShape();

		assertThat("tu chyba może być null", tested.get(), is(notNullValue()));
		assertThat("wtedy to nie ma sensu...", tested.get().size(), is(0));

		assertThat(tested.undo(), is(false));
		assertThat(tested.redo(), is(false));

		tested.add(make_point(0, 0, 1));
		assertThat(tested.get(), is(notNullValue()));
		assertThat(tested.get().size(), is(1));
		assertThat(tested.get().get(0), is(equalTo(make_point(0, 0, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(1));

		assertThat(tested.redo(), is(false));

		assertThat(tested.undo(), is(true));
		assertThat(tested.redo(), is(true));

		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(1));
		assertThat(x.get(0), is(equalTo(make_point(0, 0, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(1));

		assertThat(tested.undo(), is(true));
		assertThat(tested.undo(), is(false));

		tested.add(make_point(1, 0, 0));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(1));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));

		tested.add(make_point(1, 1, 0));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(2));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 0))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		tested.add(make_point(1, 1, 1));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(3));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 0))));
		assertThat(x.get(2), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(3));

		assertThat(tested.undo(), is(true));
		assertThat(tested.undo(), is(true));

		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(1));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(1));

		tested.add(make_point(1, 1, 1));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(2));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		tested.add(make_point(1, 1, 1));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(3));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(x.get(2), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));
		assertThat("getUniq() modyfikuje orginalną listę", x.size(), is(3));

		tested.add(make_point(1, 1, 1));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(4));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(x.get(2), is(equalTo(make_point(1, 1, 1))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		tested.add(make_point(1, 0, 0));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(5));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(x.get(2), is(equalTo(make_point(1, 1, 1))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(x.get(4), is(equalTo(make_point(1, 0, 0))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(
			"getUniq() nie zwraca zbioru", tested.getUniq().size(), is(3));

		x.remove(3);
		assertThat(
			"get() powinno zwracać kopię danych", tested.get().size(), is(5));

		assertThat(tested.undo(), is(true));
		assertThat(tested.undo(), is(true));
		assertThat(tested.undo(), is(true));
		assertThat(tested.undo(), is(true));
		assertThat(tested.redo(), is(true));

		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(2));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		assertThat(tested.addBefore(make_point(0, 0, 1), make_point(1, 1, 1)),
			is(true));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(3));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(3));

		assertThat(tested.addAfter(make_point(0, 1, 0), make_point(0, 0, 1)),
			is(true));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(4));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(0, 1, 0))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(4));

		assertThat("nieudane dodanie",
			tested.addAfter(make_point(0, 1, 0), make_point(2, 2, 2)),
			is(false));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(4));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(0, 1, 0))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(4));

		assertThat("nieudane dodanie",
			tested.addBefore(make_point(0, 1, 0), make_point(2, 2, 2)),
			is(false));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(4));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(0, 1, 0))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(4));

		assertThat(tested.removeBefore(make_point(1, 1, 1)),
			is(equalTo(make_point(0, 1, 0))));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(3));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(3));

		assertThat(tested.removeAfter(make_point(1, 0, 0)),
			is(equalTo(make_point(0, 0, 1))));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(2));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		assertThat("nieudane removeAfter()",
			tested.removeAfter(make_point(2, 2, 2)),
			is(nullValue()));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(2));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		assertThat("nieudane removeBefore()",
			tested.removeBefore(make_point(2, 2, 2)),
			is(nullValue()));
		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(2));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(2));

		assertThat(tested.undo(), is(true));
		assertThat(tested.undo(), is(true));

		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(4));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(0, 1, 0))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(4));

		assertThat(tested.undo(), is(true));
		assertThat(tested.redo(), is(true));

		x = tested.get();
		assertThat(x, is(notNullValue()));
		assertThat(x.size(), is(4));
		assertThat(x.get(0), is(equalTo(make_point(1, 0, 0))));
		assertThat(x.get(1), is(equalTo(make_point(0, 0, 1))));
		assertThat(x.get(2), is(equalTo(make_point(0, 1, 0))));
		assertThat(x.get(3), is(equalTo(make_point(1, 1, 1))));
		assertThat(tested.getUniq(), is(notNullValue()));
		assertThat(tested.getUniq().size(), is(4));

		Map<Point, Integer> map = tested.getPointsAsMap();
		assertThat(map, is(notNullValue()));
		assertThat(map.get(make_point(2, 0, 0)), is(nullValue()));
		assertThat(map.get(make_point(1, 0, 0)), is(1));
		assertThat(map.get(make_point(0, 0, 1)), is(1));
		assertThat(map.get(make_point(0, 1, 0)), is(1));
		assertThat(map.get(make_point(1, 1, 1)), is(1));
	}
}
