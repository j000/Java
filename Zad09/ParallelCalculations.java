import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.List;
import java.util.ArrayList;

public class ParallelCalculations implements ParallelCalculationsInterface {
	private static class HistogramCalculator {
		private static int MAX = PointInterface.MAX_POSITION + 1;

		private AtomicIntegerArray _histogram
			= new AtomicIntegerArray(MAX * MAX);

		private static int calculateIndex(int x, int y)
		{
			return x * MAX + y;
		}

		public void inc(int x, int y)
		{
			_histogram.incrementAndGet(calculateIndex(x, y));
		}

		public int[][] get()
		{
			int[][] out = new int[MAX][MAX];
			for (int i = 0; i < MAX; ++i)
				for (int j = 0; j < MAX; ++j) {
					out[i][j] = _histogram.get(calculateIndex(i, j));
				}
			return out;
		}
	}

	private class NumberCruncher3000 {
		private int[] _wektor;
		private int _liczbaPunktow;
		private HistogramCalculator _hc;

		public NumberCruncher3000(HistogramCalculator hc)
		{
			_hc = hc;
			reset();
		}

		public void add(PointInterface p)
		{
			++_liczbaPunktow;
			int[] polozenie = p.getPositions();
			_hc.inc(polozenie[0], polozenie[1]);
			_wektor[0] += polozenie[0];
			_wektor[1] += polozenie[1];
		}

		public void reset()
		{
			_liczbaPunktow = 0;
			_wektor = new int[2];
		}

		public void add(NumberCruncher3000 another)
		{
			_liczbaPunktow += another._liczbaPunktow;
			_wektor[0] += another._wektor[0];
			_wektor[1] += another._wektor[1];
		}

		public int[][] getHistogram()
		{
			return _hc.get();
		}

		public double[] getGeometricCenter()
		{
			double[] out = new double[2];
			out[0] = (double)_wektor[0] / _liczbaPunktow;
			out[1] = (double)_wektor[1] / _liczbaPunktow;
			return out;
		}
	}

	private class Helper extends Thread {
		private final AtomicBoolean _keepGoing = new AtomicBoolean(true);
		private final NumberCruncher3000 _nc;
		private final PointGeneratorInterface _generator;

		public Helper(HistogramCalculator hc, PointGeneratorInterface generator)
		{
			_nc = new NumberCruncher3000(hc);
			_generator = generator;
		}

		@Override
		public void run()
		{
			PointInterface p;
			while (_keepGoing.get()) {
				p = _generator.getPoint();

				_nc.add(p);
			}
		}

		public void die()
		{
			_keepGoing.set(false);
		}

		public NumberCruncher3000 getData()
		{
			return _nc;
		}
	}

	private int _threads = 0;
	private PointGeneratorInterface _generator = null;

	private List<Helper> _listOfThreads = new ArrayList<>();
	private HistogramCalculator _hc = new HistogramCalculator();
	private NumberCruncher3000 _nc = new NumberCruncher3000(_hc);

	public void setPointGenerator(PointGeneratorInterface generator)
	{
		_generator = generator;
	}

	public void setNumberOfThreads(int threads)
	{
		if (threads <= 0) {
			throw new IllegalArgumentException(
				"Ilość wątków musi być liczbą dodatnią");
		}
		_threads = threads;
	}

	public void start()
	{
		if (_generator == null) {
			throw new IllegalArgumentException(
				"Metoda setPointGenerator() nie została wywołana");
		}
		if (_threads <= 0) {
			throw new IllegalArgumentException(
				"Metoda setNumberOfThreads() nie została wywołana");
		}
		continueCalculations();
	}

	public void suspendCalculations()
	{
		for (Helper t : _listOfThreads) {
			t.die();
		}
		for (Helper t : _listOfThreads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Thread interrupted. Aborting.");
			}
			_nc.add(t.getData());
		}
		_listOfThreads.clear();
	}

	public void continueCalculations()
	{
		for (int i = 0; i < _threads; ++i) {
			Helper tmp = new Helper(_hc, _generator);
			tmp.setName("Helper " + i);
			tmp.setDaemon(true);
			tmp.start();
			_listOfThreads.add(tmp);
		}
	}

	public double[] getGeometricCenter()
	{
		return _nc.getGeometricCenter();
	}

	public int[][] getHistogram()
	{
		return _nc.getHistogram();
	}
}
