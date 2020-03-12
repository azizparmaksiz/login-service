package com.eteshis.exception;

import java.io.Serializable;

public class ExceptionMessage implements Serializable {
	private String errorCode;
	private String args;

	public ExceptionMessage(String errorCode) {
		this.errorCode = errorCode;
	}

	public ExceptionMessage(String errorCode, String args) {
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
