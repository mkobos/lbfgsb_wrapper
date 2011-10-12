package lbfgsb;

import junit.framework.TestCase;

public class Functionality_Test extends TestCase  {
	public void testUserStop() throws LBFGSBException{
		Minimizer alg = new Minimizer();
		
		MinimizationProblem prob = new HellicalValleyProblem();
		int stopIteration = 2;
		IterationStopperWrapper wrapper = 
			new IterationStopperWrapper(stopIteration, prob.getFunction());
		Result ret = alg.run(wrapper, prob.getStartingPoint());
		assertEquals(IterationsInfo.StopType.USER, ret.iterationsInfo.type);
		assertEquals(stopIteration, ret.iterationsInfo.iterations);
	}
}

class IterationStopperWrapper implements DifferentiableFunction{
	private final int stopIteration;
	private int iterationsNo;
	private DifferentiableFunction fun;
	
	public IterationStopperWrapper(int stopIteration, 
			DifferentiableFunction fun){
		this.stopIteration = stopIteration;
		this.iterationsNo = -1;
		this.fun = fun;
	}

	@Override
	public FunctionValues getValues(double[] point) {
		iterationsNo++;
		if(iterationsNo > stopIteration) return null;
		else return fun.getValues(point);
	}
	
}