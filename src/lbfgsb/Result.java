package lbfgsb;

/**
 * @author Mateusz Kobos
 */
public class Result {
	public double[] point;
	public double functionValue;
	public double[] gradient;
	public IterationsInfo iterationsInfo;

	public Result(double[] point, double functionValue,
			double[] gradient, IterationsInfo iterationsInfo) {
		this.point = point;
		this.functionValue = functionValue;
		this.gradient = gradient;
		this.iterationsInfo = iterationsInfo;
	}

	@Override
	public String toString(){
		return "point="+toString(point)+
			", functionValue="+functionValue+
			", gradient="+toString(gradient)+
			", iterationsInfo=("+iterationsInfo+")";
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
}
