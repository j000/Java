import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;

public class Tests {
	@Test
	public void test_example()
	{
		Verify verify = new Verify();
		new Checker(verify);
		new Starter().accept("Checker");
		for (Map.Entry<String, Integer> entry : verify.getChecks().entrySet()) {
			assertThat(
				"Test for " + entry.getKey(), entry.getValue(), equalTo(0));
		}
		// assertThat("Not all methods called", verify.check());
		// assertThat("Not all methods called",
		// 	verify.getChecks(),
		// 	not(hasValue(not(equalTo(0)))));
	}
}

class Verify {
	private Map<String, Integer> checks = new HashMap<>();

	protected Verify()
	{
		checks.put("meh", 3);
		checks.put("weird", 2);
		checks.put("singleString", 2);
	}

	public void doCheck(String name)
	{
		checks.put(name, checks.getOrDefault(name, 0) - 1);
	}

	public Map<String, Integer> getChecks()
	{
		return new HashMap<>(checks);
	}

	public boolean check()
	{
		for (Integer v : checks.values()) {
			if (v != 0)
				return false;
		}
		return true;
	}
}

class Checker {
	private static Verify verify = null;
	private Verify myVerify;

	public Checker()
	{
		myVerify = verify;
	}

	public Checker(Verify v)
	{
		verify = v;
	}

	////////////////////////////////////////

	public void noAnnotations()
	{
		fail("Shoud not run method without annotations");
	}

	@MethodDisabled
	public void disabled()
	{
		fail("Should not run disabled methods");
	}

	@MethodToStart(3)
	public void meh()
	{
		myVerify.doCheck("meh");
	}

	@MethodToStart(3)
	public void trap(int a)
	{
		fail("Check method parameters!");
	}

	@MethodToStart(3)
	public void trap(String a, int b)
	{
		fail("Check method parameters!");
	}

	@MethodToStart(3)
	public void trap(int a, int b)
	{
		fail("Check method parameters!");
	}

	@MethodToStart(2)
	@MethodDisabled
	public void disabled2()
	{
		fail("Should not run disabled methods, "
			+ "even when it's marked as MethodToStart");
	}

	@StringParameter("Lol")
	public void onlyStringParameter()
	{
		fail("Should not run methods with only StringParameter");
	}

	@MethodDisabled
	@StringParameter("Lol")
	public void disabledStringParameter()
	{
		fail("Should not run disabled methods with only StringParameter");
	}

	@MethodToStart(2)
	@StringParameter("Lol")
	public void weird()
	{
		myVerify.doCheck("weird");
		// TODO: fix java to allow detecting parameters ;)
	}

	@MethodToStart(2)
	@StringParameter("Lol")
	public void singleString(String a)
	{
		if (a.equals("Lol"))
			myVerify.doCheck("singleString");
	}

	@MethodDisabled
	@MethodToStart(2)
	@StringParameter("Lol")
	public void allAnnotations()
	{
		fail("Should not run disabled methods, even annotated ones");
	}
}
