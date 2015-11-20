package lmpPackage;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
	
	/**
	 * Will return the specified line of data, will check if the line excists
	 * @param i The index to return
	 * @return The float array stored at i
	 */
	public float[] getData(int i){
		if(dataArray.size()>i){
			System.err.println("|ERROR|Cannot return a value that is not stored in the array.\nYou requested value "+ i + " and the array only has "+ dataArray.size() + " values");
			throw new ArrayIndexOutOfBoundsException();
		}else{
			return dataArray.get(i);
		}
	}
	/**
	 * Getter for the arrayList if needed
	 * @return The arrayList which holds all the data
	 */
	public ArrayList<float[]> getDataArray(){
		return dataArray;
	}
	/**
	 * Will convert the arrayList to a dataSet to be used by a graph
	 * @return The created DataSet
	 */
	public XYSeriesCollection getDataSet(){
		XYSeries fRoll = new XYSeries("Filtered Roll");

		XYSeries fPitch = new XYSeries("Filtered Pitch");
		XYSeries fYaw = new XYSeries("Filtered Yaw");

		int i=0;
		while(i<dataArray.size()){
			float[] temp = dataArray.get(i);
			fRoll.add(i,temp[0]);
			fPitch.add(i,temp[1]);
			fYaw.add(i,temp[2]);
			i++;
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(fRoll);
		dataset.addSeries(fPitch);
		dataset.addSeries(fYaw);


		return dataset;
	}
}
