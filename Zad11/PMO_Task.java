import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PMO_Task implements Runnable {
	private final Runnable code2run;
	private long creationTime;
	private long startTime;
	private long finishTime;
	private static List<String> errorLog;
	private static AtomicBoolean continuationFlag;

	public PMO_Task(Runnable code2run)
	{
		this.code2run = code2run;
		creationTime = System.currentTimeMillis();
	}

	public static void setErrorLog(List<String> errorLog)
	{
		PMO_Task.errorLog = errorLog;
	}

	public static void setContinuationFlag(AtomicBoolean continuationFlag)
	{
		PMO_Task.continuationFlag = continuationFlag;
	}

	@Override
	public void run()
	{
		assert errorLog != null : "Nie ustawiono errorLog";
		assert continuationFlag != null : "Nie ustawiono continuationFlag";

		if (!continuationFlag.get())
			return;

		startTime = System.currentTimeMillis();

		if (getDelay() > PMO_Consts.TASK_DELAY) {
			errorLog.add("BŁĄD: przeciążenie -> system nie działa poprawnie");
			continuationFlag.set(false);
			return;
		}

		code2run.run();
		finishTime = System.currentTimeMillis();
	}

	public long getDelay()
	{
		return startTime - creationTime;
	}

	public long getCPU()
	{
		return finishTime - startTime;
	}
}
