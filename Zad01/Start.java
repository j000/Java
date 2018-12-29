// Jarosław Rymut
// Zadanie 01
class Start {
	public static final int N = 4;
	public static final double X_FIRST = 2.5;
	public static final double X_LAST = -0.2;
	public static final int STEPS = 5;

	public static void main(String[] args)
	{
		double step = (X_LAST - X_FIRST) / (STEPS + 1);
		for (double x = X_FIRST; (step > 0 && x < (X_LAST + 0.25 * step))
			 || (step < 0 && x > (X_LAST + 0.25 * step));
			 x += step) {
			double wartoscSinCos = Math.sin(x) * Math.cos(x);
			double przyblizenie = szereg(x);
			double roznica = wartoscSinCos - przyblizenie;
			System.out.printf(
				"x=%7.4f sin(x)cos(x)=%8.6f aprox=%8.6f delta=%10.8f\n", x,
				wartoscSinCos, przyblizenie, roznica);
		}
	}

	static double szereg(double x)
	{
		// (-1)^n * 2^(2n) * x^(2n+1) / (2n+1)!
		double plusMinus = 1;
		double a2 = 1;
		double xDoPotegi = x;
		double silnia = 1;

		double out = x;

		for (int n = 1; n < N; ++n) {
			plusMinus = 0 - plusMinus;
			a2 *= 4;
			xDoPotegi *= x * x;
			silnia *= 2 * n * (2 * n + 1);
			out += plusMinus * a2 * xDoPotegi / silnia;
		}

		return out;
	}
}
