package lmpPackage;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
	
	/**
	 * Will return the specified line of data, will check if the line exists
	 * @param i The index to return
	 * @return The float array stored at i
	 */
	public double[] getGyroData(int i){
		if(gyroData.size()<=i){
			System.err.println("|ERROR|Cannot return a value that is not stored in the array.\nYou requested value "+ i + " and the array only has "+ gyroData.size() + " values");
			throw new ArrayIndexOutOfBoundsException();
		}else{
			return gyroData.get(i);
		}
	}
	
	/**
	 * Will return the specified line of data, will check if the line exists
	 * @param i The index to return
	 * @return The float array stored at i
	 */
	public double[] getAccellData(int i){
		if(accelData.size()<=i){
			System.err.println("|ERROR|Cannot return a value that is not stored in the array.\nYou requested value "+ i + " and the array only has "+ accelData.size() + " values");
			throw new ArrayIndexOutOfBoundsException();
		}else{
			return accelData.get(i);
		}
	}
	
	/**
	 * Will create one big dataset of all the values
	 * @return The created DataSet
	 */
	public XYSeriesCollection getDataSetAll(){
		XYSeries roll = new XYSeries("Raw Roll");
		XYSeries pitch = new XYSeries("Raw Pitch");
		XYSeries yaw = new XYSeries("Raw Yaw");

		XYSeries X = new XYSeries("Raw X");
		XYSeries Y = new XYSeries("Raw Y");
		XYSeries Z = new XYSeries("Raw Z");

		int i=0;
		while(i<gyroData.size()&&i<accelData.size()){
			double[] temp = gyroData.get(i);
			roll.add(i,temp[0]);
			pitch.add(i,temp[1]);
			yaw.add(i,temp[2]);
			temp = accelData.get(i);
			X.add(i,temp[0]);
			Y.add(i,temp[1]);
			Z.add(i,temp[2]);
			i++;
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(roll);
		dataset.addSeries(pitch);
		dataset.addSeries(yaw);
		dataset.addSeries(X);
		dataset.addSeries(Y);
		dataset.addSeries(Z);


		return dataset;
	}
}
