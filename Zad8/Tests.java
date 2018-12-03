import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class Tests {
	private Point make_point(int... x)
	{
		Point out = new Point(x.length);
		for (int i = 0; i < x.length; ++i) {
			out.setPosition(i, x[i]);
		}
		return out;
	}

	@Test
	public void test1()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			tested.addAfter(make_point(2), make_point(3));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw something",
				e,
				anyOf(is(instanceOf(WrongNumberOfDimensionsException.class)),
					is(instanceOf(WrongArgumentException.class))));
		}

		assertThat(tested.get(), anyOf(is(nullValue()), is(empty())));
		try {
			tested.add(make_point(2));
		} catch (Exception e) {
			fail("Should not throw on first point");
		}
		try {
			tested.add(make_point(3));
		} catch (Exception e) {
			fail("The same number of dimensions");
		}
		try {
			tested.add(make_point(3, 3));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 1 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(1)));
			assertThat("We gave 2 dimensions",
				e2.getActualDimensions(),
				is(equalTo(2)));
		}
		try {
			tested.addBefore(make_point(4), make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		// addAfter
		try {
			tested.addAfter(make_point(3, 3), make_point(3));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 1 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(1)));
			assertThat("We gave 2 dimensions",
				e2.getActualDimensions(),
				is(equalTo(2)));
		}
		// addAfter
		try {
			tested.addAfter(make_point(3), make_point(3, 3));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw \"proper\" exception",
				e,
				anyOf(is(instanceOf(WrongNumberOfDimensionsException.class)),
					is(instanceOf(WrongArgumentException.class))));
			if (e instanceof WrongNumberOfDimensionsException) {
				WrongNumberOfDimensionsException e2
					= (WrongNumberOfDimensionsException)e;
				assertThat("We should expect 1 dimension",
					e2.getExpectedDimensions(),
					is(equalTo(1)));
				assertThat("We gave 2 dimensions",
					e2.getActualDimensions(),
					is(equalTo(2)));
			} else {
				WrongArgumentException e2 = (WrongArgumentException)e;
				assertThat(e2.getProblemPoint(), is(equalTo(make_point(3, 3))));
			}
		}
		// addAfter
		try {
			tested.addAfter(make_point(3), make_point(5));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(5))));
		}
		// addBefore
		try {
			tested.addBefore(make_point(3, 3), make_point(3));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 1 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(1)));
			assertThat("We gave 2 dimensions",
				e2.getActualDimensions(),
				is(equalTo(2)));
		}
		// addBefore
		try {
			tested.addBefore(make_point(3), make_point(3, 3));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw \"proper\" exception",
				e,
				anyOf(is(instanceOf(WrongNumberOfDimensionsException.class)),
					is(instanceOf(WrongArgumentException.class))));
			if (e instanceof WrongNumberOfDimensionsException) {
				WrongNumberOfDimensionsException e2
					= (WrongNumberOfDimensionsException)e;
				assertThat("We should expect 1 dimension",
					e2.getExpectedDimensions(),
					is(equalTo(1)));
				assertThat("We gave 2 dimensions",
					e2.getActualDimensions(),
					is(equalTo(2)));
			} else {
				WrongArgumentException e2 = (WrongArgumentException)e;
				assertThat(e2.getProblemPoint(), is(equalTo(make_point(3, 3))));
			}
		}
		// addBefore
		try {
			tested.addBefore(make_point(3), make_point(5));
			fail("This should throw");
		} catch (Exception e) {
			assertThat("This should throw exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(5))));
		}

		List<Point> list = tested.get();
		assertThat(list, is(notNullValue()));
		assertThat(list.get(0), is(equalTo(make_point(4))));
		assertThat(list.get(1), is(equalTo(make_point(2))));
		assertThat(list.get(2), is(equalTo(make_point(3))));

		try {
			List<Integer> bleh = new ArrayList<>();
			bleh.add(3);
			assertThat(tested.getByPosition(bleh),
				is(equalTo(Optional.of(make_point(3)))));
		} catch (Exception e) {
			fail("Should not throw");
		}

		try {
			List<Integer> bleh = new ArrayList<>();
			bleh.add(3);
			bleh.add(3);
			tested.getByPosition(bleh);
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 1 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(1)));
			assertThat("We gave 2 dimensions",
				e2.getActualDimensions(),
				is(equalTo(2)));
		}
	}
}
