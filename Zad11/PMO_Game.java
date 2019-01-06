import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class PMO_Game implements GameInterface {
	private final AtomicLong score = new AtomicLong(0);
	private final AtomicLong tickTime;
	private final PMO_Basket basket;
	private final int WIDTH;
	private final int HEIGHT;

	public PMO_Game(
		AtomicLong tickTime, PMO_Basket basket, int width, int height)
	{
		this.tickTime = tickTime;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.basket = basket;
	}

	@Override
	public int getScore()
	{
		return score.intValue();
	}

	@Override
	public long getTickTime()
	{
		return tickTime.get();
	}

	@Override
	public int getWidth()
	{
		return WIDTH;
	}

	@Override
	public int getHeight()
	{
		return HEIGHT;
	}

	@Override
	public int getBasketInitialSize()
	{
		return basket.getInitialSize();
	}

	@Override
	public BasketControlInterface getController()
	{
		return basket.getBasketControlInterface();
	}

	@Override
	public void addNewObjectListener(
		Consumer<FallingObjectBasicInterface> listener)
	{
	}

	@Override
	public void addShrinkBasketListener(Consumer<Integer> listener)
	{
	}
}
