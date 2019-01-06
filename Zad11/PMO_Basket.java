import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class PMO_Basket {
	private final List<Position2D> positions
		= Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong tickTime;
	private final AtomicInteger size;
	private final int width;
	private final BlockingDeque<PMO_Task> queue;
	private volatile Position2D middle;
	private final BasketController basketController;
	private final int initialSize;

	public PMO_Basket(int initialSize,
		int width,
		BlockingDeque<PMO_Task> queue,
		AtomicLong tickTime)
	{
		size = new AtomicInteger(initialSize);
		this.width = width;
		IntStream.rangeClosed(-initialSize / 2, initialSize / 2).forEach(i -> {
			positions.add(new Position2D(width / 2 + i, 0));
		});
		middle = getMiddle();
		this.queue = queue;
		this.tickTime = tickTime;
		this.basketController = new BasketController();
		this.initialSize = initialSize;
	}

	private class BasketController implements BasketControlInterface {
		private final ReentrantLock lock = new ReentrantLock();

		private void createTask(Runnable code2run)
		{
			lock.lock();

			PMO_Threads.sleep(tickTime.get() / 2);

			queue.addFirst(new PMO_Task(() -> { code2run.run(); }));

			PMO_Threads.sleep(tickTime.get() / 2);

			lock.unlock();
		}

		@Override
		public void moveLeft()
		{
			createTask(PMO_Basket.this ::moveLeft);
		}

		@Override
		public void moveRight()
		{
			createTask(PMO_Basket.this ::moveRight);
		}

		@Override
		public Position2D getPosition()
		{
			return middle;
		}
	}

	private Position2D getFirst()
	{
		return positions.get(0);
	}

	private Position2D getLast()
	{
		return positions.get(positions.size() - 1);
	}

	private Position2D getMiddle()
	{
		return positions.get(positions.size() / 2);
	}

	private void moveRight()
	{
		Position2D next = Direction.RIGHT.nextPosition2D(getLast());
		if (next.getX() < width) {
			positions.remove(getFirst());
			positions.add(next);
		}
		middle = getMiddle();
	}

	private void moveLeft()
	{
		Position2D next = Direction.LEFT.nextPosition2D(getFirst());
		if (next.getX() >= 0) {
			positions.remove(getLast());
			positions.add(0, next);
		}
		middle = getMiddle();
	}

	public void shrink()
	{
		queue.addFirst(new PMO_Task(() -> {
			positions.remove(getFirst());
			positions.remove(getLast());
			size.addAndGet(-2);
		}));
	}

	public boolean contains(Position2D position)
	{
		return positions.contains(position);
	}

	public BasketControlInterface getBasketControlInterface()
	{
		return basketController;
	}

	public int getInitialSize()
	{
		return initialSize;
	}

	@Override
	public String toString()
	{
		return positions.toString();
	}
}
