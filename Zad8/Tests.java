import java.util.Arrays;
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

	@Test
	public void test_add_remove()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			tested.add(make_point(1, 1));
			tested.add(make_point(2, 2));
			tested.add(make_point(3, 3));
		} catch (Exception e) {
			fail("Should not throw");
		}
		try {
			tested.add(make_point(1));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 2 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(2)));
			assertThat("We gave 1 dimensions",
				e2.getActualDimensions(),
				is(equalTo(1)));
		}
		try {
			tested.add(make_point(1, 1, 1));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 2 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(2)));
			assertThat("We gave 3 dimensions",
				e2.getActualDimensions(),
				is(equalTo(3)));
		}
		// try {
		// 	tested.remove(make_point(1));
		// 	fail("Should throw");
		// } catch (Exception e) {
		// 	assertThat("This should throw proper exception",
		// 		e,
		// 		is(instanceOf(WrongNumberOfDimensionsException.class)));
		// 	WrongNumberOfDimensionsException e2
		// 		= (WrongNumberOfDimensionsException)e;
		// 	assertThat("We should expect 2 dimension",
		// 		e2.getExpectedDimensions(),
		// 		is(equalTo(2)));
		// 	assertThat("We gave 1 dimensions",
		// 		e2.getActualDimensions(),
		// 		is(equalTo(1)));
		// }
		// try {
		// 	tested.remove(make_point(1, 1, 1));
		// 	fail("Should throw");
		// } catch (Exception e) {
		// 	assertThat("This should throw proper exception",
		// 		e,
		// 		is(instanceOf(WrongNumberOfDimensionsException.class)));
		// 	WrongNumberOfDimensionsException e2
		// 		= (WrongNumberOfDimensionsException)e;
		// 	assertThat("We should expect 2 dimension",
		// 		e2.getExpectedDimensions(),
		// 		is(equalTo(2)));
		// 	assertThat("We gave 3 dimensions",
		// 		e2.getActualDimensions(),
		// 		is(equalTo(3)));
		// }
		try {
			tested.remove(make_point(4, 4));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(4, 4))));
		}
		try {
			tested.remove(make_point(2, 2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		List<Point> list = tested.get();
		assertThat(list, allOf(is(notNullValue()), hasSize(2)));
		assertThat(list, contains(make_point(1, 1), make_point(3, 3)));
	}

	@Test
	public void test_removeAfter()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			tested.add(make_point(1));
			tested.add(make_point(1));
			tested.add(make_point(2));
			tested.add(make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		try {
			tested.removeAfter(make_point(2, 2));
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
		try {
			tested.removeAfter(make_point(2));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(NoSuchPointException.class)));
			NoSuchPointException e2 = (NoSuchPointException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(2))));
		}
		try {
			tested.removeAfter(make_point(3));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(3))));
		}
		try {
			tested.removeAfter(make_point(1));
		} catch (Exception e) {
			fail("Should not throw");
		}
		List<Point> list = tested.get();
		assertThat(list, allOf(is(notNullValue()), hasSize(3)));
		assertThat(list, contains(make_point(1), make_point(1), make_point(2)));
	}

	@Test
	public void test_removeBefore()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			tested.add(make_point(1));
			tested.add(make_point(1));
			tested.add(make_point(2));
			tested.add(make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		try {
			tested.removeBefore(make_point(2, 2));
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
		try {
			tested.removeBefore(make_point(1));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(NoSuchPointException.class)));
			NoSuchPointException e2 = (NoSuchPointException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(1))));
		}
		try {
			tested.removeBefore(make_point(3));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(3))));
		}
		try {
			tested.removeBefore(make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		List<Point> list = tested.get();
		assertThat(list, allOf(is(notNullValue()), hasSize(3)));
		assertThat(list, contains(make_point(1), make_point(2), make_point(2)));
	}

	@Test
	public void test_addBefore()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			tested.add(make_point(1));
			tested.add(make_point(1));
			tested.add(make_point(2));
			tested.add(make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		try {
			tested.addBefore(make_point(3, 3), make_point(2));
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
		try {
			tested.addBefore(make_point(3), make_point(2, 2));
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
		try {
			tested.addBefore(make_point(3), make_point(5));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(5))));
		}
		try {
			tested.addBefore(make_point(3), make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		List<Point> list = tested.get();
		assertThat(list, allOf(is(notNullValue()), hasSize(5)));
		assertThat(list,
			contains(make_point(1),
				make_point(1),
				make_point(3),
				make_point(2),
				make_point(2)));
	}

	@Test
	public void test_addAfter()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			tested.add(make_point(1));
			tested.add(make_point(1));
			tested.add(make_point(2));
			tested.add(make_point(2));
		} catch (Exception e) {
			fail("Should not throw");
		}
		try {
			tested.addAfter(make_point(3, 3), make_point(2));
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
		try {
			tested.addAfter(make_point(3), make_point(2, 2));
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
		try {
			tested.addAfter(make_point(3), make_point(5));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongArgumentException.class)));
			WrongArgumentException e2 = (WrongArgumentException)e;
			assertThat(e2.getProblemPoint(), is(equalTo(make_point(5))));
		}
		try {
			tested.addAfter(make_point(3), make_point(1));
		} catch (Exception e) {
			fail("Should not throw");
		}
		List<Point> list = tested.get();
		assertThat(list, allOf(is(notNullValue()), hasSize(5)));
		assertThat(list,
			contains(make_point(1),
				make_point(1),
				make_point(3),
				make_point(2),
				make_point(2)));
	}

	@Test
	public void test_getByPosition()
	{
		GeometricShapeInterface tested = new GeometricShape();
		try {
			for (int i = -1; i < 2; ++i)
				for (int j = -1; j < 2; ++j)
					for (int k = -1; k < 2; ++k) {
						tested.add(make_point(i, j, k));
					}
		} catch (Exception e) {
			fail("Should not throw");
		}
		try {
			tested.getByPosition(Arrays.asList(1, 2, 3, 4));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 3 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(3)));
			assertThat("We gave 4 dimensions",
				e2.getActualDimensions(),
				is(equalTo(4)));
		}
		try {
			tested.getByPosition(Arrays.asList(1, 2));
			fail("Should throw");
		} catch (Exception e) {
			assertThat("This should throw proper exception",
				e,
				is(instanceOf(WrongNumberOfDimensionsException.class)));
			WrongNumberOfDimensionsException e2
				= (WrongNumberOfDimensionsException)e;
			assertThat("We should expect 3 dimension",
				e2.getExpectedDimensions(),
				is(equalTo(3)));
			assertThat("We gave 2 dimensions",
				e2.getActualDimensions(),
				is(equalTo(2)));
		}
		try {
			Optional<Point> tmp = tested.getByPosition(Arrays.asList(-1, 1, 0));
			assertThat(tmp,
				allOf(is(notNullValue()),
					is(equalTo(Optional.of(make_point(-1, 1, 0))))));
		} catch (Exception e) {
			fail("should not throw");
		}
	}
}
