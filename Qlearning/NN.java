import java.io.*;
import java.util.*;

class NN implements Serializable {
	// private final int neuron_counts[]
	// = new int[] {2 + 20 * 4, 2 + 20 * 4, 1 + 20, 2};
	private final double weights[][][];
	private final double biases[][];
	private static final double alpha = 1. / 128.; // ReLU parameter

	private static final double startingLearningRate = 0.4;

	private static final long serialVersionUID = 1L;

	/* neuron_counts: array of neuron counts, including input neurons at [0] */
	public NN(int neuron_counts[])
	{
		Random rand = new Random(System.currentTimeMillis());
		// layers
		final int layersCount = neuron_counts.length - 1;
		biases = new double[layersCount][];
		weights = new double[layersCount][][];
		// neurons
		for (int layer = 0; layer < layersCount; ++layer) {
			final int neuronsOnLayer = neuron_counts[layer + 1];
			final int prevLayerSize = neuron_counts[layer];
			biases[layer] = new double[neuronsOnLayer];
			weights[layer] = new double[neuronsOnLayer][];

			for (int neuron = 0; neuron < neuronsOnLayer; ++neuron) {
				weights[layer][neuron] = new double[prevLayerSize];
				// biases[layer][neuron] = rand.nextDouble();
				for (int i = 0; i < prevLayerSize; ++i)
					weights[layer][neuron][i] = -1 + 2 * rand.nextDouble();
			}
		}
	}

	////////////////////////////////////////
	// transfer funcion, ReLU here

	static private final double transfer(double x)
	{
		// return (1. / (1. + Math.exp(-1. * x)));
		if (x < 0)
			return x * alpha;
		return x;
	}
	static private final double transferDerivative(double x)
	{
		// return x * (1. - x);
		if (x < 0)
			return alpha;
		return 1;
	}

	////////////////////////////////////////

	public final double[] get(double _input[])
	{
		// if (_input.length != weights[0][0].length)
		// 	throw new IllegalArgumentException("Wrong input size");
		//
		// double input[] = _input.clone();
		// final int layersCount = weights.length;
		// double out[] = {0};
		// for (int layer = 0; layer < layersCount; ++layer) {
		// 	final int neuronsOnLayer = weights[layer].length;
		// 	out = biases[layer].clone();
		// 	/* out = f(input * weights[layer] + biases[layer]) */
		// 	for (int neuron = 0; neuron < neuronsOnLayer; ++neuron) {
		// 		for (int i = 0; i < input.length; ++i)
		// 			out[neuron] += input[i] * weights[layer][neuron][i];
		// 		out[neuron] = transfer(out[neuron]);
		// 	}
		// 	input = out;
		// }
		// return out;
		double out[][] = getAll(_input);
		return out[out.length - 1].clone();
	}

	private final double[][] getAll(double _input[])
	{
		if (_input.length != weights[0][0].length)
			throw new IllegalArgumentException("Wrong input size");

		double input[] = _input.clone();
		final int layersCount = weights.length;
		double out[][] = new double[layersCount][];
		for (int layer = 0; layer < layersCount; ++layer) {
			final int neuronsOnLayer = weights[layer].length;
			out[layer] = biases[layer].clone();
			/* out[layer] = f(input * weights[layer] + biases[layer]) */
			for (int neuron = 0; neuron < neuronsOnLayer; ++neuron) {
				for (int i = 0; i < input.length; ++i)
					out[layer][neuron] += input[i] * weights[layer][neuron][i];
				out[layer][neuron] = transfer(out[layer][neuron]);
			}
			input = out[layer];
		}
		return out;
	}

	public final void
	backpropagate(double _input[], double _desiredOutput[], double learningRate)
	{
		final int layersCount = weights.length;

		if (_desiredOutput.length != weights[layersCount - 1].length)
			throw new IllegalArgumentException("Wrong input size");

		double outputs[][] = getAll(_input);

		double deltas[][][] = new double[layersCount][][];
		double errors[][] = new double[layersCount][];

		{
			// output layer
			final int outputLayer = layersCount - 1;
			final int neuronsOnLayer = weights[outputLayer].length;
			deltas[outputLayer] = new double[neuronsOnLayer][];
			errors[outputLayer] = new double[neuronsOnLayer];

			for (int neuron = 0; neuron < neuronsOnLayer; ++neuron) {
				final int weightsCount = weights[outputLayer][neuron].length;
				final double output = outputs[outputLayer][neuron];
				double error = (_desiredOutput[neuron] - output);
				error *= transferDerivative(output);
				errors[outputLayer][neuron] = error;

				deltas[outputLayer][neuron] = new double[weightsCount];
				for (int i = 0; i < weightsCount; ++i) {
					deltas[outputLayer][neuron][i]
						= error * outputs[outputLayer - 1][i];
				}
			}
		}
		for (int layer = layersCount - 2; layer >= 0; --layer) {
			final int neuronsOnLayer = weights[layer].length;
			deltas[layer] = new double[neuronsOnLayer][];
			errors[layer] = new double[neuronsOnLayer];
			for (int neuron = 0; neuron < neuronsOnLayer; ++neuron) {
				final int weightsCount = weights[layer][neuron].length;
				final double output = outputs[layer][neuron];

				double error = 0.;
				for (int nextNeuron = 0; nextNeuron < weights[layer + 1].length;
					 ++nextNeuron) {
					error += errors[layer + 1][nextNeuron]
						* weights[layer + 1][nextNeuron][neuron];
				}
				error *= transferDerivative(output);

				errors[layer][neuron] = error;
				deltas[layer][neuron] = new double[weightsCount];
				if (layer > 0) {
					for (int i = 0; i < weightsCount; ++i) {
						deltas[layer][neuron][i]
							= error * outputs[layer - 1][i];
					}
				} else {
					for (int i = 0; i < weightsCount; ++i) {
						deltas[layer][neuron][i] = error * _input[i];
					}
				}
			}
		}

		for (int layer = layersCount - 2; layer >= 0; --layer) {
			final int neuronsOnLayer = weights[layer].length;
			for (int neuron = 0; neuron < neuronsOnLayer; ++neuron) {
				biases[layer][neuron] += learningRate * errors[layer][neuron];
				for (int i = 0; i < weights[layer][neuron].length; ++i) {
					weights[layer][neuron][i]
						+= learningRate * deltas[layer][neuron][i];
				}
			}
		}
	}

	////////////////////////////////////////

	public final void print()
	{
		for (int layer = 0; layer < weights.length; ++layer) {
			System.out.println("Layer " + layer);
			for (int neuron = 0; neuron < weights[layer].length; ++neuron) {
				System.out.print("\tNeuron " + neuron + ": ");
				System.out.print("B:" + biases[layer][neuron] + " ");
				for (int i = 0; i < weights[layer][neuron].length; ++i) {
					System.out.print(weights[layer][neuron][i] + " ");
				}
				System.out.println();
			}
		}
	}

	public final void saveToFile(String filename)
	{
		try (final FileOutputStream fout = new FileOutputStream(filename);
			 final ObjectOutputStream oos = new ObjectOutputStream(fout)) {
			oos.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File " + filename + " not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Something went terribly wrong");
		}
	}

	static public final NN loadFromFile(String filename)
	{
		try (final FileInputStream fin = new FileInputStream(filename);
			 final ObjectInputStream ois = new ObjectInputStream(fin)) {
			try {
				return (NN)ois.readObject();
			} catch (ClassNotFoundException e) {
				System.err.println(
					"File " + filename + " does not contain our class");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File " + filename + " not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Something went terribly wrong");
		}
		return null;
	}

	////////////////////////////////////////

	@Override
	protected NN clone()
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais
				= new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (NN)ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}

	////////////////////////////////////////

	public static void main(String[] args)
	{
		NN test = new NN(new int[] {2, 3, 1});
		final double data[][] = {{0., 0.}, {0., 1.}, {1., 0.}, {1., 1.}};
		final double result[][] = {{0.}, {1.}, {1.}, {0.}};
		double learningRate = startingLearningRate;

		System.out.println("Przed:");
		for (int j = 0; j < 4; ++j) {
			System.out.printf("%.0f %.0f: % .5f\n",
				data[j][0],
				data[j][1],
				test.get(data[j])[0]);
		}

		for (int i = 10_000_000; i >= 0; --i) {
			for (int j = 0; j < 4; ++j) {
				test.backpropagate(data[j], result[j], learningRate);
			}
			learningRate *= 1. - 1. / 1024.;
		}

		System.out.println("Po:");
		for (int j = 0; j < 4; ++j) {
			System.out.printf("%.0f %.0f: % .5f\n",
				data[j][0],
				data[j][1],
				test.get(data[j])[0]);
		}
	}
}
