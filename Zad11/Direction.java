
public enum Direction implements NextPositionInterface {
	RIGHT {
		public Position2D nextPosition2D(Position2D old)
		{
			return new Position2D(old.getX() + 1, old.getY());
		}
	},
	LEFT {
		public Position2D nextPosition2D(Position2D old)
		{
			return new Position2D(old.getX() - 1, old.getY());
		}
	},
	DOWN {
		public Position2D nextPosition2D(Position2D old)
		{
			return new Position2D(old.getX(), old.getY() - 1);
		}
	};

	/**
	 * Metoda zwraca nastepna pozycje w stosunku do pozycji old, gdy ruch
	 * wykonywany jest w okreslonym kierunku.
	 *
	 * @param old stara pozycja
	 * @return nowa pozycja
	 */
	abstract public Position2D nextPosition2D(Position2D old);
}
