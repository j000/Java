import java.util.Stack;

class BetterPoint extends AbstractBetterPoint {
	private Stack<String> passwords;
	private double dimensions[];

	public BetterPoint()
	{
		passwords = new Stack<String>();
	}

	@Override
	public void setDimensions(int _dimensions)
	{
		dimensions = new double[_dimensions];
	}

	@Override
	public int lockLevel()
	{
		return passwords.size();
	}

	@Override
	public int lock(String password)
	{
		if (password != null)
			passwords.push(password);
		return lockLevel();
	}

	@Override
	public int unlock(String password)
	{
		if (passwords.size() > 0 && password == passwords.peek())
			passwords.pop();

		return lockLevel();
	}

	@Override
	public boolean move(int dimension, double delta)
	{
		if (lockLevel() > 0 || delta == 0)
			return false;

		dimensions[dimension] += delta;

		return true;
	}

	@Override
	public boolean set(int dimension, double value)
	{
		if (lockLevel() > 0)
			return false;

		dimensions[dimension] = value;

		return true;
	}

	@Override
	public double get(int dimension)
	{
		return dimensions[dimension];
	}
}
