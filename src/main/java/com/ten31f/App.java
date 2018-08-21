package com.ten31f;

import com.ten31f.engine.Engine;

public class App {

	public static void main(String[] args) {

		Engine engine = new Engine();
		engine.process(args[0]);

	}
}
