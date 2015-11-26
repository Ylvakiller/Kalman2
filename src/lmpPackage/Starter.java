package lmpPackage;

public class Starter {

	public static void main(String[] args) {
		long runtime = System.currentTimeMillis();
		System.out.println("test");
		CommunicationThread com =  new CommunicationThread(1);
		SensorThread sensor1 = new SensorThread((byte) 0x53, (byte)0x69);
		SensorThread sensor2 = new SensorThread((byte) 0x57, (byte)0x6d);
		com.start();
		
		sensor1.start();
		sensor2.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {//For some reason if I leave out this array it will send the second address twice
			e.printStackTrace();//I have not been able to figure out why this happens and I will follow up as soon as I have an answer
		}
		System.out.println("Amount of values filter 1: " + sensor1.getAmount());
		System.out.println("Amount of values filter 2: " + sensor2.getAmount());
		
		System.out.println("runtime = " + (System.currentTimeMillis()-runtime));
		System.exit(0);
		//sensor1.dataThread.print();
	}

}
