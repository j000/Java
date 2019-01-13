import java.util.*;
import java.util.function.*;
import java.util.concurrent.atomic.AtomicInteger;

public enum PathFinderEnum implements PathFinderInterface {
	LEFT_HAND_TRAFFIC {
		@Override
		protected double getPenaltyImpl(final Turn turn)
		{
			if (turn == Turn.LEFT)
				return MEDIUM_PENALTY;
			return HIGH_PENALTY;
		}
	},
	RIGHT_HAND_TRAFFIC {
		@Override
		protected double getPenaltyImpl(final Turn turn)
		{
			if (turn == Turn.RIGHT)
				return MEDIUM_PENALTY;
			return HIGH_PENALTY;
		}
	};

	private enum Turn {
		STRAIGHT,
		LEFT,
		RIGHT;
	}

	private static class StablePriorityQueue<E> {
		private class Pack<E> {
			public final E elem;
			public final int number;
			Pack(E e, int i)
			{
				elem = e;
				number = i;
			}
		}
		private final static AtomicInteger seq = new AtomicInteger(0);
		private final PriorityQueue<Pack<E>> queue;
		StablePriorityQueue(Comparator<? super E> comparator)
		{
			queue = new PriorityQueue<Pack<E>>((Pack<E> lhs, Pack<E> rhs) -> {
				final int r = comparator.compare(lhs.elem, rhs.elem);
				if (r == 0)
					return Integer.compare(lhs.number, rhs.number);
				return r;
			});
		}
		public boolean offer(E e)
		{
			return queue.offer(new Pack<E>(e, seq.incrementAndGet()));
		}
		public int size()
		{
			return queue.size();
		}
		public E poll()
		{
			Pack<E> out = queue.poll();
			if (out == null)
				return null;
			return out.elem;
		}
	}

	private class Graph {
		private class Vertex {
			public final Position pos;
			public final Collection<Edge> edges;
			public final Direction dir;
			public Vertex(final Position _pos, final Direction _dir)
			{
				pos = _pos;
				dir = _dir;
				edges = new ArrayList<Edge>();
			}
			@Override
			public String toString()
			{
				return pos.toString() + " "
					+ (dir == null ? "" : dir.toString());
			}
		}
		private class Edge {
			public final Vertex to;
			public final double weight;
			public final Turn turn;
			public Edge(final Vertex _to,
				final double _weight,
				final Turn _turn)
			{
				to = _to;
				weight = _weight;
				turn = _turn;
			}
		}

		private Map<Vertex, Collection<Edge>> graph
			= new HashMap<Vertex, Collection<Edge>>();
		private Map<Position, Map<Direction, Vertex>> verticesList
			= new HashMap<Position, Map<Direction, Vertex>>();

		public void createVertices(final Position _pos)
		{
			Map<Direction, Vertex> list = verticesList.get(_pos);
			if (list == null) {
				list = new EnumMap<Direction, Vertex>(Direction.class);
				verticesList.put(_pos, list);
			}
			for (final Direction direction : Direction.values()) {
				list.put(direction, new Vertex(_pos, direction));
			}
		}

		public void createConnections(final Position _from,
			final Position _to,
			final Direction _dir)
		{
			final Map<Direction, Vertex> vertices = verticesList.get(_from);
			final Direction incoming = _dir.right().right();
			final Vertex destination = verticesList.get(_to).get(incoming);
			vertices.get(incoming).edges.add(
				new Edge(destination, _from.weight, Turn.STRAIGHT));
			vertices.get(incoming.right())
				.edges.add(new Edge(destination, _from.weight, Turn.LEFT));
			vertices.get(incoming.left())
				.edges.add(new Edge(destination, _from.weight, Turn.RIGHT));
		}

		public Map<Direction, Vertex> getVertices(final Position _pos)
		{
			return verticesList.get(_pos);
		}

		public PositionInterface[] findPath(final Position _start,
			final Position _finish,
			final Function<Edge, Double> weight)
		{
			if (_start == null || _finish == null)
				return null;
			if (_start.equals(_finish))
				return new PositionInterface[] {_start};

			Vertex startingVertex = new Vertex(_start, null);
			Map<Direction, Vertex> startingVertices = verticesList.get(_start);
			for (Vertex v : startingVertices.values()) {
				for (Edge e : v.edges) {
					if (e.turn != Turn.STRAIGHT)
						continue;
					startingVertex.edges.add(e);
				}
			}

			final Map<Vertex, Double> dist = new HashMap<Vertex, Double>();
			final Map<Vertex, Vertex> prev = new HashMap<Vertex, Vertex>();
			final Map<Vertex, Vertex> next = new HashMap<Vertex, Vertex>();
			final StablePriorityQueue<Vertex> queue
				= new StablePriorityQueue<Vertex>(
					(Vertex lhs, Vertex rhs)
						-> Double.compare(
							dist.getOrDefault(lhs, Double.MAX_VALUE),
							dist.getOrDefault(rhs, Double.MAX_VALUE)));

			dist.put(startingVertex, 0.);
			prev.put(startingVertex, null);
			queue.offer(startingVertex);

			Vertex finishingVertex = new Vertex(null, null);

			while (queue.size() > 0) {
				final Vertex current = queue.poll();
				// System.out.println("@" + current.pos);

				if (current.pos.equals(_finish)) {
					if (dist.get(current)
						< dist.getOrDefault(finishingVertex, Double.MAX_VALUE))
						finishingVertex = current;
					continue;
				}

				for (Edge edge : current.edges) {
					final Vertex neighbour = edge.to;
					if (edge.to.pos.isWall())
						throw new RuntimeException("WALL!");

					// System.out.println("   " + edge.to.pos);

					final double alt = dist.get(current) + weight.apply(edge)
						+ ((current.edges.size() > 1) ? getPenalty(edge.turn)
													  : 0.);
					final double neighbourDist
						= dist.getOrDefault(neighbour, Double.MAX_VALUE);
					if (alt < neighbourDist) {
						dist.put(neighbour, alt);
						prev.put(neighbour, current);
						next.put(current, neighbour);
						queue.offer(neighbour);
					}
				}
			}
			Deque<Position> list = new LinkedList<Position>();
			for (Vertex i = finishingVertex; i != null; i = prev.get(i)) {
				list.addFirst(i.pos);
			}

			return list.toArray(new PositionInterface[0]);
			// return new PositionInterface[] {_start, _finish};
		}
	}

	private enum Direction {
		NORTH {
			@Override
			public int column()
			{
				return 0;
			}
			@Override
			public int row()
			{
				return 1;
			}
			@Override
			public Direction right()
			{
				return EAST;
			}
			@Override
			public Direction left()
			{
				return WEST;
			}
		},
		EAST {
			@Override
			public int column()
			{
				return 1;
			}
			@Override
			public int row()
			{
				return 0;
			}
			@Override
			public Direction right()
			{
				return SOUTH;
			}
			@Override
			public Direction left()
			{
				return NORTH;
			}
		},
		SOUTH {
			@Override
			public int column()
			{
				return 0;
			}
			@Override
			public int row()
			{
				return -1;
			}
			@Override
			public Direction right()
			{
				return WEST;
			}
			@Override
			public Direction left()
			{
				return EAST;
			}
		},
		WEST {
			@Override
			public int column()
			{
				return -1;
			}
			@Override
			public int row()
			{
				return 0;
			}
			@Override
			public Direction right()
			{
				return NORTH;
			}
			@Override
			public Direction left()
			{
				return SOUTH;
			}
		};
		public abstract int column();
		public abstract int row();
		public abstract Direction right();
		public abstract Direction left();
	}

	private class Position implements PositionInterface {
		final int col;
		final int row;
		public final int weight;
		public Position(int _col, int _row, int _weight)
		{
			col = _col;
			row = _row;
			weight = _weight;
		}
		public Position(int _col, int _row)
		{
			this(_col, _row, 0);
		}
		public Position(PositionInterface _pi)
		{
			this(_pi.getCol(), _pi.getRow(), 0);
		}
		@Override
		public int getCol()
		{
			return col;
		}
		@Override
		public int getRow()
		{
			return row;
		}
		public boolean isWall()
		{
			return weight <= 0;
		}
		@Override
		public boolean equals(Object o)
		{
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Position p = (Position)o;

			if (col == p.col && row == p.row /*&& weight == p.weight*/)
				return true;
			return false;
		}
		@Override
		public int hashCode()
		{
			// return Objects.hash(col, row, weight);
			return Objects.hash(col, row);
		}
		@Override
		public String toString()
		{
			return "(" + getCol() + "," + getRow() + ")";
		}
	}

	private Graph graph;
	private Position[][] map;
	private int width;
	private int height;

	private final static double HIGH_PENALTY = 1. / 64.;
	private final static double MEDIUM_PENALTY = HIGH_PENALTY / 8.;
	private final static double LOW_PENALTY = MEDIUM_PENALTY / 8.;

	protected abstract double getPenaltyImpl(final Turn turn);
	public double getPenalty(final Turn turn)
	{
		if (turn == Turn.STRAIGHT)
			return LOW_PENALTY;
		return getPenaltyImpl(turn);
	}

	/**
	 * Metoda ustawia mapę miasta.
	 *
	 * @param map dwuwymiarowa tablica reprezentująca mapę dróg w mieście
	 */
	public void setMap(int[][] _map)
	{
		width = _map.length;
		height = _map[0].length;
		map = new Position[width][height];
		graph = new Graph();
		for (int column = 0; column < width; ++column) {
			for (int row = 0; row < height; ++row) {
				final Position current
					= new Position(column, row, _map[column][row]);
				map[column][row] = current;
				if (current.isWall())
					continue;
				graph.createVertices(current);
				Position neighbour;
				if (column > 0
					&& !(neighbour = map[column - 1][row]).isWall()) {
					graph.createConnections(current, neighbour, Direction.WEST);
					graph.createConnections(neighbour, current, Direction.EAST);
				}
				if (row > 0 && !(neighbour = map[column][row - 1]).isWall()) {
					graph.createConnections(
						current, neighbour, Direction.SOUTH);
					graph.createConnections(
						neighbour, current, Direction.NORTH);
				}
			}
		}
	}

	/**
	 * Metoda zwraca tablicę położeń na mapie, które reprezentują najkrótszą
	 * trasę pomiędzy położeniem begin a położeniem end. Trasa najkrótsza to
	 * trasa przechodząca przez możliwie najmniej położeń pośrednich. W
	 * przypadku, gdy istnieją trasy o identycznej długości o wyborze decyduje
	 * preferowany kierunek ruchu na skrzyżowaniach. Trasa jako pozycje skrajne
	 * zawiera położenia begin i end.
	 *
	 * @param begin położenie startowe
	 * @param end   położenie końcowe
	 *
	 * @return najkrótsza trasa od begin do end
	 */
	public PositionInterface[] getShortestRoute(PositionInterface begin,
		PositionInterface end)
	{
		return graph.findPath(
			new Position(begin), new Position(end), (Graph.Edge e) -> 1.);
		// return new PositionInterface[] {begin, end};
	}

	/**
	 * Metoda zwraca tablicę położeń na mapie, które reprezentują najłatwiejszą
	 * trasę pomiędzy położeniem begin a położeniem end. O wyborze trasy
	 * decyduje preferowany kierunek ruchu na skrzyżowaniach. Trasa jako pozycje
	 * skrajne zawiera położenia begin i end.
	 *
	 * @param begin położenie startowe
	 * @param end   położenie końcowe
	 * @return najłatwiejsza trasa od begin do end
	 */
	public PositionInterface[] getEasiestRoute(PositionInterface begin,
		PositionInterface end)
	{
		return graph.findPath(
			new Position(begin), new Position(end), (Graph.Edge e) -> 0.);
		// return new PositionInterface[] {begin, end};
	}

	/**
	 * Metoda zwraca tablicę położeń tablicy, które reprezentują najszybszą
	 * trasę pomiędzy położeniem begin a położeniem end. Trasa najszybsza to
	 * trasa przechodząca przez położenia, których suma wartości w mapie jest
	 * najmniejsza. W przypadku, gdy istnieją trasy o identycznym czasie
	 * przejazdu o wyborze decyduje preferowany kierunek ruchu na
	 * skrzyżowaniach. Trasa jako pozycje skrajne zawiera położenia begin i end.
	 *
	 * @param begin położenie startowe
	 * @param end   położenie końcowe
	 * @return najszybsza trasa od begin do end
	 */
	public PositionInterface[] getFastestRoute(PositionInterface begin,
		PositionInterface end)
	{
		return graph.findPath(
			new Position(begin), new Position(end), (Graph.Edge e) -> e.weight);
		// return new PositionInterface[] {begin, end};
	}

	public static void main(String[] argv)
	{
		// PathFinderEnum test = LEFT_HAND_TRAFFIC;
		PathFinderEnum test = RIGHT_HAND_TRAFFIC;
		test.run();
	}

	public void test_left()
	{
		PositionInterface[] ans = run();
		System.out.println("ans.length: " + ans.length);
		if (ans.length != 7) {
			throw new RuntimeException("Wrong asnwer");
		}
		if (ans[3].getCol() != 1 || ans[3].getRow() != 4)
			throw new RuntimeException("Wrong path");
	}

	public void test_right()
	{
		PositionInterface[] ans = run();
		System.out.println("ans.length: " + ans.length);
		if (ans.length != 7) {
			throw new RuntimeException("Wrong asnwer");
		}
		if (ans[3].getCol() != 3 || ans[3].getRow() != 2)
			throw new RuntimeException("Wrong path");
	}

	public PositionInterface[] run()
	{
		int map[][] = {{0, 0, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 0, 0},
			{0, 0, 1, 0, 1, 0, 0},
			{0, 0, 1, 1, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 0}};
		setMap(map);

		return getEasiestRoute(new Position(1, 1), new Position(3, 5));
	}
}
// vim: tabstop=4 shiftwidth=0 noexpandtab
