package lbfgsb;

import java.util.ArrayList;

public class PowellBadlyScaledProblem implements BoundedMinimizationProblem{

	@Override
	public int getVariablesNo() {
		return 2;
	}
	
	@Override
	public DifferentiableFunction getFunction() {
		return new PowellBadlyScaledFunction();
	}
	
	@Override
	public double[] getStartingPoint() {
		return new double[]{0, 1};
	}

	@Override
	public double[] getExpectedMinimum() {
		return new double[]{1.098e-5, 9.106};
	}
	
	@Override
	public ArrayList<Bound> getBounds() {
		ArrayList<Bound> bounds = new ArrayList<Bound>();
		bounds.add(new Bound(0.0, 1.0));
		bounds.add(new Bound(1.0, 9.0));
		return bounds;
	}

	@Override
	public double getExpectedBoundedMinimumFunctionValue() {
		return 0.15125900e-9;
	}

}

class PowellBadlyScaledFunction implements DifferentiableFunction {

	@Override
	public FunctionValues getValues(double[] pt) {
		double f0 = Math.pow(10, 4)*pt[0]*pt[1]-1;
		double f1 = Math.exp(-pt[0])+Math.exp(-pt[1])-1.0001;
		double funVal = f0*f0+f1*f1;
		
		double dx0f0 = 2*Math.pow(10, 4)*f0*pt[1];
		double dx0f1 = -2*f1*Math.exp(-pt[0]);
		double dx0f = dx0f0+dx0f1;
		
		double dx1f0 = 2*Math.pow(10, 4)*f0*pt[0];
		double dx1f1 = -2*f1*Math.exp(-pt[1]);
		double dx1f = dx1f0+dx1f1;
		
		double[] g =  new double[]{dx0f, dx1f};
		FunctionValues vals = new FunctionValues(funVal, g);
		//System.out.println("In point "+toString(pt)+"f="+vals.functionValue);
		return vals;
	}
	
//	private static String toString(double[] point){
//		StringBuilder b = new StringBuilder();
//		b.append("[");
//		for(int i = 0; i < point.length; i++){
//			b.append(point[i]);
//			if(i!=point.length-1) b.append(",");
//		}
//		b.append("]");
//		return b.toString();
//	}
}
