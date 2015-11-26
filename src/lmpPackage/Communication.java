package lmpPackage;

import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;



public class Communication {
	
	
	//SINGLETON!!!
	public static Communication getInstance(){
		return CommunicationHolder.INSTANCE;
	}
	
	private static class CommunicationHolder{
		private static final Communication INSTANCE = new Communication();
	}
	
	
	I2CBus bus;
	public Communication() {
		try {
			bus = I2CFactory.getInstance(I2CBus.BUS_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DataSensor getSensorAxis(TypeSensor sensor,byte Adress) throws IOException{
		//implement some pattern to create more than a sensor
		switch (sensor) {
		case GYROSCOPE:
				Gyro gyro = new Gyro(bus, Adress);
			return gyro.readAxis();
		case ACCELEROMETER:
				Accel accel = new Accel(bus, Adress);
			return accel.readAxis();
		
		}
		return null;
	}
	
}
