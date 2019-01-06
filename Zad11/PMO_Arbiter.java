import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class PMO_Arbiter {
	private final PMO_Basket basket;
	private final List<String> errorLog;
	private final AtomicBoolean continuationFlag;
	private final AtomicLong score;

	public PMO_Arbiter(PMO_Basket basket,
		List<String> errorLog,
		AtomicBoolean continuationFlag,
		AtomicLong score)
	{
		this.basket = basket;
		this.continuationFlag = continuationFlag;
		this.errorLog = errorLog;
		this.score = score;
	}

	public boolean collision(PMO_FallingObject fallingObject)
	{
		if (basket.contains(fallingObject.getActualPosition())
			|| basket.contains(fallingObject.nextPosition2D())) {
			score.addAndGet(fallingObject.getValue());
			System.out.println("Złapano obiekt " + fallingObject.getUniqID());
			return true;
		}

		if (fallingObject.getActualPosition().getY() == 0) {
			if (fallingObject.mandatoryInterception()) {
				errorLog.add("BŁĄD: obiekt " + fallingObject
					+ " nie został przechwycony.");
				System.out.println(
					"BŁĄD: Nie złapano obiektu " + fallingObject.getUniqID());
				continuationFlag.set(false);
			}
		}

		return false;
	}
}
