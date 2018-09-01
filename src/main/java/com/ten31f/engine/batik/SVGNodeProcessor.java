package com.ten31f.engine.batik;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SVGNodeProcessor {

	// contentScriptType
	// width
	// xmlns:xlink
	// zoomAndPan
	// contentStyleType
	// viewBox
	// height
	// id
	// data-name
	// preserveAspectRatio
	// xmlns
	// version

	private static final String ATTRIBUTE_WIDTH = "width";
	private static final String ATTRIBUTE_HEIGHT = "height";
	private static final String ATTRIBUTE_VIEWBOX = "viewBox";

	private static final String[] ATTRIBUTE_WHITELIST = { ATTRIBUTE_WIDTH, ATTRIBUTE_HEIGHT, ATTRIBUTE_VIEWBOX };

	private SVGNodeProcessor() {
		// hide constructor
	}

	public static void process(Node svgNode, Node targetNode) {

		NamedNodeMap sourceSVGNamedNodeMap = svgNode.getAttributes();
		NamedNodeMap targetSVGNamedNodeMap = targetNode.getAttributes();

		for (int index = 0; index < ATTRIBUTE_WHITELIST.length; index++) {

			targetSVGNamedNodeMap.setNamedItemNS(targetNode.getOwnerDocument()
					.adoptNode(sourceSVGNamedNodeMap.getNamedItem(ATTRIBUTE_WHITELIST[index])));

		}
	}

}
