package com.producer.consumer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LocalXMLFileReader {

	LocalXMLFileReader(){

	}

	void parseXmlFile(String fileName){
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File file = new File(fileName);
			if (file.exists()) {
				Document doc = db.parse(file);
				//Testing 
				Element docElement = doc.getDocumentElement();

				// To verify file is getting readed print root element of the document
				System.out.println("Root element of the document: "
						+ docElement.getNodeName());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
