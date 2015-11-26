package lmpPackage;
import java.io.IOException;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;


public class Gyro {

	   private I2CDevice device;
	   private static final byte REG_POWER_CTL = 0x20;   
	   private DataSensor gyro;
	   
	   public Gyro(I2CBus bus, byte adress){
	      try {
			device = bus.getDevice(adress);
			device.write(REG_POWER_CTL, (byte)0x0f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	      
	      gyro = new DataSensor(TypeSensor.GYROSCOPE,adress);
	   }
	   
	   public DataSensor readAxis() throws IOException{

		   int xMSB = device.read(0x29);
	        int xLSB = device.read(0x28);
	        int x = twoBytesToInt(xMSB, xLSB);
	        gyro.setCoordinateX((double)x/250);
	        
	        int yMSB = device.read(0x2B);
	        int yLSB = device.read(0x2A);
	        int y = twoBytesToInt(yMSB, yLSB);
	        gyro.setCoordinateY((double)y/250);
	        
	        int zMSB = device.read(0x2D);
	        int zLSB = device.read(0x2C);
	        int z = twoBytesToInt(zMSB, zLSB);
	        gyro.setCoordinateZ((double)z/250);
	
//		      System.out.printf("\tGx -> %.6f", gyro.getX());
//		      System.out.printf("\tGy -> %.6f", gyro.getY());
//		      System.out.printf("\tGz -> %.6f \n",gyro.getZ());
		 		   
		return gyro;
	   }
	   
	    private int twoBytesToInt(int msb, int lsb) {
	        if (msb > 127) {
	            msb = msb - 256;
	        }
	        if (lsb < 0) {
	            lsb = lsb + 256;
	        }
	        return msb * 256 + lsb;
	    }
}
