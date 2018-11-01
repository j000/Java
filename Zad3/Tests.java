import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.beans.*;
import org.hamcrest.core.*;
import org.junit.Test;

public class Tests {
	@Test
	public void test1()
	{
		assertThat("before proper initalization", Ship.getShip(0), is(nullValue()));

		assertThat("before proper initalization", Ship.getShip(1), is(nullValue()));

		assertThat("before proper initalization", Ship.getShip(20), is(nullValue()));

		Ship.setLimit(new ShipSizeLimit(new int[] {1, 2, 1}));

		assertThat("getShip(0) always returns null", Ship.getShip(0), is(nullValue()));

		assertThat("create ship", Ship.getShip(1), is(not(nullValue())));

		assertThat("hit limit", Ship.getShip(1), is(nullValue()));

		assertThat("create ship", Ship.getShip(2), is(not(nullValue())));

		Ship wreck = Ship.getShip(2);
		assertThat("create ship", wreck, is(not(nullValue())));

		assertThat("hit limit", Ship.getShip(2), is(nullValue()));

		wreck.shipwreck();

		wreck = Ship.getShip(2);
		assertThat("after wrecking", wreck, is(not(nullValue())));

		assertThat(Ship.getShip(2), is(nullValue()));

		assertThat(Ship.getShip(1), is(nullValue()));

		assertThat(Ship.getShip(3), is(not(nullValue())));

		assertThat(Ship.getShip(3), is(nullValue()));

		assertThat("outside limits", Ship.getShip(4), is(nullValue()));

		assertThat("outside limits", Ship.getShip(20), is(nullValue()));

		wreck.shipwreck();

		Ship.setLimit(new ShipSizeLimit(new int[] {0, 0, 0, 1}));

		assertThat("new limit", Ship.getShip(1), is(nullValue()));

		assertThat("new limit (old should produce)", Ship.getShip(2), is(nullValue()));

		assertThat("new limit", Ship.getShip(3), is(nullValue()));

		assertThat("new limit (old shouldn't produce)", Ship.getShip(4), is(not(nullValue())));

		assertThat("new limit", Ship.getShip(4), is(nullValue()));
	}
}
