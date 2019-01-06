import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class PMO_Main {
	private final AtomicLong tickTime
		= new AtomicLong(PMO_Consts.INITIAL_TICK_TIME);
	private final AtomicBoolean continuationFlag = new AtomicBoolean(true);
	private final List<String> errorLog
		= Collections.synchronizedList(new LinkedList<>());
	private final AtomicLong score = new AtomicLong(0);

	private final PMO_TasksExecutor executor;
	private PMO_Basket basket;
	private PMO_Game game;
	private PMO_TxtGraphics graphics;
	private PMO_FallingObjectsObservableGenerator observableGenerator;
	private PMO_Arbiter arbiter;

	private final int HEIGHT;
	private final int WIDTH;
	private final int BASKET_INITIAL_SIZE;

	{
		PMO_Task.setContinuationFlag(continuationFlag);
		PMO_Task.setErrorLog(errorLog);
		executor = new PMO_TasksExecutor(errorLog, continuationFlag);
	}

	/**
	 * Odpowiada za uruchomienie opadania obiektu. Opadanie startuje po czasie
	 * tickTime.
	 */
	private class FallingObjectInitiator
		implements Consumer<FallingObjectBasicInterface> {
		@Override
		public void accept(
			FallingObjectBasicInterface fallingObjectBasicInterface)
		{
			PMO_Threads.createThreadAndStartAsDaemon(() -> {
				PMO_Threads.sleep(tickTime.get());
				((PMO_FallingObject)fallingObjectBasicInterface)
					.startFalling(tickTime, executor.getQueue(), arbiter);
			});
		}
	}

	private void view(long period)
	{
		ScheduledExecutorService ses
			= Executors.newSingleThreadScheduledExecutor();

		ses.scheduleAtFixedRate(() -> {
			System.out.println(graphics);
			if (!continuationFlag.get()) {
				ses.shutdownNow();
			}
		}, 100, period, TimeUnit.MILLISECONDS);
	}

	public PMO_Main(int HEIGHT, int BASKET_INITIAL_SIZE)
	{
		this.HEIGHT = HEIGHT;
		this.BASKET_INITIAL_SIZE = BASKET_INITIAL_SIZE;
		PMO_FallingObjectsProgrammableGenerator generator
			= new PMO_FallingObjectsTestGenerator(
				continuationFlag, tickTime, HEIGHT);
		WIDTH = generator.getWidth();

		basket = new PMO_Basket(
			BASKET_INITIAL_SIZE, WIDTH, executor.getQueue(), this.tickTime);

		arbiter = new PMO_Arbiter(basket, errorLog, continuationFlag, score);

		game = new PMO_Game(tickTime, basket, WIDTH, HEIGHT);
		game.addNewObjectListener(new FallingObjectInitiator());

		graphics = new PMO_TxtGraphics(WIDTH, HEIGHT, basket, score);

		observableGenerator
			= new PMO_FallingObjectsObservableGenerator(generator);
		observableGenerator.addNewObjectListener(new FallingObjectInitiator());
		observableGenerator.addNewObjectListener(
			graphics.new NewObjectListener());

		observableGenerator.start();
		executor.start();
	}

	private static ControllerInterface createController()
	{
		ControllerInterface controller = null;

		try {
			Class<?> controllerClass = Class.forName("Controller");
			controller = (ControllerInterface)controllerClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException
			| IllegalAccessException e) {
			controller = new PMO_Controller();
		}

		return controller;
	}

	public static void main(String[] args)
	{
		PMO_Main main = new PMO_Main(30, 9);

		ControllerInterface controller = createController();
		PMO_Threads.createThreadAndStartAsDaemon(
			() -> { controller.setGame(main.game); });
		main.view(PMO_Consts.SHOW_PERIOD);
		PMO_Threads.sleep(25000, main.continuationFlag);
		main.basket.shrink();
		PMO_Threads.sleep(35000, main.continuationFlag);
		main.basket.shrink();
		PMO_Threads.sleep(65000, main.continuationFlag);
		main.continuationFlag.set(false);
		PMO_Threads.sleep(500);
		if (!main.errorLog.isEmpty())
			System.out.println(main.errorLog);
		System.out.println("KONIEC wykonuje System.exit");
		System.exit(0);
	}
}
