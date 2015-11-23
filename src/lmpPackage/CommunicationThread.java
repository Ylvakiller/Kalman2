package lmpPackage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CommunicationThread extends Thread {
	/**
	 * This is input array, commands should be entered in this in the following format:
	 * float[0] the thread ID
	 * float[1] Sensor address to request
	 */
	 protected static BlockingQueue<float[]> inQueue;

	 /**
	  * This is the output array, this will output data in the following way:
	  * float[0] the thread id
	  * float[1] Sensor Address from which data has come
	  * float[2] Data 1 e.g. acceleration X
	  * float[3] Data 2 e.g. acceleration Y
	  * float[4] Data 3 e.g. acceleration Z
	  */
	 protected static BlockingQueue<float[]> outQueue;
	 
	 private static final int amountOfSensorsPerNode = 2;
	 
	 /**
	  * Constructor to use.
	  * This constructor will create the Blocking queues in the correct size and format
	  * @param amountOfThreads the amount of threads, the queues will have 4 times this in terms of space
	  * The reason for this size is that a thread can in theory give the command for the next set of data before the first set of data is being processed.
	  * This doubles the size.
	  * Since each sensor nodes have 2 sensors this will also double the size
	  */
	 public CommunicationThread(int amountOfThreads){
		 inQueue = new ArrayBlockingQueue<float[]>(amountOfThreads*2*amountOfSensorsPerNode);
		 outQueue = new ArrayBlockingQueue<float[]>(amountOfThreads*2*amountOfSensorsPerNode);
		 
	 }
	 
}
