package lbfgsb;

/**
 * @author Mateusz Kobos
 */
public class LBFGSBException extends Exception{
	private static final long serialVersionUID = 1L;

	public LBFGSBException(String info){
		super("LBFGS algorithm exception: \""+info+"\"");
	}
}
