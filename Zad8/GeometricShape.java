import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

class GeometricShape implements GeometricShapeInterface {
	private List<Point> listOfPoints;
	private int dimensions;

	public GeometricShape()
	{
		listOfPoints = new ArrayList<>();
		dimensions = -1;
	}

	private int countDimensions(Point point)
	{
		for (int i = 0;; ++i) {
			try {
				point.getPosition(i);
			} catch (ArrayIndexOutOfBoundsException e) {
				return i;
			}
		}
	}

	private void checkNumberOfDimensions(Point point)
		throws WrongNumberOfDimensionsException
	{
		int newDimensions = countDimensions(point);
		if (newDimensions != dimensions)
			throw new WrongNumberOfDimensionsException(
				dimensions, newDimensions);
	}

	public void add(Point point) throws WrongNumberOfDimensionsException
	{
		if (dimensions == -1) {
			dimensions = countDimensions(point);
			listOfPoints.add(point);
			return;
		}

		checkNumberOfDimensions(point);

		listOfPoints.add(point);
	}

	public void remove(Point point) throws WrongArgumentException
	{
		if (!listOfPoints.remove(point))
			throw new WrongArgumentException(point);
		// TODO
	}

	public void addBefore(Point point, Point beforePoint)
		throws WrongArgumentException, WrongNumberOfDimensionsException
	{
		checkNumberOfDimensions(point);
		checkNumberOfDimensions(beforePoint);

		int index = listOfPoints.indexOf(beforePoint);
		if (index == -1)
			throw new WrongArgumentException(beforePoint);

		listOfPoints.add(index, point);
	}

	public void addAfter(Point point, Point afterPoint)
		throws WrongNumberOfDimensionsException, WrongArgumentException
	{
		checkNumberOfDimensions(point);
		checkNumberOfDimensions(afterPoint);

		int index = listOfPoints.indexOf(afterPoint);
		if (index == -1)
			throw new WrongArgumentException(afterPoint);

		listOfPoints.add(index, point);
	}

	public Point removeBefore(Point beforePoint)
		throws NoSuchPointException, WrongNumberOfDimensionsException,
			   WrongArgumentException
	{
		// TODO
		return null;
	}

	public Point removeAfter(Point afterPoint)
		throws NoSuchPointException, WrongNumberOfDimensionsException,
			   WrongArgumentException
	{
		// TODO
		return null;
	}

	public List<Point> get()
	{
		return new ArrayList<>(listOfPoints);
	}

	public Set<Point> getSetOfPoints()
	{
		return new HashSet<>(listOfPoints);
	}

	public Optional<Point> getByPosition(List<Integer> positions)
		throws WrongNumberOfDimensionsException
	{
		if (positions.size() != dimensions)
			throw new WrongNumberOfDimensionsException(
				dimensions, positions.size());

	outer:
		for (int i = listOfPoints.size() - 1; i >= 0; --i) {
			Point p = listOfPoints.get(i);
			for (int j = 0; j < positions.size(); ++j) {
				if (p.getPosition(j) != positions.get(j))
					continue outer;
			}
			return Optional.of(p);
		}
		return Optional.empty();
	}
}

// vim: tabstop=4 shiftwidth=0 noexpandtab
