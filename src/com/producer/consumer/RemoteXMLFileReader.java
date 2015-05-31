package com.producer.consumer;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RemoteXMLFileReader {

	void parseXMLFile() {
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			URL url = new URL("http://appvigil.co/test1.xml");
			InputStream inputStream = url.openStream();
			Document document = db.parse(inputStream);

			Element docEle = document.getDocumentElement();

			// Print root element of the document
			System.out.println("Root element of the document: "
					+ docEle.getNodeName());
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}
}
