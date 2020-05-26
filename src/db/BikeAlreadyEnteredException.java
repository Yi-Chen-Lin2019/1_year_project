package db;

public class BikeAlreadyEnteredException extends Exception {
	private static final long serialVersionUID = 1L;

	public BikeAlreadyEnteredException(String message, Throwable e) {
		super(message, e);
	}
}
