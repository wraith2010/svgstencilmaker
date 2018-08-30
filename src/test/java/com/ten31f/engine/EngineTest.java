package com.ten31f.engine;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Test;

public class EngineTest {

	@Test
	public void mainEngineTest() throws URISyntaxException {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test-imgs/fire-fly.svg").getFile());
		
		Engine engine = new Engine();
		engine.process(file.getAbsolutePath());

	}

}
