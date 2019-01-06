import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class PMO_TasksExecutor {
	private final BlockingDeque<PMO_Task> queue = new LinkedBlockingDeque<>();
	private Thread thread;
	private final AtomicBoolean continuationFlag;
	private final AtomicLong tasksExecuted = new AtomicLong();
	private final AtomicLong totalCPUTime = new AtomicLong();
	private final AtomicLong totalDelayTime = new AtomicLong();
	private List<String> errorLog;

	public PMO_TasksExecutor(
		List<String> errorLog, AtomicBoolean continuationFlag)
	{
		this.errorLog = errorLog;
		this.continuationFlag = continuationFlag;
	}

	public void start()
	{
		thread = PMO_Threads.createThreadAndStartAsDaemon(new Executor());
	}

	public BlockingDeque<PMO_Task> getQueue()
	{
		return queue;
	}

	private class Executor implements Runnable {
		@Override
		public void run()
		{
			assert errorLog != null : "Nie ustawiono referencji do errorLog";
			PMO_Task task;
			while (continuationFlag.get()) {
				try {
					task = queue.takeLast();
					task.run();
					tasksExecuted.incrementAndGet();
					totalDelayTime.addAndGet(task.getDelay());
					totalCPUTime.addAndGet(task.getCPU());
				} catch (InterruptedException e) {
					errorLog.add(
						"W trakcie metody takeLast pojawił się wyjątek InterruptedException");
				} catch (Exception e) {
					errorLog.add("Executor odnotował pojawienie się wyjątku "
						+ e.toString());
				}
			}
		}
	}

	public long getTasks()
	{
		return tasksExecuted.get();
	}

	public long getTotalCPUTime()
	{
		return totalCPUTime.get();
	}

	public long getTotalDelay()
	{
		return totalDelayTime.get();
	}
}
