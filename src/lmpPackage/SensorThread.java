package lmpPackage;
/**
 * This class will do the filtering, will use an adapted version of the data class in the def package
 * @author Ylva
 *
 */
public class SensorThread extends Thread {

	/**
	 * This is the address as stored for the accelerometer on this sensor node
	 */
	private int axlAddress;

	/**
	 * This is the address as stored for the gyroscope on this sensor node
	 */
	private int gyrAddress;
	/**
	 * This is the object of the current thread in which the data is stored
	 */
	protected DataThread dataThread;
	/**
	 * This is the constructor to use, sets the addresses of the sensors on this node
	 * Creates a new data holder
	 * @param addressAxl The address of the accelerometer
	 * @param AddressGyr The address of the gyroscope
	 */
	public SensorThread(byte addressAxl, byte AddressGyr) {
		super();
		axlAddress = addressAxl;
		gyrAddress = AddressGyr;
		dataThread= new DataThread();
	}

	/**
	 * This constructor will crash the system, this to make sure we do not use it
	 */
	public SensorThread() {
		System.err.println("|ERROR|You used the wrong construtor.");
		System.exit(1);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 */
	public SensorThread(Runnable arg0) {
		super(arg0);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 */
	public SensorThread(String arg0) {
		super(arg0);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 * @param arg1 Second parameter of super class
	 */
	public SensorThread(ThreadGroup arg0, Runnable arg1) {
		super(arg0, arg1);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 * @param arg1 Second parameter of super class
	 */
	public SensorThread(ThreadGroup arg0, String arg1) {
		super(arg0, arg1);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 * @param arg1 Second parameter of super class
	 */
	public SensorThread(Runnable arg0, String arg1) {
		super(arg0, arg1);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 * @param arg1 Second parameter of super class
	 * @param arg2 Third parameter of super class
	 */
	public SensorThread(ThreadGroup arg0, Runnable arg1, String arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * AutoGenerated constructor, not necessary but keeping it in in case we need it
	 * @param arg0 First parameter of super class
	 * @param arg1 Second parameter of super class
	 * @param arg2 Third parameter of super class
	 * @param arg3 Fourth parameter of super class
	 */
	public SensorThread(ThreadGroup arg0, Runnable arg1, String arg2, long arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	/**
	 * This method will manage what this thread needs to do, we will divide this thread in different submethods, for example the one 
	 */
	public void run(){
		if(axlAddress==0||gyrAddress==0){
			System.err.println("|Error|One or more of the address are not set, please set these using the constructor");//This makes sure that there are addresses in the correct place
		}else{
			System.out.println(Integer.toHexString(axlAddress));

			System.out.println(Integer.toHexString(gyrAddress));
			//Start by requesting new data from each sensor
			long[] temp = new long[2];
			temp[0] = currentThread().getId();
			temp[1] = gyrAddress;
			System.out.println("Attempting to send address " + Long.toHexString(temp[1]));
			System.out.println(CommunicationThread.commandQueue.offer(temp));

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {//For some reason if I leave out this array it will send the second address twice
				e.printStackTrace();//I have not been able to figure out why this happens and I will follow up as soon as I have an answer
			}
			temp[1] = axlAddress;

			System.out.println("Attempting to send address " + Long.toHexString(temp[1]));
			System.out.println(CommunicationThread.commandQueue.offer(temp));
			//New Data has been requested, time to see if it has the old data already.
			String[] data;
			int i=0;
			while(i!=2){
				data = CommunicationThread.dataQueue.peek();
				if (data==null){
					//This is just to check if there is something in the dataQueue
				}else {
					//System.out.println(data[0] + "" +Integer.valueOf((data[0])));
					if (Long.valueOf((data[0]))==currentThread().getId()){
						//System.out.println("Response with id " + data[0] + " from sensor addresss " +data[1] + " was recieved at time " + data[2]);
						try {
							data = CommunicationThread.dataQueue.take();//Remove the data so that the next thread can read their data from the HEAD
							System.out.println("Response with id " + data[0] + " from sensor addresss " +data[1] + " was recieved at time " + data[2]);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						i++;
					}
				}
			}
			//Right here we have asked for new data, gotten the old data and can start processing this old data


			double[] dummy= new double[3];//dummy array, made for the sake of testing the DataThread class
			i =0;
			while(i<10){
				System.out.println(i);
				dummy[0] = Math.random();
				dummy[1] = Math.random();
				dummy[2] = Math.random();
				dataThread.addData(dummy[0], dummy[1], dummy[2]);
				i++;
			}

			//data.print();
			//The first time it runs it needs to run differently, therefore I will use a while method for all but the first runthough

		}
	}

}
