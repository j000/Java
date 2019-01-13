import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// JUnit4
import static org.junit.Assert.*;
import org.junit.Test;

// Junit5 (hopefully)
// import org.junit.jupiter.api.*;

public class Tests {
	@Test
	public void test1()
	{
		BetterPoint bp = new BetterPoint();
		bp.setDimensions(3);

		assertThat("Lock level starts at 0", bp.lockLevel(), is(0));

		for (int i = 0; i < 3; ++i) {
			bp.set(i, i + 0.5);
		}
		for (int i = 0; i < 3; ++i) {
			assertThat("Proper value", bp.get(i), is(i + 0.5));
		}

		// locking
		bp.lock(null);
		assertThat("Null does not lock", bp.lockLevel(), is(0));
		bp.lock("pass1");
		assertThat("Locked", bp.lockLevel(), is(1));

		// lock test
		for (int i = 0; i < 3; ++i) {
			bp.set(i, i + 1.5);
			assertThat("No change while locked", bp.get(i), is(i + 0.5));
		}

		// unlocking
		bp.unlock(null);
		assertThat("Null does not unlock", bp.lockLevel(), is(1));
		bp.unlock("pass2");
		assertThat("Wrong password does not unlock", bp.lockLevel(), is(1));
		bp.unlock("pass1");
		assertThat("Unlocked", bp.lockLevel(), is(0));
		for (int i = 0; i < 3; ++i) {
			bp.set(i, i + 1.5);
			assertThat("Changed while unlocked", bp.get(i), is(i + 1.5));
		}

		// two passwords
		bp.lock("pass1");
		bp.lock("pass2");
		assertThat("Locked", bp.lockLevel(), is(2));
		bp.unlock("pass3");
		assertThat("Still locked", bp.lockLevel(), is(2));
		bp.unlock("pass2");
		assertThat("Locked, lower level", bp.lockLevel(), is(1));
		bp.unlock("pass3");
		assertThat("Still locked", bp.lockLevel(), is(1));
		bp.unlock("pass1");
		assertThat("Unlocked", bp.lockLevel(), is(0));

		// same passwords
		bp.lock("pass1");
		bp.lock("pass1");
		assertThat("Locked", bp.lockLevel(), is(2));
		bp.unlock("pass2");
		assertThat("Still locked", bp.lockLevel(), is(2));
		bp.unlock("pass1");
		assertThat("Locked, lower level", bp.lockLevel(), is(1));
		bp.unlock("pass2");
		assertThat("Still locked", bp.lockLevel(), is(1));
		bp.unlock("pass1");
		assertThat("Unlocked", bp.lockLevel(), is(0));

		// move
		for (int i = 0; i < 3; ++i) {
			bp.move(i, -1);
			assertThat("Move worked while unlocked", bp.get(i), is(i + 0.5));
		}

		bp.lock("pass");
		for (int i = 0; i < 3; ++i) {
			bp.move(i, -1);
			assertThat(
				"Move did not work while locked", bp.get(i), is(i + 0.5));
		}
		bp.unlock("pass");
		for (int i = 0; i < 3; ++i) {
			bp.move(i, -1);
			assertThat("Move worked while unlocked", bp.get(i), is(i - 0.5));
		}
	}
}
