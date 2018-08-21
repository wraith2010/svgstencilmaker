package com.ten31f.engine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Engine {

	private static final String KEY_RECTANGLE = "<rect";
	private static final String KEY_CIRCLE = "<circle";
	private static final String KEY_LINE = "<line";
	private static final String KEY_PATH = "<path";

	private static final String KEY_CX = "cx=";
	private static final String KEY_CY = "cy=";
	private static final String KEY_R = "r=";

	// <path d="M600,350 l 50,-25
	// a25,25 -30 0,1 50,-25 l 50,-25
	// a25,50 -30 0,1 50,-25 l 50,-25
	// a25,75 -30 0,1 50,-25 l 50,-25
	// a25,100 -30 0,1 50,-25 l 50,-25"
	// fill="none" stroke="red" stroke-width="5" />

	// private static final String PATH_BEZIER_CURVES = "<path d=\"M10 10 C 20 20,
	// 40 20, 50 10\" stroke=\"black\" fill=\"transparent\"/>\n";
	private static final String PATH_BEZIER_CURVES = "<path d=\"M%s %s C %s %s, %s %s, %s %s\" stroke=\"green\" fill=\"none\"/>\n";

	//
	// style="fill:
	// part[5]: none;stroke:
	// part[6]: red;stroke-linecap:
	// part[7]: round;stroke-miterlimit:
	// part[8]: 10;stroke-width:
	// part[9]: 0.25px"

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
			return whiteSpace + line;

		case KEY_PATH:
			return whiteSpace + line;

		default:
			return whiteSpace + line;

		}

	}

	private String getOutputFileName(String file) {

		int lastDot = file.lastIndexOf('.');
		return file.substring(0, lastDot) + "-stencil.svg";

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

		System.out.println("xCenter = " + xCenter);
		System.out.println("yCenter = " + yCenter);
		System.out.println("radius = " + radius);

		// subPaths.add(String.format(PATH_ARC, xCenter - radius, yCenter));
		subPaths.add(generateArch(xCenter, yCenter, radius));

		String orginal = String.join(" ", parts);

		return orginal + "\n" + String.join("\n", subPaths);
	}

	private double fetchValue(String section) {

		return Double.parseDouble(section.split("\"")[1]);
	}

	private static final String PATH_ARC = "<path d=\"M%s %S A%s %s 0 1 %s %s %s\"  fill=\"none\" stroke=\"red\" />\n";

	private String generateArch(double x, double y, double radius) {
		return String.format(PATH_ARC, x - radius, y, radius, x - radius, y, x, y - radius);
	}

}
