package com.producer.consumer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLProducerConsumerMain {
	public static void main(String[] args) {

		//Merging local and remote file into master.xml and save in local directory
		mergeFiles();


		//Queue with max number of element is 3
		WaitingQueue queue = new WaitingQueue();
		XMLProducer producerThread = new XMLProducer(queue);
		XMLConsumer consumerThread1 = new XMLConsumer(queue);
		consumerThread1.setName("First Consumer");
		XMLConsumer consumerThread2 = new XMLConsumer(queue);
		consumerThread2.setName("Second Consumer");

		producerThread.start();
		consumerThread1.start();
		consumerThread2.start();

		try {
			consumerThread1.join();
		} catch (Exception ex) {
		}

	}

	/**
	 * Read local xml file
	 * Read remote xml file
	 * Merge the two xml file into master.xml file
	 * 
	 */
	public static void mergeFiles() {

		try{
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = domFactory.newDocumentBuilder();

			//Reading local xml file
			Document document1 = builder.parse(new File("test2.xml"));

			//Reading remote xml file
			URL url = new URL("http://appvigil.co/test1.xml");
			InputStream inputStream = url.openStream();
			Document document2 = builder.parse(inputStream);

			MergeXMLFile mergeFile = new MergeXMLFile();

			// root (node)point from where nodes from files should be appended is project
			mergeFile.mergeXMLFiles(document1,document2,"/project", "project/modelVersion");
			System.out.println("Succesful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}		

}
