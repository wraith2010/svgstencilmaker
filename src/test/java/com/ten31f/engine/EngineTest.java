package com.ten31f.engine;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.ten31f.engine.batik.BatikEngine;
import com.ten31f.engine.string.StringParserEngine;

public class EngineTest {

	@Test
	public void mainEngineTest() throws URISyntaxException, IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test-imgs/fire-fly.svg").getFile());

		Engine engine = new StringParserEngine();
		engine.process(file.getAbsolutePath());

	}

	@Test
	public void batikEngineFireFlyTest() throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test-imgs/fire-fly.svg").getFile());

		Engine engine = new BatikEngine();
		engine.process(file.getAbsolutePath());
	}

}
