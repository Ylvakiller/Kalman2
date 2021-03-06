package lmpPackage;

import org.ejml.simple.SimpleMatrix;


/**
 * This class will do the filtering, will use an adapted version of the data class in the def package
 * @author Ylva
 *
 */
public class SensorThread extends Thread {

	/**
	 * Used to let the threads sleep if they do not have anything to do but restart as soon as it is possible to do something again
	 */
	private static Object notifier = new Object();
	
	/**
	 * An object on which the data is stored
	 */
	private DataThread store;
	/**
	 * This is the address as stored for the accelerometer on this sensor node
	 */
	private int axlAddress;

	/**
	 * This is the address as stored for the gyroscope on this sensor node
	 */
	private int gyrAddress;
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
		store = new DataThread();
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
	 * This is the running of the sensor thread. 
	 * The thread starts with initialising base values needed for the filter.
	 * 		This means things like X0 and similar things
	 * It will then go into its infinite loop
	 * In this loop it will run the following procedure:
	 * 1)Request new sensor data from both sensors
	 * 2)Look at the head of the return queue and check if the head is data for this thread
	 * 3)If the data is for this thread, take it out and continue, otherwise back to step 2
	 * 4)Divide the data up in gyroscope and accelerometer data
	 * 5)Do one iteration of the filter
	 * 6)Store the output
	 * 7)Start at the start of the loop
	 * 
	 */
	public void run(){
		/*
		 * We start of initialising variables that are needed for the filter
		 */
		SimpleMatrix H = SimpleMatrix.identity(4);
		double[][] xHatDat={
				{1},
				{0},
				{0},
				{0}
		};
		SimpleMatrix xHat = new SimpleMatrix(xHatDat);
		/*
		SimpleMatrix[] P;
		P[0] = SimpleMatrix.identity(4);*/
		//Looking at the above code we run into an error when we have an array of P matrices
		//What we can do however is just use P and Pold for P[k] and P[k-1] we will also have Pest for the estimate of P[k]
		//Something that we need to take into account is that the math starts at K=1 and uses a k0 and p0 location
		//We however do not have this since we take data from our array which starts at 0, care need to be taken to ensure we get the correct data
		SimpleMatrix P0 = SimpleMatrix.identity(4);
		SimpleMatrix pEst = SimpleMatrix.identity(4);
		SimpleMatrix pOld = SimpleMatrix.identity(4);
		SimpleMatrix P = SimpleMatrix.identity(4);

		double[][] xHat0Dat = {
				{1},
				{0},
				{0},
				{0}
		};
		SimpleMatrix xHatOld = new SimpleMatrix(xHat0Dat);
		double pIntValue = 0;
		
		
		long[] commandGyro = new long[3];
		commandGyro[0] = currentThread().getId();
		commandGyro[1] = gyrAddress;
		commandGyro[2] = 0;

		CommunicationThread.commandQueue.offer(commandGyro);


		long[] commandAxl = new long[3];
		commandAxl[0] = currentThread().getId();
		commandAxl[1] = axlAddress;
		commandAxl[2] = 1;
		CommunicationThread.commandQueue.offer(commandAxl);
		long oldTime = System.currentTimeMillis();
		while(true){
			if(axlAddress==0||gyrAddress==0){
				System.err.println("|Error|One or more of the address are not set, please set these using the constructor");//This makes sure that there are addresses in the correct place
			}else{
				//Start by requesting new data from each sensor
				CommunicationThread.commandQueue.offer(commandGyro);
				CommunicationThread.commandQueue.offer(commandAxl);
				//New Data has been requested, time to see if it has the old data already.
				String[] data;
				float[] gyroDat = new float[3];
				float[] accelDat = new float[3];
				boolean gotAccel = false, gotGyro = false;
				long timeAccel = 0,timeGyro = 0;
				while(!gotAccel||!gotGyro){
					data = CommunicationThread.dataQueue.peek();
					if (data==null){
						synchronized (notifier) {
							try {
								notifier.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}else {
						
						//System.out.println(data[0] + "" +Integer.valueOf((data[0])));
						if (Long.valueOf((data[0]))==currentThread().getId()){
							//System.out.println("Response with id " + data[0] + " from sensor addresss " +data[1] + " was recieved at time " + data[2]);
							try {
								data = CommunicationThread.dataQueue.take();//Remove the data so that the next thread can read their data from the HEAD
								synchronized (notifier) {
									notifier.notifyAll();
							    }

							} catch (InterruptedException e) {
								e.printStackTrace();
							}//In here it will have recieved one set of data, will need to run it again for the next set

							byte temp = Byte.parseByte(data[1], 16);//Converting the Hex String back to an int to check for the address
							if(temp==gyrAddress){
								//received data on gyroscope
								gyroDat[0] = Float.parseFloat(data[3]);
								gyroDat[1] = Float.parseFloat(data[4]);
								gyroDat[2] = Float.parseFloat(data[5]);
								timeGyro = Long.parseLong(data[2]);
								gotGyro=true;
							}else if (temp==axlAddress){
								//received data on accelerometer
								accelDat[0] = Float.parseFloat(data[3]);
								accelDat[1] = Float.parseFloat(data[4]);
								accelDat[2] = Float.parseFloat(data[5]);
								timeAccel = Long.parseLong(data[2]);
								gotAccel = true;
							}else{
								//In this case the thread id was correct but the addresses where not
								System.err.println("Something went wrong.\nThe communicationThread returned values for an address that this thread does not have.");
								throw new IllegalThreadStateException("Data got back for a thread which could not use that data"); 
							}
						}else{
							synchronized (notifier) {
								try {
									notifier.notify();;//This will make sure that before this thread goes to sleep it will wake up the next thread
									notifier.wait();
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
						    }
						}
					}
				}
				
				long deltaTime = (timeAccel+timeGyro)/2-oldTime;
				//Right here we have asked for new data, gotten the old data and can start processing this old data




				//The first time it runs it needs to run differently, therefore I will use a while method for all but the first runthough

				double pValue = gyroDat[0];
				double qValue = gyroDat[1];
				double rValue = gyroDat[2];
				double[][] aDat={
						{0,-pValue,-qValue,-rValue},
						{pValue,0,rValue,-qValue},
						{qValue,-rValue,0,pValue},
						{rValue,qValue,-pValue,0}
				};

				SimpleMatrix a = SimpleMatrix.identity(4).plus((new SimpleMatrix(aDat).scale((deltaTime*1000)/2)));

				double[][] qDat = {
						{1E-4,0,0,0},
						{0,1E-4,0,0},
						{0,0,1E-4,0},	
						{0,0,0,1E-4}
				};
				double[][] rDat = {
						{10,0,0,0},
						{0,10,0,0},
						{0,0,10,0},	
						{0,0,0,10}
				};
				SimpleMatrix rBig =new SimpleMatrix(rDat);

				SimpleMatrix qBig =new SimpleMatrix(qDat);

				double g = 9.807;
				double theta = (Math.asin(((accelDat[0]))/g)); 
				double phi = (Math.asin((-(accelDat[1]))/(g*Math.cos(theta))));
				double omega = 0;
				SimpleMatrix Z = SensorThread.toQuaternion(theta,phi,omega);
				SimpleMatrix xHatEstimate = a.mult(xHatOld);
				pEst = a.mult(pOld).mult(a.transpose()).plus(qBig);
				SimpleMatrix K = pEst.mult(H.transpose()).mult((H.mult(pEst).mult(H.transpose()).plus(rBig)).invert());
				xHat = xHatEstimate.plus(K.mult((Z.minus(H.mult(xHatEstimate)))));
				P = pEst.minus(K.mult(H).mult(pEst));
				pOld=P;
				xHatOld = xHat;
				this.storeData(xHat, store);//output data
				
			}
		}
	}

	/**
	 * This method converts euler angles to quaternion values
	 * @param Theta The given euler roll
	 * @param Phi The given euler pitch
	 * @param Omega The given euler yaw
	 * @return A matrix with the quaternion data
	 */
	private static SimpleMatrix toQuaternion(double theta, double phi, double omega){
		theta = theta/2;//Since the formula for the conversion between euler and quaternion only uses these values devided by 2 this is easier to do
		phi = phi/2;
		omega = omega/2;
		double[][] tempDat={	//quaternion data
				{Math.cos(phi)*Math.cos(theta)*Math.cos(omega)+Math.sin(phi)*Math.sin(theta)*Math.sin(omega)},
				{Math.sin(phi)*Math.cos(theta)*Math.cos(omega)-Math.cos(phi)*Math.sin(theta)*Math.sin(omega)},
				{Math.cos(phi)*Math.sin(theta)*Math.cos(omega)+Math.sin(phi)*Math.cos(theta)*Math.sin(omega)},
				{Math.cos(phi)*Math.cos(theta)*Math.sin(omega)-Math.sin(phi)*Math.sin(theta)*Math.cos(omega)}
		};
		SimpleMatrix Z = new SimpleMatrix(tempDat);
		return Z;
	}

	/**
	 * Will store the data, converts from quaternion to euler and puts it in the data array
	 * @param quat The matrix with the quaternions to store
	 * @param data The data object in which to store the data
	 */
	private void storeData(SimpleMatrix quat, DataThread store){
		double q0 = quat.get(0, 0);
		double q1 = quat.get(1, 0);
		double q2 = quat.get(2, 0);
		double q3 = quat.get(3, 0);

		double roll = (Math.atan2(2*(q0*q1+q2*q3), (1-2*(q1*q1+q2*q2))));

		double pitch = (Math.asin(2*(q0*q2-q1*q3)));

		double yaw = (Math.atan2(2*(q0*q3+q1*q2), (1-2*(q2*q2+q3*q3))));
		store.addData((float)Math.toDegrees(roll), (float)Math.toDegrees(pitch), (float)Math.toDegrees(yaw));
	}

	/**
	 * Returns what is currently stored
	 * @return the object that holds all the data
	 */
	public synchronized DataThread getStore() {
		return store;
	}
	
	/**
	 * Get the last stored data
	 * @return A double array with the last values in it
	 */
	public synchronized double[] getLast() {
		return store.getLastData();
	}
	
	/**
	 * Get the last stored data
	 * @return A double array with the last values in it
	 */
	public synchronized int getAmount() {
		return store.getSize();
	}
	
//	public static synchronized void waiter(){
//		try {
//			Starter.Syncer.wait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
}
