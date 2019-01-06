import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

abstract public class PMO_FallingObjectsProgrammableGenerator
	implements PMO_GeneratorInterface {
	private final List<ProgramEntry> program = new ArrayList<>();
	private final Map<Character, PMO_FallingObject> repository
		= new HashMap<>();
	private final Map<Character, List<Character>> alternatives
		= new HashMap<>();
	private final Map<Character, Character> dictionary = new TreeMap<>();
	private final char NONE = '.';
	private PMO_Observable observable;

	private int index;
	private int groupID;
	private final AtomicBoolean continuationFlag;
	private final AtomicLong tickTime;
	protected int width;
	protected final int HEIGHT_1;
	protected final int HEIGHT;

	private class ProgramEntry {
		String line;
		double delay;
		int groupID;
		Map<Boolean, List<PMO_FallingObject>> objects2add;

		ProgramEntry(String line, double delay, int groupID)
		{
			this.line = line;
			this.delay = delay;
			this.groupID = groupID;
			objects2add = new HashMap<>();
		}

		@Override
		public String toString()
		{
			return "ProgramEntry{"
				+ "line='" + line + '\'' + ", delay=" + delay
				+ ", groupID=" + groupID + ", objects2add=" + objects2add + '}';
		}
	}

	public PMO_FallingObjectsProgrammableGenerator(
		AtomicBoolean continuationFlag, AtomicLong tickTime, int height)
	{
		this.continuationFlag = continuationFlag;
		this.tickTime = tickTime;
		this.HEIGHT = height;
		this.HEIGHT_1 = height - 1;
		prepareRepository();
		prepareAlternatives();
		prepareDictionary();
		program();
	}

	public int getWidth()
	{
		return width;
	}

	private void prepareRepository()
	{
		repository.put('1', new PMO_FallingObject(false, 10));
		repository.put('2', new PMO_FallingObject(false, 20));
		repository.put('3', new PMO_FallingObject(false, 30));
		repository.put('4', new PMO_FallingObject(false, 40));
		repository.put('5', new PMO_FallingObject(false, 50));
		repository.put('6', new PMO_FallingObject(false, 60));
		repository.put('7', new PMO_FallingObject(false, 70));
		repository.put('8', new PMO_FallingObject(false, 80));
		repository.put('9', new PMO_FallingObject(false, 90));
		repository.put('0', new PMO_FallingObject(true, 0));
	}

	private List<Character> list(Character c1)
	{
		List<Character> result = new ArrayList<>();
		c1 = Character.toUpperCase(c1);
		result.add(c1);
		result.add((char)(c1 + 32));
		return result;
	}

	private void prepareAlternatives()
	{
		alternatives.put('1', list('a'));
		alternatives.put('2', list('b'));
		alternatives.put('3', list('c'));
		alternatives.put('4', list('d'));
		alternatives.put('5', list('e'));
		alternatives.put('6', list('f'));
		alternatives.put('7', list('g'));
		alternatives.put('8', list('h'));
		alternatives.put('9', list('i'));
		alternatives.put('0', list('o'));
	}

	private void shuffle()
	{
		alternatives.keySet().stream().forEach(
			k -> Collections.shuffle(alternatives.get(k)));
	}

	private void prepareDictionary()
	{
		shuffle();
		alternatives.entrySet().forEach(e -> {
			dictionary.put(e.getValue().get(0), e.getKey());
			dictionary.put(e.getValue().get(1), NONE);
		});
		dictionary.put(NONE, NONE);
		repository.keySet().forEach(c -> dictionary.put(c, c));
	}

	protected void addLine(String line, double delay, int groupID)
	{
		if (width == 0) {
			width = line.length();
		}
		if (line.length() != width) {
			System.err.println(
				"Wewnętrzny błąd gry: zła długość linii programu");
			System.exit(0);
		}
		StringBuffer sb = new StringBuffer();
		line.chars()
			.mapToObj(c -> (char)c)
			.forEach(c -> sb.append(dictionary.get(c)));
		System.out.println(line + " -> " + sb.toString());
		program.add(new ProgramEntry(sb.toString(), delay, groupID));
	}

	abstract protected void addLines();

	private void program()
	{
		addLines();
		program.forEach(entry -> {
			process(entry, false);
			process(entry, true);
		});
	}

	private void process(ProgramEntry entry, boolean shift)
	{
		String oneLine = entry.line;
		List<PMO_FallingObject> result = new ArrayList<>();

		for (int i = 0; i < width; i++) {
			if (oneLine.charAt(i) != NONE) {
				PMO_FallingObject object
					= repository.get(oneLine.charAt(i)).clone();
				if (shift) {
					object.setInitialLocation(
						new Position2D(width - i - 1, HEIGHT_1));
				} else {
					object.setInitialLocation(new Position2D(i, HEIGHT_1));
				}
				result.add(object);
			}
		}
		entry.objects2add.put(shift, result);
	}

	@Override
	public void run()
	{
		Random rnd = ThreadLocalRandom.current();
		boolean shift = rnd.nextBoolean();
		while (continuationFlag.get()) {
			ProgramEntry entry = program.get(index % program.size());
			System.out.println(PMO_Threads.getThreadName() + "|run>" + entry);
			index++;
			PMO_Threads.sleep((long)(entry.delay * tickTime.get()));
			if (groupID != entry.groupID) {
				shift = rnd.nextBoolean();
				groupID = entry.groupID;
			}
			entry.objects2add.get(shift).forEach(
				o -> observable.sendUpdates(o.clone()));
		}
	}

	@Override
	public void setObservable(PMO_Observable observable)
	{
		this.observable = observable;
	}
}
