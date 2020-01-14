package model;

public class CodedException extends Exception {
	private int errorCode;

	public CodedException(String message, Integer errorCode) {
		
		super(message);
		this.errorCode=errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
}
