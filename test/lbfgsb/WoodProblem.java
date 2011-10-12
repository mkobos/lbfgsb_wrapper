package lbfgsb;

import java.util.ArrayList;

class WoodProblem implements BoundedMinimizationProblem{
	@Override
	public int getVariablesNo() {
		return 4;
	}

	@Override
	public DifferentiableFunction getFunction() {
		return new WoodFunction();
	}
	
	@Override
	public double[] getStartingPoint() {
		return new double[]{-3, -1, -3, -1};
	}

	@Override
	public double[] getExpectedMinimum() {
		return new double[]{1, 1, 1, 1};
	}
	
	@Override
	public ArrayList<Bound> getBounds() {
		ArrayList<Bound> bounds = new ArrayList<Bound>();
		bounds.add(new Bound(-100.0, 0.0));
		bounds.add(new Bound(-100.0, 10.0));
		bounds.add(new Bound(-100.0, 100.0));
		bounds.add(new Bound(-100.0, 100.0));
		return bounds;
	}

	@Override
	public double getExpectedBoundedMinimumFunctionValue() {
		return 0.15567008e1;
	}
}

class WoodFunction implements DifferentiableFunction {
	private static final long serialVersionUID = 1L;

	@Override
	public FunctionValues getValues(double[] point) {
		return new FunctionValues(functionValue(point), gradient(point));
	}
	
	private double functionValue(double[] pt) {
		double f0 = 10*(pt[1]-Math.pow(pt[0], 2));
		double f1 = 1-pt[0];
		double f2 = Math.sqrt(90)*(pt[3]-Math.pow(pt[2], 2));
		double f3 = 1-pt[2];
		double f4 = Math.sqrt(10)*(pt[1]+pt[3]-2);
		double f5 = (pt[1]-pt[3])/Math.sqrt(10);
		return f0*f0+f1*f1+f2*f2+f3*f3+f4*f4+f5*f5;
	}
	
	private double[] gradient(double[] pt) {
		double dx0f = -400*(pt[1]-Math.pow(pt[0], 2))*pt[0]-2*(1-pt[0]);
		double dx1f = 200*(pt[1]-Math.pow(pt[0], 2))+
			20*(pt[1]+pt[3]-2)+2/10.0*(pt[1]-pt[3]);
		double dx2f = -4*90*(pt[3]-Math.pow(pt[2], 2))*pt[2]-2*(1-pt[2]);
		double dx3f = 2*90*(pt[3]-Math.pow(pt[2], 2))+
			20*(pt[1]+pt[3]-2)-2/10.0*(pt[1]-pt[3]);
		return new double[]{dx0f, dx1f, dx2f, dx3f};
	}
}
