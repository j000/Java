import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.beans.*;
import org.hamcrest.core.*;
import org.junit.Test;

public class Tests {
	@Test
	public void test1()
	{
		Ship result;

		result = Ship.getShip(0);
		assertThat("before proper initalization", result, is(nullValue()));

		result = Ship.getShip(1);
		assertThat("before proper initalization", result, is(nullValue()));

		result = Ship.getShip(20);
		assertThat("before proper initalization", result, is(nullValue()));

		Ship.setLimit(new ShipSizeLimit(new int[] {1, 2, 1}));

		result = Ship.getShip(0);
		assertThat("getShip(0) always returns null", result, is(nullValue()));

		result = Ship.getShip(1);
		assertThat(result, is(not(nullValue())));

		result = Ship.getShip(1);
		assertThat("hit limit", result, is(nullValue()));

		result = Ship.getShip(2);
		assertThat(result, is(not(nullValue())));

		Ship wreck = Ship.getShip(2);
		assertThat(wreck, is(not(nullValue())));

		result = Ship.getShip(2);
		assertThat(result, is(nullValue()));

		wreck.shipwreck();

		result = Ship.getShip(2);
		assertThat(result, is(not(nullValue())));

		result = Ship.getShip(2);
		assertThat(result, is(nullValue()));

		result = Ship.getShip(1);
		assertThat(result, is(nullValue()));

		result = Ship.getShip(3);
		assertThat(result, is(not(nullValue())));

		result = Ship.getShip(3);
		assertThat(result, is(nullValue()));

		result = Ship.getShip(20);
		assertThat(result, is(nullValue()));
	}
}
