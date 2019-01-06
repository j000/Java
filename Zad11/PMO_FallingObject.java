import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class PMO_FallingObject implements FallingObjectBasicInterface {
	private static final AtomicLong counter = new AtomicLong(0);
	private final long id;
	private final boolean mandatory;
	private volatile boolean collisionDetected;
	private final long value;
	private Position2D initialLocation;
	private final List<Consumer<Position2D>> listeners
		= Collections.synchronizedList(new ArrayList<>());
	private volatile Position2D actualPosition;
	private volatile Position2D nextPosition;

	public PMO_FallingObject(
		boolean mandatory, long value, Position2D initialLocation)
	{
		this(mandatory, value);
		initializePosition(initialLocation);
	}

	public PMO_FallingObject(boolean mandatory, long value)
	{
		id = counter.incrementAndGet();
		this.mandatory = mandatory;
		this.value = value;
		collisionDetected = false;
	}

	private void initializePosition(Position2D initialLocation)
	{
		this.initialLocation = initialLocation.clone();
		actualPosition = this.initialLocation;
		nextPosition = Direction.DOWN.nextPosition2D(actualPosition);
	}

	public void setInitialLocation(Position2D initialLocation)
	{
		initializePosition(initialLocation);
	}

	public PMO_FallingObject clone()
	{
		if (initialLocation == null)
			return new PMO_FallingObject(mandatory, value);
		else
			return new PMO_FallingObject(
				mandatory, value, initialLocation.clone());
	}

	public void startFalling(
		AtomicLong tickTime, BlockingDeque<PMO_Task> queue, PMO_Arbiter arbiter)
	{
		PMO_Threads.createThreadAndStartAsDaemon(() -> {
			while (actualPosition.getY() >= 0) {
				queue.addFirst(new PMO_Task(() -> {
					if ((actualPosition.getY() == 0 || nextPosition.getY() == 0)
						&& (!collisionDetected)) {
						collisionDetected = arbiter.collision(this);
					}
					updatePosition(nextPosition);
					if (collisionDetected)
						return;
				}));
				PMO_Threads.sleep(tickTime.get());
			}
		});
	}

	private void generateThread(Consumer<Position2D> consumer)
	{
		PMO_Threads.createThreadAndStartAsDaemon(
			() -> consumer.accept(actualPosition));
	}

	private void sendUpdates()
	{
		listeners.stream().forEach(l -> generateThread(l));
	}

	public void updatePosition(Position2D newPosition2D)
	{
		actualPosition = newPosition2D;
		nextPosition = Direction.DOWN.nextPosition2D(actualPosition);
		sendUpdates();
	}

	public Position2D nextPosition2D()
	{
		return nextPosition;
	}

	public Position2D getActualPosition()
	{
		return actualPosition;
	}

	@Override
	public long getUniqID()
	{
		return id;
	}

	@Override
	public boolean mandatoryInterception()
	{
		return mandatory;
	}

	@Override
	public long getValue()
	{
		return value;
	}

	@Override
	public Position2D getInitialLocation()
	{
		return initialLocation;
	}

	@Override
	public void addLocationListener(Consumer<Position2D> listener)
	{
		listeners.add(listener);
	}

	@Override
	public String toString()
	{
		return "PMO_FallingObject{"
			+ "id=" + id + ", mandatory=" + mandatory + ", value=" + value
			+ ", actualPosition=" + actualPosition + '}';
	}
}
