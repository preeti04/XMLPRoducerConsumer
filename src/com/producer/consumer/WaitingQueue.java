package com.producer.consumer;

import java.util.Vector;

/**
 * Queue used by producer and consumer
 * Producer cannot write to the queue untill enough space is available
 * Consumer cannot read to the queue has some data to read
 */
public class WaitingQueue extends Object {
	private Vector list = new Vector();

	private boolean eof = false;
	public WaitingQueue() {
	}

	/**
	 * Put the data into queue till enough space is available ie 3 element max
	 * Once queue has data, notify the waiting consumers to read the data from queue
	 * If no room is available it will be in waiting state.
	 * @param data data to be stored into queue.
	 * 
	 */
	public synchronized void put(Object data) {

		while (list.size() >= 3) {
			try {
				System.out.println("Queue is full.");
				wait();
			} catch (Exception ex) {
			}
		}
		list.add(data);
		notifyAll();
	}

	/**
	 * To check if queue is empty or not
	 * @return true|false if queue is empty or not.
	 */
	public synchronized boolean isEmpty() {
		return (list.size() == 0 ? true : false);
	}

	/**
	 * Read the data from queue by consumer and wait untill data is available in queue
	 * Notify other produce after reading all data ie if queue is empty.
	 * @param data data to be readed from queue
	 */


	public synchronized Object take() {
		//wait until there is room to store
		//come out if the end of file signaled
		while (list.size() <= 0 && (eof != true)) {
			try {
				System.out.println("Waiting to consume data");
				wait();
			} catch (Exception ex) {
			}
		}
		Object obj = null;
		if (list.size() > 0) {
			obj = list.remove(list.size()-1);
		} else {
			// System.out.println("Woke up because end of document");
		}
		notifyAll();
		return obj;

	}
	/**
	 * Identifies files has reached to end and notify all thread to stop reading
	 *
	 */
	public synchronized void end() {
		eof = true;
		notifyAll();
	}
	/**
	 * Specify wheather file has reached to end or not ie EOF is reached or not
	 * @return
	 */
	public synchronized boolean isEnd() {
		return eof;
	}

}
