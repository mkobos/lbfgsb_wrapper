package lbfgsb;

public class RosenbrockProblem implements MinimizationProblem{

	@Override
	public int getVariablesNo() {
		return 2;
	}

	@Override
	public DifferentiableFunction getFunction() {
		return new RosenbrockFunction();
	}

	@Override
	public double[] getStartingPoint() {
		return new double[]{-1.2, 1};
	}
	
	@Override
	public double[] getExpectedMinimum() {
		return new double[]{1, 1};
	}
}

class RosenbrockFunction implements DifferentiableFunction {
	@Override
	public FunctionValues getValues(double[] pt) {
		double f0 = 10*(pt[1] - Math.pow(pt[0], 2));
		double f1 = 1 - pt[0];
		double f = f0*f0 + f1*f1;
		
		double dx0f0 = -4*f0*pt[0];
		double dx0f1 = -2*f1;
		double dx0f = dx0f0+dx0f1; 
		
		double dx1f0 = 2*f0;
		double dx1f = dx1f0;
		
		return new FunctionValues(f, new double[]{dx0f, dx1f});
	}
}
