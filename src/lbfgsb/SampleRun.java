package lbfgsb;

import java.util.ArrayList;

public class SampleRun {
	public static void main(String[] args){
		try {
			QuadraticFun fun = new QuadraticFun();
			Minimizer alg = new Minimizer();
//			alg.setIterationFinishedListener(new SampleListener());
			ArrayList<Bound> bounds = new ArrayList<Bound>();
			bounds.add(new Bound(new Double(10), null));
			alg.setBounds(bounds);
			Result result = alg.run(fun, new double[]{40});
			System.out.println("The final result: "+result);
		} catch (LBFGSBException e) {
			e.printStackTrace();
		}
	}
}

class QuadraticFun implements DifferentiableFunction{
	public FunctionValues getValues(double[] point){
		double p = point[0];
		System.out.println("Calculating function for x="+p);
		return new FunctionValues(Math.pow(p+4, 2), 
				new double[]{2*(p+4)});
	}
}

//class SampleListener implements IterationFinishedListener{
//	int i = 0;
//	@Override
//	public boolean iterationFinished(double[] point,
//			double functionValue, double[] gradient) {
//		System.out.println("Iteration "+i+" finished with x="+point[0]+
//				", function value="+functionValue+", gradient="+gradient[0]);
//		i++;
//		return true;
//	}
//}