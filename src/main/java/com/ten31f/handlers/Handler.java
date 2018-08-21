package com.ten31f.handlers;

public interface Handler {

	public String getTAG();
	
	public StringBuilder process(String entry);
	
}
