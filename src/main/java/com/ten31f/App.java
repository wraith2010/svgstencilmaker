package com.ten31f;

import java.io.IOException;

import com.ten31f.engine.Engine;
import com.ten31f.engine.string.StringParserEngine;

public class App {

	public static void main(String[] args) {

		Engine engine = new StringParserEngine();
		try {
			engine.process(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
