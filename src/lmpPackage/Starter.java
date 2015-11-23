package lmpPackage;

public class Starter {

	public static void main(String[] args) {
		System.out.println("test");
		CommunicationThread com =  new CommunicationThread(1);
		SensorThread sensor1 = new SensorThread((byte) 0x34, (byte)0x44);
		
		com.start();
		
		sensor1.start();
		sensor1.dataThread.print();
	}

}
