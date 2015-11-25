package lmpPackage;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataThread {
	/**
	 * Will hold all the data
	 */
	private ArrayList<double[]> dataArray;
	/**
	 * Default constructor, added for convention sakes
	 */
	public DataThread(){
		dataArray = new ArrayList<double[]>();
	}
	
	/**
	 * Will add the given data to the end of the arrayList
	 * @param roll The pitch value to store
	 * @param pitch The roll value to store
	 * @param yaw The yaw value to store
	 */
	public void addData(double roll, double pitch, double yaw){
		double[] temp = new double[3];
		temp[0] = roll;
		temp[1] = pitch;
		temp[2] = yaw;
		dataArray.add(temp);
	}
	
	/**
	 * Will return the specified line of data, will check if the line excists
	 * @param i The index to return
	 * @return The float array stored at i
	 */
	public double[] getData(int i){
		if(dataArray.size()<=i){
			System.err.println("|ERROR|Cannot return a value that is not stored in the array.\nYou requested value "+ i + " and the array only has "+ dataArray.size() + " values");
			throw new ArrayIndexOutOfBoundsException();
		}else{
			return dataArray.get(i);
		}
	}
	
	/**
	 * Get the most recent filtered data
	 * @return the last data in the object
	 */
	public double[] getLastData(){
		return dataArray.get(dataArray.size()-1);
	}
	/**
	 * Getter for the arrayList if needed
	 * @return The arrayList which holds all the data
	 */
	public ArrayList<double[]> getDataArray(){
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
			double[] temp = dataArray.get(i);
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
	/**
	 * Will print the contents of the dataArray to the default outputstream
	 * Used for debugging
	 */
	public void print(){
		int i =0;
		while(i<dataArray.size()){
			System.out.println(this.getData(i)[0]);
			i++;
		}
	}
}
