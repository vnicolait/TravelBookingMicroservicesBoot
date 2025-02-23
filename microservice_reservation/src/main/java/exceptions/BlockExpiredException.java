package exceptions;

public class BlockExpiredException extends RuntimeException{

	public BlockExpiredException(String message) {
		super(message);
	}
}
