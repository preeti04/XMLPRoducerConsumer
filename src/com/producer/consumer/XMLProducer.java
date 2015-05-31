package com.producer.consumer;


public class XMLProducer extends Thread
{
	private WaitingQueue  queue;

	/**
	 * constructs producer with queue
	 * @param queue
	 */
	public XMLProducer(WaitingQueue queue)
	{
		this.queue=queue;
	}

	/**
	 * Method called for parsing the xml file
	 * @parsing master file using SAX Parser API
	 * @param master file path
	 * @param queue
	 */
	public void run()
	{
		XMLParser parser = new XMLParser("file:B:/ComApplication/XMLPRoducerConsumer/master.xml", queue);
		parser.parse();

	}
}

