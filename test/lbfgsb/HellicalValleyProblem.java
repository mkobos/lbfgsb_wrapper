package lbfgsb;

import java.util.ArrayList;

public class HellicalValleyProblem implements BoundedMinimizationProblem{

	@Override
	public int getVariablesNo() {
		return 3;
	}

	@Override
	public DifferentiableFunction getFunction() {
		return new HellicalValleyFunction();
	}

	@Override
	public double[] getStartingPoint() {
		return new double[]{-1, 0, 0};
	}

	@Override
	public double[] getExpectedMinimum() {
		return new double[]{1, 0, 0};
	}
	
	@Override
	public ArrayList<Bound> getBounds() {
		ArrayList<Bound> l = new ArrayList<Bound>();
		l.add(new Bound(-100.0, .8));
		l.add(new Bound(-1.0, 1.0));
		l.add(new Bound(-1.0, 1.0));
		return l;
	}

	@Override
	public double getExpectedBoundedMinimumFunctionValue() {
		return 0.99042212;
	}

}

class HellicalValleyFunction implements DifferentiableFunction {

	@Override
	public FunctionValues getValues(double[] point) {
		return new FunctionValues(functionValue(point), gradient(point));
	}
	
	private double functionValue(double[] pt) {
		double f0 = 10*(pt[2]-10*phi(pt[0], pt[1]));
		double f1 = 
			10*(Math.pow(pt[0]*pt[0]+pt[1]*pt[1], .5) - 1);
		double f2 = pt[2];
		return f0*f0+f1*f1+f2*f2;
	}
	
	private double[] gradient(double[] pt) {
		double dx0phi = 1/(2*Math.PI)*1/(1+Math.pow(pt[1]/pt[0] ,2))*
			pt[1]/Math.pow(pt[0], 2)*(-1);
		double dx0f = 2*10*(pt[2]-10*phi(pt[0], pt[1]))*
				(-100)*dx0phi + 
			2*10*(Math.pow(pt[0]*pt[0]+pt[1]*pt[1], .5) - 1)*
				5*Math.pow(Math.pow(pt[0], 2)+Math.pow(pt[1], 2), .5)*
				2*pt[0];
		
		double dx1phi = 1/(2*Math.PI)*1/(1+Math.pow(pt[1]/pt[0] ,2))*1/pt[0];
		double dx1f = 2*10*(pt[2]-10*phi(pt[0], pt[1]))*
				(-100)*dx1phi +
			2*10*(Math.pow(pt[0]*pt[0]+pt[1]*pt[1], .5) - 1)*
				5*Math.pow(Math.pow(pt[0], 2)+Math.pow(pt[1], 2), .5)*2*pt[1];
		
		double dx2f = 2*10*(pt[2]-10*phi(pt[0], pt[1]))*10 + 2*pt[2];
		
		return new double[]{dx0f, dx1f, dx2f};
	}
	
	private double phi(double x0, double x1){
		double l = 1/(2*Math.PI)*Math.atan(x1/x0);
		if(x0>0) return l;
		else return l+.5;
	}

}
