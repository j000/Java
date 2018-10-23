import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.beans.*;
import org.hamcrest.core.*;
import org.junit.Test;

public class Tests {
	private static SimpleCalculations calc = new SimpleCalculations();

	private Point createPoint(double[] coordinates)
	{
		Point result = new Point();
		result.setNumberOfDimensions(coordinates.length);
		for (int i = 0; i < coordinates.length; ++i) {
			result.setPosition(i, coordinates[i]);
		}
		return result;
	}

	@Test
	public void equidistantPoints()
	{
		Point result[];

		result = calc.equidistantPoints(null, null, 0);
		assertThat(result, is(equalTo(null)));

		result = calc.equidistantPoints(
			createPoint(new double[] {0}), createPoint(new double[] {1}), 0);
		assertThat(result, is(equalTo(null)));

		result = calc.equidistantPoints(null, createPoint(new double[] {1}), 1);
		assertThat(result, is(equalTo(null)));

		result = calc.equidistantPoints(createPoint(new double[] {0}), null, 1);
		assertThat(result, is(equalTo(null)));

		result = calc.equidistantPoints(
			createPoint(new double[] {0}), createPoint(new double[] {1}), 1);
		assertThat(
			result, equalTo(new Point[] {createPoint(new double[] {0.5})}));

		result = calc.equidistantPoints(
			createPoint(new double[] {0}), createPoint(new double[] {3}), 2);
		assertThat(
			result,
			equalTo(new Point[] {createPoint(new double[] {1}),
								 createPoint(new double[] {2})}));

		result = calc.equidistantPoints(
			createPoint(new double[] {0, 0}),
			createPoint(new double[] {3, 3}),
			2);
		assertThat(
			result,
			equalTo(new Point[] {createPoint(new double[] {1, 1}),
								 createPoint(new double[] {2, 2})}));

		Point expected[] = new Point[100];
		for (int i = 0; i < 100; i++) {
			expected[i] = createPoint(new double[] {i, 1});
		}

		result = calc.equidistantPoints(
			createPoint(new double[] {-1, 1}),
			createPoint(new double[] {100, 1}),
			100);

		assertThat(result, equalTo(expected));
	}

	@Test
	public void geometricCenter()
	{
		Point result;

		result = calc.geometricCenter(null);
		assertThat(result, is(equalTo(null)));

		result = calc.geometricCenter(new Point[] {});
		assertThat(result, is(equalTo(null)));

		result
			= calc.geometricCenter(new Point[] {createPoint(new double[] {1})});
		Point expected = createPoint(new double[] {1});

		assertThat(result, equalTo(expected));

		result = calc.geometricCenter(new Point[] {
			createPoint(new double[] {1}), createPoint(new double[] {1})});
		expected = createPoint(new double[] {1});

		assertThat(result, is(equalTo(expected)));

		result = calc.geometricCenter(new Point[] {
			createPoint(new double[] {0}), createPoint(new double[] {2})});
		expected = createPoint(new double[] {1});

		assertThat(result, equalTo(expected));

		result = calc.geometricCenter(
			new Point[] {createPoint(new double[] {0, 2}),
						 createPoint(new double[] {2, 0})});
		expected = createPoint(new double[] {1, 1});

		assertThat(result, equalTo(expected));

		result = calc.geometricCenter(
			new Point[] {createPoint(new double[] {1, 1}),
						 createPoint(new double[] {2, 3})});
		assertThat(result, equalTo(createPoint(new double[] {1.5, 2})));
	}

	@Test
	public void next()
	{
		Point result;

		result = calc.next(null, null, 0);
		assertThat(result, is(equalTo(null)));

		result = calc.next(
			createPoint(new double[] {0}), createPoint(new double[] {1}), 0);
		assertThat(result, equalTo(createPoint(new double[] {1})));

		result = calc.next(
			createPoint(new double[] {0}), createPoint(new double[] {1}), 1);
		assertThat(result, equalTo(createPoint(new double[] {2})));

		result = calc.next(
			createPoint(new double[] {0}), createPoint(new double[] {1}), 2);
		assertThat(result, equalTo(createPoint(new double[] {3})));

		result = calc.next(
			createPoint(new double[] {0}), createPoint(new double[] {-1}), 1);
		assertThat(result, equalTo(createPoint(new double[] {-2})));

		result = calc.next(
			createPoint(new double[] {0, 4}),
			createPoint(new double[] {-1, 4}),
			1);
		assertThat(result, equalTo(createPoint(new double[] {-2, 4})));

		result = calc.next(
			createPoint(new double[] {1, 2}),
			createPoint(new double[] {1, 1}),
			2);
		assertThat(result, equalTo(createPoint(new double[] {1, -1})));
	}
}

