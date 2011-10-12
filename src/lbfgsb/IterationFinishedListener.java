package lbfgsb;

/**
 * @author Mateusz Kobos
 */
public interface IterationFinishedListener {
	/** @return true if the algorithm should continue, false otherwise.*/
	boolean iterationFinished(double[] point, 
			double functionValue, double[] gradient);
}
