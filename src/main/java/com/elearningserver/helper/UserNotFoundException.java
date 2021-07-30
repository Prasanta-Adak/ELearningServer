package com.elearningserver.helper;

public class UserNotFoundException extends Exception{

	public UserNotFoundException() {
		super("User Not found");
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
