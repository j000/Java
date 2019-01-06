import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class PMO_FallingObjectRandomGenerator
	implements PMO_GeneratorInterface {
	private AtomicLong tickTime;
	private final AtomicBoolean continuationFlag;
	private PMO_Observable observable;
	private int lastMandatory;
	private final Random rnd = new Random();
	private final int WIDTH;
	private final int HEIGHT;

	public PMO_FallingObjectRandomGenerator(AtomicLong tickTime,
		AtomicBoolean continuationFlag,
		int width,
		int height)
	{
		this.tickTime = tickTime;
		this.continuationFlag = continuationFlag;
		WIDTH = width;
		HEIGHT = height;
	}

	@Override
	public void run()
	{
		while (continuationFlag.get()) {
			PMO_Threads.sleep(tickTime.get());
			if (rnd.nextBoolean())
				continue;
			boolean mandatory = rnd.nextDouble() < 0.2;

			if (mandatory && (lastMandatory < WIDTH)) {
				mandatory = false;
			}
			if (mandatory)
				lastMandatory = 0;
			else
				lastMandatory++;

			int col = 1 + rnd.nextInt(WIDTH - 2);

			PMO_FallingObject newObject = new PMO_FallingObject(
				mandatory, rnd.nextInt(100), new Position2D(col, HEIGHT - 1));
			System.out.println(newObject);
			observable.sendUpdates(newObject);
		}
	}

	@Override
	public void setObservable(PMO_Observable observable)
	{
		this.observable = observable;
	}
}
