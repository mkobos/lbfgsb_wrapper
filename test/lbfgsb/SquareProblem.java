package lbfgsb;

import java.util.ArrayList;

public class SquareProblem implements BoundedMinimizationProblem{

	@Override
	public int getVariablesNo() {
		return 1;
	}

	@Override
	public DifferentiableFunction getFunction() {
		return new SquareFunction();
	}

	@Override
	public double[] getStartingPoint() {
		return new double[]{40};
	}
	
	@Override
	public double[] getExpectedMinimum() {
		return new double[]{4};
	}

	@Override
	public ArrayList<Bound> getBounds() {
		ArrayList<Bound> bounds = new ArrayList<Bound>();
		bounds.add(new Bound(5.0, 100.0));
		return bounds;
	}

	@Override
	public double getExpectedBoundedMinimumFunctionValue() {
		return 1;
	}

}

class SquareFunction implements DifferentiableFunction{

	@Override
	public FunctionValues getValues(double[] point) {
		return new FunctionValues(functionValue(point), gradient(point));
	}
	
	public double functionValue(double[] pt){
		return (pt[0]-4)*(pt[0]-4);
	}
	
	public double[] gradient(double[] pt) {
		return new double[]{2*(pt[0]-4)};
	}
}
