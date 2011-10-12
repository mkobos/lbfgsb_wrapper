package lbfgsb;

/**
 * Information about the algorithm iterations
 * @author Mateusz Kobos
 */
public class IterationsInfo {
	public enum StopType{
		/** algorithm ended by reaching the maximum iterations limit */
		MAX_ITERATIONS,
		/** algorithm ended by satisfying one of the other stop conditions*/
		OTHER_STOP_CONDITIONS,
		/** algorithm ended but wasn't able to satisfy the stop conditions*/
		ABNORMAL,
		/** algorithm stopped by the user */
		USER
	}

	public int iterations;
	public int functionEvaluations;
	public StopType type;
	public String stateDescription;
	
	public IterationsInfo(int iterations, int functionEvaluations,
			StopType type, String stateDescription) {
		super();
		this.iterations = iterations;
		this.functionEvaluations = functionEvaluations;
		this.type = type;
		this.stateDescription = stateDescription;
	}
	
	@Override
	public String toString(){
		return "iterations="+iterations+
		", functionEvaluations="+functionEvaluations+
		", stopType="+type+
		", stateDescription="+stateDescription;
	}
}
