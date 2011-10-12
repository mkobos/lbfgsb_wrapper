package lbfgsb;

public class PowellSingularProblem implements MinimizationProblem{

	@Override
	public int getVariablesNo() {
		return 4;
	}

	@Override
	public DifferentiableFunction getFunction() {
		return new PowellSingularFunction();
	}
	
	@Override
	public double[] getStartingPoint() {
		return new double[]{3, -1, 0, 1};
	}
	
	@Override
	public double[] getExpectedMinimum() {
		return new double[]{0, 0, 0, 0};
	}
}

class PowellSingularFunction implements DifferentiableFunction{

	@Override
	public FunctionValues getValues(double[] point) {
		return new FunctionValues(functionValue(point), gradient(point));
	}
	
	public double functionValue(double[] pt) {
		double f0 = pt[0]+10*pt[1];
		double f1 = Math.pow(5, 0.5)*(pt[2]-pt[3]);
		double f2 = Math.pow(pt[1]-2*pt[2], 2);
		double f3 = Math.pow(10, 0.5)*Math.pow(pt[0]-pt[3], 2);
		return f0*f0+f1*f1+f2*f2+f3*f3;
	}
	
	public double[] gradient(double[] pt) {
		double dx0f = 2*(pt[0]+10*pt[1])+
			4*10*Math.pow(pt[0]-pt[3], 3);
		double dx1f = 20*(pt[0]+10*pt[1])+
			4*Math.pow(pt[1]-2*pt[2], 3);
		double dx2f = 2*5*(pt[2]-pt[3])+
			-8*Math.pow(pt[1]-2*pt[2], 3);
		double dx3f = -2*5*(pt[2]-pt[3])+
			-4*10*Math.pow(pt[0]-pt[3], 3);
		return new double[]{dx0f, dx1f, dx2f, dx3f};
	}
}
