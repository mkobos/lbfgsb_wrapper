package lbfgsb;

/**
 * @author Mateusz Kobos
 */
public interface IterationFinishedListener {
	/** 
	 * @param point point coordinates
	 * @param functionValue value of the function at the point
	 * @param gradient gradient at the point
	 * @return true if the algorithm should continue, false otherwise
	 */
	boolean iterationFinished(double[] point, 
			double functionValue, double[] gradient);
}
