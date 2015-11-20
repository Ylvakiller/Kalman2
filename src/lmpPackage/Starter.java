package lmpPackage;

public class Starter {

	public static void main(String[] args) {
		System.out.println("test");
		SensorThread sensor1 = new SensorThread((byte) 0x34, (byte)0x44);
		sensor1.run();
		sensor1.data.print();
	}

}
