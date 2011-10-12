package lbfgsb;

/**
 * Function value and gradient at a certain point
 * @author Mateusz Kobos
 */
public class FunctionValues {
	public double functionValue;
	public double[] gradient;
	
	public FunctionValues(double functionValue, double[] gradient) {
		this.functionValue = functionValue;
		this.gradient = gradient;
	}
}
