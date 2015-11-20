package lmpPackage;

import java.util.ArrayList;

public class DataThread {
	/**
	 * Will hold all the data
	 */
	private ArrayList<float[]> dataArray;
	/**
	 * Default constructor, added for convention sakes
	 */
	public DataThread(){
		
	}
	
	/**
	 * Will add the given data to the end of the arrayList
	 * @param pitch The pitch value to store
	 * @param roll The roll value to store
	 * @param yaw The yaw value to store
	 */
	public void addData(float pitch, float roll, float yaw){
		float[] temp = new float[3];
		temp[0] = pitch;
		temp[1] = roll;
		temp[2] = yaw;
		dataArray.add(temp);
	}
	
	
}
