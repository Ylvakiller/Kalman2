package lmpPackage;

public class Starter {

	public static void main(String[] args) {
		System.out.println("test");
		CommunicationThread com =  new CommunicationThread(1);
		SensorThread sensor1 = new SensorThread((byte) 0x34, (byte)0x44);
		SensorThread sensor2 = new SensorThread((byte) 0x22, (byte)0x69);
		com.start();
		
		sensor1.start();
		sensor2.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {//For some reason if I leave out this array it will send the second address twice
			e.printStackTrace();//I have not been able to figure out why this happens and I will follow up as soon as I have an answer
		}
		System.exit(0);
		//sensor1.dataThread.print();
	}

}
