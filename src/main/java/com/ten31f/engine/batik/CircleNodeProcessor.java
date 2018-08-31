package com.ten31f.engine.batik;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class CircleNodeProcessor {

	private static final String ATTRIBUTE_CX = "cx";
	private static final String ATTRIBUTE_CY = "cy";
	private static final String ATTRIBUTE_R = "r";

	private static final String PATH_ARC = "M%s %S A %s,%s %s, %s, %s, %s, %s";

	private static final Logger LOGGER = Logger.getLogger(CircleNodeProcessor.class.getName());

	private CircleNodeProcessor() {
		// hide constructor
	}

	public static void process(Node circleNode, Node targetNode) {

		NamedNodeMap circleAttributeNamedNodeMap = circleNode.getAttributes();

		double radius = Double.parseDouble(circleAttributeNamedNodeMap.getNamedItem(ATTRIBUTE_R).getTextContent());
		double xCenter = Double.parseDouble(circleAttributeNamedNodeMap.getNamedItem(ATTRIBUTE_CX).getTextContent());
		double yCenter = Double.parseDouble(circleAttributeNamedNodeMap.getNamedItem(ATTRIBUTE_CY).getTextContent());

		String msg = String.format("circle: cx=%s cy=%s r=%s", xCenter, yCenter, radius);
		LOGGER.info(msg);

		generateArchsFromCircle(xCenter, yCenter, radius, targetNode);
	}

	private static void generateArchsFromCircle(double x, double y, double radius, Node targetNode) {

		generateArch(x, y, radius, 5, 85, targetNode);
		generateArch(x, y, radius, 95, 175, targetNode);
		generateArch(x, y, radius, 185, 265, targetNode);
		generateArch(x, y, radius, 275, 355, targetNode);

	}

	private static void generateArch(double x, double y, double radius, double startAngle, double stopAngle,
			Node targetNode) {

		double xStart = x + (radius * Math.cos(Math.toRadians(startAngle)));
		double yStart = y + (radius * Math.sin(Math.toRadians(startAngle)));
		double xStop = x + (radius * Math.cos(Math.toRadians(stopAngle)));
		double yStop = y + (radius * Math.sin(Math.toRadians(stopAngle)));

		Element pathElement = targetNode.getOwnerDocument().createElement(BatikEngine.NODE_NAME_PATH);

		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		pathElement.setAttribute(BatikEngine.ATTRIBUTE_STYLE, BatikEngine.STYLE_TEXT);
		pathElement.setAttribute(BatikEngine.ATTRIBUTE_D,
				String.format(PATH_ARC, decimalFormat.format(xStart), decimalFormat.format(yStart),
						decimalFormat.format(radius), decimalFormat.format(radius), 0, 0, 1,
						decimalFormat.format(xStop), decimalFormat.format(yStop)));

		targetNode.appendChild(pathElement);
	}
}
