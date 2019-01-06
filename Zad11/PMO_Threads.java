import java.util.concurrent.atomic.AtomicBoolean;

public class PMO_Threads {
	public static Thread createThreadAndStartAsDaemon(Runnable code)
	{
		Thread th = new Thread(code);
		th.setDaemon(true);
		th.start();
		return th;
	}

	public static boolean sleep(long time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	public static boolean sleep(long time, AtomicBoolean flag)
	{
		long start = System.currentTimeMillis();
		do {
			sleep(100);
			if ((System.currentTimeMillis() - start) > time)
				return true;
		} while (flag.get());
		return false;
	}

	public static String getThreadName()
	{
		return Thread.currentThread().getName();
	}
}
