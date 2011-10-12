package lbfgsb;

import java.util.ArrayList;

public interface BoundedMinimizationProblem extends MinimizationProblem{
	public ArrayList<Bound> getBounds();
	public double getExpectedBoundedMinimumFunctionValue();
}
