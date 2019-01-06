import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class PMO_FallingObjectsTestGenerator
	extends PMO_FallingObjectsProgrammableGenerator {
	public PMO_FallingObjectsTestGenerator(
		AtomicBoolean continuationFlag, AtomicLong tickTime, int height)
	{
		super(continuationFlag, tickTime, height);
	}

	protected void addLines()
	{
		addLine("...a......1..........", 2, 0);
		addLine(".8...1...........A...", width / 2 + 3, 1);
		addLine("...............1...8.", width + 3, 1);
		addLine(".....b...999...B.....", width / 2, 1);
		addLine(".0.................9.", width / 2 + 3, 2);
		addLine(".9.................0.", width + 3, 2);
		addLine("..........0..........", width / 2 + 3, 2);
		addLine(".7.................9.", width / 2 + 3, 3);
		addLine(".7..1..............9.", 1.1, 3);
		addLine(".7.............1.....", 1.1, 3);
		addLine(".7.....1..........1..", 1.1, 3);
		addLine(".........000.........", width / 2 + 3, 4);
		addLine(".....1.............6.", width / 2 + 3, 4);
		addLine(".9.........1.......6.", 1.1, 4);
		addLine(".9..a.........1..A...", 1.1, 4);
		addLine("......1...0.......2..", width / 2 + 3, 5);
		addLine("...................9.", width / 2 + 3, 5);
		addLine(".777..........1....9.", 1.1, 5);
		addLine(".797...1...........9.", 1.1, 5);
		addLine(".777..............1..", 1.1, 5);
		addLine("..1.......0...4......", width / 2 + 3, 6);
		addLine(".....3..0...3.....6..", 4, 6);
		addLine(".2....0........6.....", 4, 6);
		addLine("1...0....1......2....", 4, 6);
		addLine("..0........1.......1.", 4, 6);
		addLine(".0...c.......1...3...", 3, 6);
		addLine("..0.....1.......C....", 4, 6);
		addLine("....0.....1...4....1.", 4, 6);
		addLine("..1...0.....8........", 4, 6);
		addLine("........0........1...", 4, 6);
		addLine("....1.....0...1......", 4, 6);
		addLine(".a.A.a.A..0.a.A.a.A.a", 4, 7);
		addLine("999..................", width / 2 + 2, 8);
		addLine("..........0..........", 2, 8);
	}
}
