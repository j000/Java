import java.util.function.Consumer;

public class Example_GameInterface implements GameInterface {
	private Consumer<FallingObjectBasicInterface> newObjectListener;
	private Consumer<Integer> shrinkConsumer;

	@Override
	public int getBasketInitialSize()
	{
		return 0;
	}

	@Override
	public void addNewObjectListener(
		Consumer<FallingObjectBasicInterface> listener)
	{
		newObjectListener = listener;
	}

	@Override
	public void addShrinkBasketListener(Consumer<Integer> listener)
	{
		shrinkConsumer = listener;
	}

	@Override
	public int getScore()
	{
		return 0;
	}

	@Override
	public long getTickTime()
	{
		return 0;
	}

	@Override
	public int getWidth()
	{
		return 0;
	}

	@Override
	public int getHeight()
	{
		return 0;
	}

	@Override
	public BasketControlInterface getController()
	{
		return null;
	}

	public static void main(String[] args)
	{
		GameInterface gi = new Example_GameInterface();
		ControllerInterface controller = new Example_Controller();

		controller.setGame(gi);

		((Example_GameInterface)gi)
			.newObjectListener.accept(new Example_FallingObject(10));
		((Example_GameInterface)gi)
			.newObjectListener.accept(new Example_FallingObject(20));
		((Example_GameInterface)gi)
			.newObjectListener.accept(new Example_FallingObject(30));

		((Example_GameInterface)gi).shrinkConsumer.accept(9);
		((Example_GameInterface)gi).shrinkConsumer.accept(7);
		((Example_GameInterface)gi).shrinkConsumer.accept(5);
	}
}
