import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Example_FallingObject implements FallingObjectBasicInterface {
	private Consumer<Position2D> listener;
	private static ScheduledExecutorService executor
		= Executors.newSingleThreadScheduledExecutor();
	private Position2D position;
	private static long counter;
	private final long id;

	public Example_FallingObject(int x)
	{
		position = new Position2D(x, 100);
		id = counter++;

		executor.scheduleAtFixedRate(() -> {
			position = Direction.DOWN.nextPosition2D(position);
			listener.accept(position);
		}, 100, 500, TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString()
	{
		return "Example_FallingObject{"
			+ "id=" + id + '}';
	}

	@Override
	public long getUniqID()
	{
		return id;
	}

	@Override
	public boolean mandatoryInterception()
	{
		return false;
	}

	@Override
	public long getValue()
	{
		return 0;
	}

	@Override
	public Position2D getInitialLocation()
	{
		return null;
	}

	@Override
	public void addLocationListener(Consumer<Position2D> listener)
	{
		this.listener = listener;
	}
}
