import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class PMO_TxtGraphics {
	private final PMO_Basket basket;
	private final AtomicLong score;
	private final long startTime;
	private final String[][] picture;

	public PMO_TxtGraphics(
		int width, int height, PMO_Basket basket, AtomicLong score)
	{
		picture = new String[width][height];
		for (int col = 0; col < width; col++)
			for (int row = 1; row < height; row++)
				picture[col][row] = PMO_Consts.BACKGROUND;
		for (int col = 0; col < width; col++) {
			picture[col][0] = PMO_Consts.LAST_LINE;
		}
		this.basket = basket;
		this.score = score;
		startTime = System.currentTimeMillis();
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\033[H\033[2J");
		sb.append("Score: ");
		sb.append(score.get());
		sb.append("\nGame time: "
			+ (System.currentTimeMillis() - startTime) / 1000 + "s");
		sb.append("\n");
		synchronized (picture)
		{
			for (int row = picture[0].length - 1; row > 0; row--) {
				for (int col = 0; col < picture.length; col++) {
					sb.append(picture[col][row]);
				}
				sb.append("\n");
			}
			for (int col = 0; col < picture.length; col++) {
				if (basket.contains(new Position2D(col, 0))) {
					sb.append(PMO_Consts.BASKET);
				} else {
					sb.append(picture[col][0]);
				}
			}
		}
		return sb.toString();
	}

	private class UpdateView implements Consumer<Position2D> {
		private final long id;
		private String view;
		private String background;
		private Position2D previousPosition;

		private UpdateView(long id, String view, Position2D position2D)
		{
			this.id = id;
			this.view = view;
			previousPosition = position2D;
			background = get(position2D);
		}

		private String get(Position2D position)
		{
			synchronized (picture)
			{
				return picture[position.getX()][position.getY()];
			}
		}

		private void set(Position2D position, String value)
		{
			synchronized (picture)
			{
				picture[position.getX()][position.getY()] = value;
			}
		}

		@Override
		public void accept(Position2D position2D)
		{
			background = ".";

			if (position2D.getY() == -1) {
				set(previousPosition, PMO_Consts.LAST_LINE);
				return;
			}

			String tmp = get(position2D);
			set(previousPosition, background);
			set(position2D, view);
			background = tmp;
			previousPosition = position2D;
		}
	}

	public class NewObjectListener
		implements Consumer<FallingObjectBasicInterface> {
		private String objectView(FallingObjectBasicInterface object)
		{
			if (object.mandatoryInterception())
				return PMO_Consts.FALLING_OBJECT;
			int value = (int)(object.getValue() / 10);
			return Character.toString((char)('0' + value));
		}

		@Override
		public void accept(
			FallingObjectBasicInterface fallingObjectBasicInterface)
		{
			fallingObjectBasicInterface.addLocationListener(
				new UpdateView(fallingObjectBasicInterface.getUniqID(),
					objectView(fallingObjectBasicInterface),
					fallingObjectBasicInterface.getInitialLocation()));
		}
	}
}
