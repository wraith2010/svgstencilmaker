package com.ten31f.engine.batik;

import java.util.logging.Logger;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class PathNodeProcessor {

	private static final Logger LOGGER = Logger.getLogger(CircleNodeProcessor.class.getName());

	private static final String ATTRIBUTE_TRANSFORM = "transform";

	private PathNodeProcessor() {
		// hide constructor
	}

	public static void process(Node pathNode, Node targetNode) {

		NamedNodeMap pathAttributeNamedNodeMap = pathNode.getAttributes();

		String transformContents = pathAttributeNamedNodeMap.getNamedItem(ATTRIBUTE_TRANSFORM).getTextContent();

		transformContents = transformContents.substring(transformContents.indexOf('(') + 1,
				transformContents.indexOf(')'));

		String[] transformParts = transformContents.split(" ");

		double xTransform = Double.parseDouble(transformParts[0]);
		double yTransform = Double.parseDouble(transformParts[1]);

		
		
	}

}
