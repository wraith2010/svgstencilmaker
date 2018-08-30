package com.ten31f.engine;

public class AbstractEngine {

	private static final String APPENDED_EXTENSION = "-stencil.svg";
	
	public String getOutputFileName(String file) {

		int lastDot = file.lastIndexOf('.');

		return file.substring(0, lastDot) + APPENDED_EXTENSION;

	}

}
