package com.ten31f.engine.batik;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ten31f.engine.AbstractEngine;
import com.ten31f.engine.Engine;

public class BatikEngine extends AbstractEngine implements Engine {

	public static final String STYLE_TEXT = "fill:none;stroke:red;stroke-width:0.25px";

	public static final String NODE_NAME_PATH = "path";
	public static final String NODE_NAME_CIRCLE = "circle";
	public static final String NODE_NAME_SVG = "svg";
	public static final String NODE_POUND_TEXT = "#text";

	public static final String ATTRIBUTE_D = "d";
	public static final String ATTRIBUTE_STYLE = "style";

	private static final Logger LOGGER = Logger.getLogger(BatikEngine.class.getName());

	@Override
	public void process(String file) throws IOException {

		Document sourceSVGDocument = createSVGDocument(file);
		Document outputSVGDocument = createOutputDocument();

		proccessNode(sourceSVGDocument.getChildNodes().item(0), outputSVGDocument.getChildNodes().item(0));

		try {
			LOGGER.info(toString(outputSVGDocument.getChildNodes().item(0)));
		} catch (TransformerException transformerException) {
			LOGGER.throwing(BatikEngine.class.getName(), "process", transformerException);
		}
	}

	private void proccessNode(Node sourcenode, Node targetNode) {

		Node appendedNode = null;

		switch (sourcenode.getNodeName()) {

		case NODE_NAME_CIRCLE:
			CircleNodeProcessor.process(sourcenode, targetNode);
			break;

		case NODE_NAME_PATH:
			PathNodeProcessor.process(sourcenode, targetNode);
			break;

		case NODE_NAME_SVG:
			appendedNode = targetNode;
			break;

		case NODE_POUND_TEXT:
			break;

		default:
			LOGGER.info(sourcenode.getNodeName());
			Node cloneNode = sourcenode.cloneNode(false);
			targetNode.getOwnerDocument().adoptNode(cloneNode);
			appendedNode = targetNode.appendChild(cloneNode);
		}

		NodeList nodeList = sourcenode.getChildNodes();

		for (int index = 0; index < nodeList.getLength(); index++) {
			proccessNode(nodeList.item(index), appendedNode);
		}

	}

	private Document createSVGDocument(String uri) throws IOException {
		String parser = XMLResourceDescriptor.getXMLParserClassName();

		SAXDocumentFactory factory = new SAXDocumentFactory(SVGDOMImplementation.getDOMImplementation(), parser);
		return factory.createDocument(uri);
	}

	private Document createOutputDocument() {
		String svgNS = "http://www.w3.org/2000/svg";
		return SVGDOMImplementation.getDOMImplementation().createDocument(svgNS, "svg", null);
	}

	private String toString(Node node) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(node), new StreamResult(writer));
		return writer.toString();
	}

}
