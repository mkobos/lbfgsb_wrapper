package lbfgsb;

/**
 * Bound information for a certain variable
 * @author Mateusz Kobos
 */
public class Bound{
	public Double lowerBound;
	public Double upperBound;
	
	/**
	 * @param lowerBound Value of the lower bound. No bound is defined if the
	 * value is null.
	 * @param upperBound Value of the upper bound. No bound is defined if the
	 * value is null.
	 */
	public Bound(Double lowerBound, Double upperBound)
	{
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public boolean isLowerBoundDefined(){
		return lowerBound!=null;
	}
	
	public boolean isUpperBoundDefined(){
		return upperBound!=null;
	}
	
	@Override
	public String toString(){
		String lowerBoundStr = null;
		String upperBoundStr = null;
		if(isLowerBoundDefined()) lowerBoundStr = "["+lowerBound;
		else lowerBoundStr = "(-\\infty";
		if(isUpperBoundDefined()) upperBoundStr = upperBound+"]";
		else upperBoundStr = "\\infty)";
		return lowerBoundStr+", "+upperBoundStr;
	}
};
