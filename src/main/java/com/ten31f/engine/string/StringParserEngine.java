package com.ten31f.engine.string;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.ten31f.engine.AbstractEngine;
import com.ten31f.engine.Engine;

public class StringParserEngine extends AbstractEngine implements Engine {

	private static final String KEY_RECTANGLE = "<rect";
	private static final String KEY_CIRCLE = "<circle";
	private static final String KEY_LINE = "<line";
	private static final String KEY_PATH = "<path";

	private static final String KEY_CX = "cx=";
	private static final String KEY_CY = "cy=";
	private static final String KEY_R = "r=";

	private static final String PATH_ARC = "<path d=\"M%s %S A %s,%s %s, %s, %s, %s, %s \" style=\"fill:none;stroke:red;stroke-width:0.25px\" />\n";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ten31f.engine.Engine#process(java.lang.String)
	 */
	@Override
	public void process(String file) {

		Path inputFile = Paths.get(file);

		try {
			FileOutputStream outputStream = new FileOutputStream(getOutputFileName(file));

			try (Stream<String> stream = Files.lines(inputFile)) {

				stream.forEach(x -> {
					try {
						outputStream.write(processLine(x).getBytes());
						outputStream.write("\n".getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			} catch (IOException e) {
				e.printStackTrace();
			}

			outputStream.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String processLine(String line) {

		int whiteSpaceSize = line.length() - line.trim().length();

		String whiteSpace = line.substring(0, whiteSpaceSize);

		String[] parts = line.trim().split(" ");

		switch (parts[0]) {

		case KEY_RECTANGLE:
			return whiteSpace + line;

		case KEY_CIRCLE:
			return whiteSpace + processCircle(parts);

		case KEY_LINE:
			return whiteSpace + processLine(parts);

		case KEY_PATH:
			return whiteSpace + line;

		default:
			return whiteSpace + line;

		}

	}

	private String processLine(String[] parts) {

		return String.join(" ", parts);
	}

	private String processCircle(String[] parts) {

		List<String> subPaths = new ArrayList<>();

		double radius = 0;
		double xCenter = 0;
		double yCenter = 0;

		for (int index = 0; index < parts.length; index++) {

			if (parts[index].startsWith(KEY_CX)) {
				xCenter = fetchValue(parts[index]);
			}

			if (parts[index].startsWith(KEY_CY)) {
				yCenter = fetchValue(parts[index]);
			}

			if (parts[index].startsWith(KEY_R)) {
				radius = fetchValue(parts[index]);
			}

		}

		subPaths.addAll(generateArchs(xCenter, yCenter, radius));

		return String.join("\n", subPaths);
	}

	private double fetchValue(String section) {

		return Double.parseDouble(section.split("\"")[1]);
	}

	private List<String> generateArchs(double x, double y, double radius) {
		List<String> arches = new ArrayList<>();

		arches.add(generateArch(x, y, radius, 5, 85));
		arches.add(generateArch(x, y, radius, 95, 175));
		arches.add(generateArch(x, y, radius, 185, 265));
		arches.add(generateArch(x, y, radius, 275, 355));

		return arches;
	}

	private String generateArch(double x, double y, double radius, double startAngle, double stopAngle) {

		double xStart = x + (radius * Math.cos(Math.toRadians(startAngle)));
		double yStart = y + (radius * Math.sin(Math.toRadians(startAngle)));
		double xStop = x + (radius * Math.cos(Math.toRadians(stopAngle)));
		double yStop = y + (radius * Math.sin(Math.toRadians(stopAngle)));

		return String.format(PATH_ARC, xStart, yStart, radius, radius, 0, 0, 1, xStop, yStop);

	}

}
