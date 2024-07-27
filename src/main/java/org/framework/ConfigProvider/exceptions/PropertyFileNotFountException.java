package org.framework.ConfigProvider.exceptions;

public class PropertyFileNotFountException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PropertyFileNotFountException() {
		super();
	}

	public PropertyFileNotFountException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PropertyFileNotFountException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public PropertyFileNotFountException(String arg0) {
		super(arg0);
	}
	
	public PropertyFileNotFountException(Throwable arg0) {
		super(arg0);
	}
}