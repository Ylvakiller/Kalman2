package def;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This is simply a holding class, it will hold the data for all the sensors and return the next value
 * The return value of this class we can replace with the getting data function from the pi4j library
 * @author Ylva
 *
 */

public class Data {
	/**
	 * This variable will hold all the data, will need some getter and setter methods though
	 * This variable has 6 fields, gyro roll pitch and yaw and accelerometer data in the x y and z axis
	 */
	private float[][] dataArray;
	/**
	 * This variable will hold the filtered data, again it will needs getter and setter methods
	 * This variable has 3 fields, roll pitch and yaw
	 */
	private float[][] filteredArray;

	/**
	 * This variable is a count to see in which state the program is
	 */
	private int count;

	/**
	 * Default constructor, sets the count to 0 and runs the update method
	 */
	public Data(){
		this.update();
		filteredArray= new float[dataArray.length][3];
		count=0;
	}
	/**
	 * This is used to re read the array, needs to be ran before the rest of this class can operate
	 * This will also reset the count
	 */
	public void update(){
		dataArray = ExcelReader.read();

	}
	/**
	 * This method will return the specified row of data
	 * @return an array of size 6 with the float values for the different datasets
	 */
	public float[] getNextBaseValue(){
		float[] temp = new float[dataArray[0].length];//creates a new array with the length of one row of the base array
		if (count>=20001){
			System.err.println("The count is out of bounds, cannot return row, stopping program.\nYou need to adapt your code");
			throw new ArrayIndexOutOfBoundsException();

		}
		for(int i = 0; i<temp.length;i++){
			temp[i]=dataArray[count][i];
		}
		count++;
		return temp;
	}
	/**
	 * Will set the count back to zero
	 */
	public void resetCount(){
		count=0;
	}

	/**
	 * Returns all the data as it is stored
	 * @return all the data stored in this class
	 */
	public float[][] returnAllBase(){
		return dataArray;
	}
	/**
	 * Will return all the filtered data
	 * @return the array holding all the filtered data
	 */
	public float[][] returnAllFiltered(){
		return filteredArray;
	}

	/**
	 * With this method you can store data in the filteredDataArray
	 * @param number The location in the array to store the data
	 * @param roll The value of the current roll
	 * @param pitch The value of the current pitch
	 * @param yaw The value of the current yaw
	 */
	public void setFilteredData(int number, float roll, float pitch, float yaw){
		if (number<filteredArray.length&&number>=0){
			filteredArray[number][0]=roll;
			filteredArray[number][1]=pitch;
			filteredArray[number][2]=yaw;
		}else if(number<0){
			System.err.println("You entered a negative number.\nYou entered:" + number);
			throw new ArrayIndexOutOfBoundsException();
		}else{
			System.err.println("You tried to enter data into the filteredArray where there was not any space for data\nThe space you tried to enter data is:" + number + "\nThe array only has " + filteredArray.length + " places for data");
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * This method will return a specific row of filteredData
	 * @param row the row to send back
	 * @return the row of data to be returned
	 */
	public float[] getSpecificData(int row){
		float[] temp = new float[3];
		if (row>=20001){
			System.err.println("The number is out of bounds, cannot return row, stopping program.\nYou need to adapt your code");
			throw new ArrayIndexOutOfBoundsException();
		}else{
			for(int i = 0; i<temp.length;i++){
				temp[i]=filteredArray[count][i];
			}
		}
		return temp;
	}
	
	/**
	 * Will get the total size of the data stored
	 * @return dataArray.length
	 */
	public int length(){
		return dataArray.length;
	}
	
	/**
	 * Transforms the filtereddata array into a dataSet for the graphs
	 * @return the dataset with all the data
	 */
	public XYSeriesCollection createFilteredDataset(){

			final XYSeries fRoll = new XYSeries("Filtered Roll");

			final XYSeries fPitch = new XYSeries("Filtered Roll");
			final XYSeries fYaw = new XYSeries("Filtered Roll");

			int i=0;
			while(i<filteredArray.length){
				fRoll.add(i,filteredArray[i][0]);
				fPitch.add(i,filteredArray[i][1]);
				fYaw.add(i,filteredArray[i][2]);
				i++;
			}


			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(fRoll);
			dataset.addSeries(fPitch);
			dataset.addSeries(fYaw);


			return dataset;

	}
}
