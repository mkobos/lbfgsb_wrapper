package lbfgsb;

public interface MinimizationProblem {
	public int getVariablesNo();
	public DifferentiableFunction getFunction();
	public double[] getStartingPoint();
	public double[] getExpectedMinimum();
}
