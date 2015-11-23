package lmpPackage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CommunicationThread extends Thread {
	/**
	 * This is input array, commands should be entered in this in the following format:
	 * float[0] the thread ID
	 * float[1] Sensor address to request
	 */
	protected static BlockingQueue<long[]> commandQueue;

	/**
	 * This is the output array, this will output data in the following way:
	 * float[0] the thread id
	 * float[1] Sensor Address from which data has come
	 * float[2] A timestamp of this dataRetrieval moment
	 * float[3] Data 1 e.g. acceleration X
	 * float[4] Data 2 e.g. acceleration Y
	 * float[5] Data 3 e.g. acceleration Z
	 */
	protected static BlockingQueue<String[]> dataQueue;

	/**
	 * This is the amount of sensors each node has, used to calculate the size of the queues
	 */
	private static final int amountOfSensorsPerNode = 2;

	/**
	 * This variable will make absolutely sure that the correct constructor is called
	 */
	private boolean initialised = false;
	/**
	 * Constructor to use.
	 * This constructor will create the Blocking queues in the correct size and format
	 * @param amountOfThreads the amount of threads, the queues will have 4 times this in terms of space
	 * The reason for this size is that a thread can in theory give the command for the next set of data before the first set of data is being processed.
	 * This doubles the size.
	 * Since each sensor nodes have 2 sensors this will also double the size
	 */
	public CommunicationThread(int amountOfThreads){
		commandQueue = new ArrayBlockingQueue<long[]>(amountOfThreads*2*amountOfSensorsPerNode);
		dataQueue = new ArrayBlockingQueue<String[]>(amountOfThreads*2*amountOfSensorsPerNode);
		initialised = true;
	}

	/**
	 * This constructor will crash the system, this to make sure we do not use it
	 */
	public CommunicationThread() {
		System.err.println("|ERROR|You used the wrong construtor.");
		System.exit(1);
	}
	/**
	 * This method is used to produce and consume everything.
	 */
	public void run(){
		if (!initialised){
			throw new UnsupportedOperationException("The communicationThread was not initialised properly. Use the correct constructor next time!");
		}else{
			while(true){
				try {
					long[] command = commandQueue.take();//This will hold the program until there is at least 1 command in the queue
					if (command.length!=2){
						throw new ArrayIndexOutOfBoundsException("The command given is of incorrect format");
					}else{
						System.out.println("The thread with thread id " + command[0] + " has given the command to get data from address " +Long.toHexString(command[1]));
						//In this part the program should communicate with the sensors, for this I need Junior his code which is not available due to him not being here
						//For now I will just hold this method with a delay to simulate the communication and then send some random data back

						try {
							Thread.sleep(150);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String[] response =  new String[6];
						response[0] = Long.toString(command[0]);
						response[1] = Long.toHexString(command[1]);
						response[2] = Long.toString(System.currentTimeMillis());
						response[3] ="0";
						response[4] ="0";
						response[5] ="0";
						CommunicationThread.dataQueue.offer(response);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
