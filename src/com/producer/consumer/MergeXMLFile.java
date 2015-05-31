package com.producer.consumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MergeXMLFile {

	/**
	 * Method to merge two xml files
	 * <ol>
	 * <li>Read  the node from remote xml file</li>
	 * <li>Import the node readed from remote xml to local xml file
	 * <li>Again get the node from the local xml file</li>
	 * <li>Now insert the node before the existing child node</li>
	 * <li>Write the final output tomaster.xml</li>
	 * </ol>
	 * @param root local xml file
	 * @param insertDoc remote xml file
	 * @param toPath root node
	 * @param fromPath
	 */

	static void mergeXMLFiles(Document root, Document insertDoc, String toPath, String fromPath) {

		if (null != root) {



			try {
				Node element = getNode(insertDoc, fromPath);
				Node dest = root.importNode(element, true);
				Node node = getNode(root, toPath);
				node.insertBefore(dest, null);

				writeToFile(node);

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

		}

	}

	/**
	 * Used to get the node from the remote/local xml file
	 * @param doc
	 * @param strXpathExpression
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static  Node getNode(Document doc, String strXpathExpression)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {

		XPath xpath = XPathFactory.newInstance().newXPath();

		// XPath Query for showing all nodes value
		XPathExpression expr =xpath.compile(strXpathExpression);

		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);

		return node;
	}

	/**
	 * Write the output to master.xml file
	 * @param node
	 */

	public static void writeToFile(Node node){
		try{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");  

			DOMSource source = new DOMSource(node);
			StreamResult result = new StreamResult(new StringWriter());
			transformer.transform(source, result); 

			Writer output = new BufferedWriter(new FileWriter("master.xml"));
			String xmlOutput = result.getWriter().toString();  
			output.write(xmlOutput);
			output.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
