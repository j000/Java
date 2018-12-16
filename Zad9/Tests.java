import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.management.ManagementFactory.THREAD_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.newPlatformMXBeanProxy;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;

public class Tests {
	private static final double DELTA = 1e-5;
	private static final List<Long> systemThreadsIds = new ArrayList<>();

	static class Point implements PointInterface {
		private final int[] positions = new int[2];

		public Point(int x, int y)
		{
			positions[0] = x;
			positions[1] = y;
		}

		public int[] getPositions()
		{
			return positions.clone();
		}
	}

	static class SimplePointGenerator implements PointGeneratorInterface {
		private static final int max = 4;
		public static int max_x = max;
		public static int max_y = max;

		private final ReentrantLock lock = new ReentrantLock();

		private volatile int last_x = 0;
		private volatile int last_y = 0;

		public PointInterface getPoint()
		{
			lock.lock();

			last_x++;
			if (last_x > max_x)
				last_x = 0;
			int x = last_x;

			last_y++;
			if (last_y > max_y)
				last_y = 0;
			int y = last_y;

			lock.unlock();

			return new Point(x, y);
		}
	}

	private static Map<ThreadInfo, Long> getThreadList()
	{
		ThreadMXBean tmbean = ManagementFactory.getThreadMXBean();

		if (!tmbean.isThreadCpuTimeSupported()) {
			System.err.println(
				"This VM does not support thread CPU time monitoring");
		} else {
			tmbean.setThreadCpuTimeEnabled(true);
		}

		// Get all threads and their ThreadInfo objects
		// with no stack trace
		long[] tids = tmbean.getAllThreadIds();
		ThreadInfo[] tinfos = tmbean.getThreadInfo(tids);

		// build a map with key = ThreadInfo and value = CPU time
		Map<ThreadInfo, Long> map = new LinkedHashMap<>();
		for (int i = tids.length - 1; i >= 0; --i) {
			// filter out threads that have been terminated
			if (tinfos[i] == null || systemThreadsIds.contains(tids[i]))
				continue;
			long cpuTime = tmbean.getThreadCpuTime(tids[i]);
			if (cpuTime == -1)
				continue;

			map.put(tinfos[i], new Long(cpuTime));
		}

		return map;
	}

	public void prettyPrint(int[][] arr)
	{
		final int width = 6;
		final int width2 = 2;
		System.out.printf("%" + width2 + "s", "");
		for (int j = 0; j < arr[0].length; ++j) {
			System.out.printf(" %" + width + "d", j);
		}
		System.out.print("\n");

		for (int i = 0; i < arr.length; ++i) {
			System.out.printf("%" + width2 + "d", i);
			for (int j = 0; j < arr[i].length; ++j) {
				System.out.printf(" %" + width + "d", arr[i][j]);
			}
			System.out.print("\n");
		}
	}

	public void printThreads(Map<ThreadInfo, Long> threads)
	{
		threads.forEach((k, v)
							-> System.out.printf("\n%3d (%-15s): %16d",
								k.getThreadId(),
								k.getThreadName(),
								v));
	}

	public void printThreads()
	{
		printThreads(getThreadList());
	}

	@BeforeClass
	public static void findSystemThreads()
	{
		Map<ThreadInfo, Long> threads = getThreadList();
		threads.forEach((k, v) -> systemThreadsIds.add(k.getThreadId()));
	}

	@Test
	public void test_example() throws InterruptedException, ExecutionException
	{
		final SimplePointGenerator spg = new SimplePointGenerator();
		final ParallelCalculationsInterface tested = new ParallelCalculations();
		final int startingThreads = Thread.activeCount();

		tested.setNumberOfThreads(4);
		tested.setPointGenerator(spg);

		// tested.start();
		{
			ExecutorService executor = Executors.newCachedThreadPool();
			Future<Void> future = executor.submit(() -> {
				tested.start();
				return null;
			});

			try {
				future.get(50, TimeUnit.MILLISECONDS);
			} catch (TimeoutException ex) {
				fail("start() should start new tasks and return quickly");
			}
			executor.shutdown();
		}

		assertThat("There should be more threads",
			Thread.activeCount(),
			is(greaterThan(startingThreads)));

		synchronized (this)
		{
			this.wait(1000 /* ms */);
		}

		// tested.suspendCalculations();
		{
			ExecutorService executor = Executors.newCachedThreadPool();
			Future<Void> future = executor.submit(() -> {
				tested.suspendCalculations();
				return null;
			});

			try {
				future.get(50, TimeUnit.MILLISECONDS);
			} catch (TimeoutException ex) {
				fail("suspendCalculations() should return quickly");
			}
			executor.shutdown();
		}

		{
			double[] tmp = tested.getGeometricCenter();
			assertThat(tmp, is(not(nullValue())));
			/* (╯°□°）╯︵ ┻━┻
			 * java... */
			// assertThat(tmp, is(arrayWithSize(equalTo(2))));
			// assertThat(tmp, everyItem(closeTo(2.0, DELTA)));
			assertThat(tmp[0], is(closeTo(2.0, DELTA)));
			assertThat(tmp[1], is(closeTo(2.0, DELTA)));

			int[][] histogram = tested.getHistogram();
			assertThat(
				histogram, is(allOf(not(nullValue()), not(emptyArray()))));
			for (int i = 0; i < histogram.length; ++i) {
				for (int j = 0; j < histogram[i].length; ++j) {
					if (i < 5 && i == j)
						assertThat("histogram[" + i + "][" + j + "]",
							histogram[i][j],
							is(greaterThan(0)));
					else
						assertThat("histogram[" + i + "][" + j + "]",
							histogram[i][j],
							is(equalTo(0)));
				}
			}
			// prettyPrint(histogram);
		}

		{
			/* (╯°□°）╯︵ ┻━┻
			 * java... */
			final long sum1[] = {0L};
			{
				Map<ThreadInfo, Long> threads = getThreadList();
				threads.forEach((k, v) -> sum1[0] += v);
				System.out.print("\nNo user threads should run here:");
				printThreads(threads);
			}

			synchronized (this)
			{
				this.wait(1000 /* ms */);
			}

			/* (╯°□°）╯︵ ┻━┻
			 * java... */
			final long sum2[] = {0L};
			{
				Map<ThreadInfo, Long> threads = getThreadList();
				threads.forEach((k, v) -> sum2[0] += v);
				System.out.print("\nLook for changes here if assertion fails:");
				printThreads(threads);
			}
			assertThat("No user threads should be running",
				sum2[0] - sum1[0],
				is(lessThan(1000000L)));
		}

		tested.continueCalculations();

		{
			/* (╯°□°）╯︵ ┻━┻
			 * java... */
			final long sum1[] = {0L};
			{
				Map<ThreadInfo, Long> threads = getThreadList();
				threads.forEach((k, v) -> sum1[0] += v);
				System.out.print("\nUser threads should run here:");
				printThreads(threads);
			}

			synchronized (this)
			{
				this.wait(1000 /* ms */);
			}

			/* (╯°□°）╯︵ ┻━┻
			 * java... */
			final long sum2[] = {0L};
			{
				Map<ThreadInfo, Long> threads = getThreadList();
				threads.forEach((k, v) -> sum2[0] += v);
				System.out.print("\nLook for changes here if assertion fails:");
				printThreads(threads);
			}
			assertThat("User threads should be running",
				sum2[0] - sum1[0],
				is(greaterThan(1000000L)));
		}

		// tested.suspendCalculations();
		{
			ExecutorService executor = Executors.newCachedThreadPool();
			Future<Void> future = executor.submit(() -> {
				tested.suspendCalculations();
				return null;
			});

			try {
				future.get(100, TimeUnit.MILLISECONDS);
			} catch (TimeoutException ex) {
				fail("suspendCalculations() should not take forever");
			}
			executor.shutdown();
		}

		{
			double[] tmp = tested.getGeometricCenter();
			assertThat(tmp[0], is(closeTo(2.0, DELTA)));
			assertThat(tmp[1], is(closeTo(2.0, DELTA)));
			int[][] histogram = tested.getHistogram();
			for (int i = 0; i < histogram.length; ++i) {
				for (int j = 0; j < histogram[i].length; ++j) {
					if (i < 5 && i == j)
						assertThat("histogram[" + i + "][" + j + "]",
							histogram[i][j],
							is(greaterThan(0)));
					else
						assertThat("histogram[" + i + "][" + j + "]",
							histogram[i][j],
							is(equalTo(0)));
				}
			}
			// prettyPrint(histogram);
		}
	}
}
