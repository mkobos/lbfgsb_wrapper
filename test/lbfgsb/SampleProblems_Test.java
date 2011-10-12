package lbfgsb;

import junit.framework.TestCase;

public class SampleProblems_Test extends TestCase {
	public void testHellicalValley() throws LBFGSBException{
		checkMinimizationProblem(new HellicalValleyProblem(), 10e-6);
	}
	public void testBoundedHellicalValley() throws LBFGSBException{
		checkBoundedMinimizationProblem(new HellicalValleyProblem(), 10e-3);
	}
	
//	public void testPowellBadlyScaled() throws LBFGSBException{
//		checkMinimizationProblem(new PowellBadlyScaledProblem(), 10e-6);
//	}
//	public void testBoundedPowellBadlyScaled() throws LBFGSBException{
//		checkBoundedMinimizationProblem(new PowellBadlyScaledProblem(), 10e-6);
//	}
	
	public void testWood() throws LBFGSBException{
		checkMinimizationProblem(new WoodProblem(), 10e-5);
	}
	public void testBoundedWood() throws LBFGSBException{
		checkBoundedMinimizationProblem(new WoodProblem(), 10e-6);
	}
	
	public void testRosenbrock() throws LBFGSBException{
		checkMinimizationProblem(new RosenbrockProblem(), 10e-6);
	}
	
	public void testSquare() throws LBFGSBException{
		checkMinimizationProblem(new SquareProblem(), 10e-6);
	}
	public void testBoundedSquare() throws LBFGSBException{
		checkBoundedMinimizationProblem(new SquareProblem(), 10e-6);
	}
	
	public void testPowellSingular() throws LBFGSBException{
		checkMinimizationProblem(new PowellSingularProblem(), 10e-2);
	}
	
	private void checkMinimizationProblem(MinimizationProblem prob, 
			double precision) 
	throws LBFGSBException{
		Minimizer alg = new Minimizer();
		
//		StopConditions stopConditions = alg.getStopConditions();
//		stopConditions.setFunctionReductionFactorInactive();
//		stopConditions.setMaxIterationsInactive();
//		stopConditions.setMaxGradientNormInactive();
//		alg.getStopConditions().setFunctionReductionFactor(1);
//		alg.setIterationFinishedListener(new IterationListener());
//		alg.setDebugLevel(999);
		
		Result ret = alg.run(prob.getFunction(), prob.getStartingPoint());
	
		assertTrue(toString(prob.getExpectedMinimum())+"!="+toString(ret.point), 
				areEqual(prob.getExpectedMinimum(), ret.point, precision));
	}
	
	private void checkBoundedMinimizationProblem(
			BoundedMinimizationProblem prob, double precision) 
	throws LBFGSBException{
		Minimizer alg = new Minimizer();
		
//		StopConditions stopConditions = alg.getStopConditions();
//		stopConditions.setFunctionReductionFactorInactive();
//		stopConditions.setMaxIterationsInactive();
//		stopConditions.setMaxGradientNormInactive();
		
		alg.setBounds(prob.getBounds());
		
		Result ret = alg.run(prob.getFunction(), prob.getStartingPoint());
		double f = ret.functionValue;
		assertEquals(prob.getExpectedBoundedMinimumFunctionValue(), 
				f, precision);
	}
	
	private static String toString(double[] point){
		StringBuilder b = new StringBuilder();
		b.append("[");
		for(int i = 0; i < point.length; i++){
			b.append(point[i]);
			if(i!=point.length-1) b.append(",");
		}
		b.append("]");
		return b.toString();
	}
	
	private static boolean areEqual(double[] p0, double[] p1, 
			double precision){
		if(p0.length!=p1.length) return false;
		for(int i = 0; i < p0.length; i++)
			if(Math.abs(p0[i]-p1[i]) > precision) return false;
		return true;
	}
}

//class IterationListener implements IterationFinishedListener{
//	int i = 0;
//	@Override
//	public void iterationFinished(double[] point,
//			double functionValue, double[] gradient) {
//		System.out.println("Iteration "+i+
//				": finished with x="+toString(point)+", "+
//				"fun_val="+functionValue+", gradient="+toString(gradient));
//		i++;
//	}
//	
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
//}
