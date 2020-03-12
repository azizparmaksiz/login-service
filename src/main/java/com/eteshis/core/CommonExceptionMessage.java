package com.eteshis.core;

import java.io.Serializable;

public class CommonExceptionMessage implements Serializable {
	private String errorCode;
	private String args;
	
	public CommonExceptionMessage(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
	public CommonExceptionMessage(String errorCode, String args) {
		this.errorCode = errorCode;
		this.args = args;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
}
