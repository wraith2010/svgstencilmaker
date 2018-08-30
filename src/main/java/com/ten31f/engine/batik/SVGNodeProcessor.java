package com.ten31f.engine.batik;

import java.util.logging.Logger;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SVGNodeProcessor {

	private static final Logger LOGGER = Logger.getLogger(SVGNodeProcessor.class.getName());

	private SVGNodeProcessor() {
		// hide constructor
	}

	public static void process(Node svgNode, Node targetNode) {

		NamedNodeMap sourceSVGNamedNodeMap = svgNode.getAttributes();
		NamedNodeMap targetSVGNamedNodeMap = targetNode.getAttributes();

		for (int index = 0; index < sourceSVGNamedNodeMap.getLength(); index++) {

			//targetSVGNamedNodeMap.setNamedItemNS(
			//		targetNode.getOwnerDocument().adoptNode(sourceSVGNamedNodeMap.item(index).cloneNode(false)));

		}
	}

}
