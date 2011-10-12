package lbfgsb;

import java.util.List;

import lbfgsb.jniwrapper.*;

/**
 * A more low-level wrapper for the L-BFGS-B algorithm.
 * 
 * It is used internally by the {@link Minimizer} class.
 * @author Mateusz Kobos
 */
public class Wrapper{
	static {
		System.loadLibrary("lbfgsb_wrapper");
	}
	
	private lbfgsb data;
	
	Wrapper(int variablesNo, int correctionsNo){
		this.data = lbfgsb_wrapper.lbfgsb_create(
				variablesNo, correctionsNo);
	}
	
	public void setBounds(List<Bound> bounds){
		assert getVariablesNo()==bounds.size();
		SWIGTYPE_p_int nbd = data.getNbd();
		SWIGTYPE_p_double l = data.getL();
		SWIGTYPE_p_double u = data.getU();
		for(int i = 0; i < bounds.size(); i++){
			Bound b = bounds.get(i);
			lbfgsb_wrapper.intArray_setitem(nbd, i, getBoundCode(b));
			double lowerBound = 0;
			if(b.lowerBound!=null) lowerBound = b.lowerBound;
			double upperBound = 0;
			if(b.upperBound!=null) upperBound = b.upperBound;
			lbfgsb_wrapper.doubleArray_setitem(l, i, lowerBound);
			lbfgsb_wrapper.doubleArray_setitem(u, i, upperBound);
		}
	}
	
	public void setFunctionFactor(double value){
		this.data.setFactr(value);
	}
	
	public void setMaxGradientNorm(double value){
		this.data.setPgtol(value);
	}
	
	public double[] getX(){
		return convertArrayC2Java(data.getX(), getVariablesNo());
	}
	
	public void setX(double[] x){
		copyArrayJava2C(x, data.getX());
	}
	
	public lbfgsb_task_type getTask(){
		return lbfgsb_wrapper.lbfgsb_get_task(data);
	}
	
	public void setTask(lbfgsb_task_type type){
		lbfgsb_wrapper.lbfgsb_set_task(data, type);
	}
	
	public int getVariablesNo(){
		return data.getN();
	}
	
	public double getFunctionValue(){
		return data.getF();
	}
	
	public void setFunctionValue(double value){
		data.setF(value);
	}
	
	public double[] getGradient(){
		return convertArrayC2Java(data.getG(), getVariablesNo());
	}
	
	public void setGradient(double[] value){
		copyArrayJava2C(value, data.getG());
	}
	
	public String getStateDescription(){
		return data.getTask().trim();
	}
	
	public void setDebugLevel(int value){
		data.setIprint(value);
	}
	public int getDebugLevel(){
		return data.getIprint();
	}

	public void step(){
		lbfgsb_wrapper.lbfgsb_step(data);
	}
	
	public IterationsInfo iterate(DifferentiableFunction fun, 
			int maxIterations, IterationFinishedListener listener) 
	throws LBFGSBException{
		setTask(lbfgsb_task_type.LBFGSB_START);
		int iterationsFinished = 0;
		int functionEvaluations = 0;
		while(true)
		{
			if(maxIterations>0 && iterationsFinished==maxIterations)
			{
				stopAlgorithm();
				return new IterationsInfo(iterationsFinished, 
						functionEvaluations, 
						IterationsInfo.StopType.MAX_ITERATIONS,
						getStateDescription());
			}
			step();
			lbfgsb_task_type type = getTask();
			switch(type){
			case LBFGSB_FG:
				functionEvaluations++;
				FunctionValues vals = fun.getValues(getX());
				if(vals==null){
					stopAlgorithm();
					return new IterationsInfo(iterationsFinished, 
							functionEvaluations, 
							IterationsInfo.StopType.USER,
							getStateDescription());
				}
				setFunctionValue(vals.functionValue);
				setGradient(vals.gradient);	
				break;
			case LBFGSB_NEW_X:
				iterationsFinished++;
				if(listener!=null){
					boolean continueAlg = listener.iterationFinished(
							getX(),	getFunctionValue(), getGradient());
					if(!continueAlg){
						stopAlgorithm();
						return new IterationsInfo(iterationsFinished, 
								functionEvaluations, 
								IterationsInfo.StopType.USER,
								getStateDescription());
					}
				}
				break;
			case LBFGSB_CONV:
				return new IterationsInfo(iterationsFinished, 
						functionEvaluations, 
						IterationsInfo.StopType.OTHER_STOP_CONDITIONS,
						getStateDescription());
			case LBFGSB_ABNO:
				return new IterationsInfo(iterationsFinished, 
						functionEvaluations, 
						IterationsInfo.StopType.ABNORMAL,
						getStateDescription());
			case LBFGSB_ERROR:
				throw new LBFGSBException(getStateDescription());
			default:
				throw new LBFGSBException("Unknown task");
			}
		}
	}
	
	/**
	 * Release the resources 
	 * (it is also automatically done by the Garbage Collector)
	 */
	public void close(){
		if(!isClosed()){
			lbfgsb_wrapper.lbfgsb_delete(data);
			data = null;			
		}
	}
	
	/**
	 * @return true iff the resources are released
	 */
	public boolean isClosed(){
		return data==null;
	}
	
	@Override
	protected void finalize() throws Throwable{
		try {
			close();
		} finally {
			super.finalize();
		}		
    }
	
	private void stopAlgorithm(){
		setTask(lbfgsb_task_type.LBFGSB_STOP);
		step();		
	}
	
	private static int getBoundCode(Bound b){
		if(!b.isLowerBoundDefined() && !b.isUpperBoundDefined()) return 0;
		if(b.isLowerBoundDefined() && !b.isUpperBoundDefined()) return 1;
		if(b.isLowerBoundDefined() && b.isUpperBoundDefined()) return 2;
		else return 3;
	}

	private  static void copyArrayJava2C(
			double[] arrJ, SWIGTYPE_p_double arrC){
		for(int i = 0; i < arrJ.length; i++)
			lbfgsb_wrapper.doubleArray_setitem(arrC, i, arrJ[i]);
	}
	
	private  static double[] convertArrayC2Java(
			SWIGTYPE_p_double arrC, int length){
		double[] arrJ = new double[length];
		for(int i = 0; i < arrJ.length; i++)
			arrJ[i] = lbfgsb_wrapper.doubleArray_getitem(arrC, i);
		return arrJ;
	}	
};
