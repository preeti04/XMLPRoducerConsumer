package com.producer.consumer;

import java.util.Hashtable;

public class XMLConsumer extends Thread {
	private WaitingQueue queue;

	/**
	 * constructs consumer with  queue
	 * @param queue
	 */
	public XMLConsumer(WaitingQueue queue) {
		this.queue = queue;

	}
	/**
	 * read date till the queue is empty or till the fill is ended
	 * 
	 */
	public void run() {
		while (!queue.isEmpty() || !queue.isEnd()) {
			Hashtable val = (Hashtable) queue.take();
			System.out.println("Obtained by " + this.getName() + " " + val);

		}
	}
}
