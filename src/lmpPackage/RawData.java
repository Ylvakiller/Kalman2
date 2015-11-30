package lmpPackage;

import java.util.ArrayList;

/**
 * This class will hold raw data for one node, that means gyro and accelerometer data
 * This data is just as we get it from the sensor
 * @author Ylva
 *
 */
public class RawData {
	/**
	 * Holder for the gyroscope data.
	 * Will have doubles of size 3 in the following configuration:
	 * [0] = roll
	 * [1] = pitch
	 * [2] = yaw
	 */
	private ArrayList<double[]> gyroData;

	/**
	 * Holder for the accelerometer data.
	 * Will have doubles of size 3 in the following configuration:
	 * [0] = X
	 * [1] = Y
	 * [2] = Z
	 */
	private ArrayList<double[]> accelData;
	
	/**
	 * Constructor to create the raw values
	 */
	public RawData(){
		gyroData = new ArrayList<double[]>();
		accelData = new ArrayList<double[]>();
	}
	/**
	 * Add a next set of values to the Gyroscope data
	 * @param roll The change in roll
	 * @param pitch The change in pitch
	 * @param yaw The change in yaw
	 */
	public void addGyro(double roll, double pitch, double yaw){
		double[] temp = new double[3];
		temp[0] = roll;
		temp[1] = pitch;
		temp[2] = yaw;
		gyroData.add(temp);
	}
	/**
	 * Add a next set of values to the accelerometer data
	 * @param x The acceleration along the X axis
	 * @param y The acceleration along the Y axis
	 * @param z The acceleration along the Z axis
	 */
	public void addAccel(double x, double y, double z){
		double[] temp = new double[3];
		temp[0] = x;
		temp[1] = y;
		temp[2] = z;
		accelData.add(temp);
	}
}
