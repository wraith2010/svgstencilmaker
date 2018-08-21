package com.ten31f.handlers;

public class PassThroughHandler implements Handler {

	@Override
	public String getTAG() {
		return "*";
	}

	@Override
	public StringBuilder process(String entry) {
		return new StringBuilder(entry);
	}

}
