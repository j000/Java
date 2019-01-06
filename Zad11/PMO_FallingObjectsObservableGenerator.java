import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class PMO_FallingObjectsObservableGenerator implements PMO_Observable {
	private final List<Consumer<FallingObjectBasicInterface>> listeners
		= Collections.synchronizedList(new ArrayList<>());
	private final PMO_GeneratorInterface generator;

	public PMO_FallingObjectsObservableGenerator(
		PMO_GeneratorInterface generator)
	{
		generator.setObservable(this);
		this.generator = generator;
	}

	public void start()
	{
		PMO_Threads.createThreadAndStartAsDaemon(generator);
	}

	public void addNewObjectListener(
		Consumer<FallingObjectBasicInterface> listener)
	{
		listeners.add(listener);
	}

	private Thread generateThread(
		Consumer<FallingObjectBasicInterface> consumer,
		FallingObjectBasicInterface newObject)
	{
		return new Thread(() -> consumer.accept(newObject));
	}

	/*
	Każdy obserwator jest informowany niezależnym wątkiem
	 */
	public void sendUpdates(FallingObjectBasicInterface newObject)
	{
		listeners.stream()
			.map(l -> generateThread(l, newObject))
			.forEach(t -> t.start());
	}
}
