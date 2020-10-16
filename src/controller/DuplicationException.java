package controller;

public class DuplicationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicationException(String message, Throwable e) {
		super(message, e);
	}

}
