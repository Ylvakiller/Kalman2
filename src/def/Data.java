package def;
/**
 * This is simply a holding class, it will hold the data for all the sensors and return the next value
 * The return value of this class we can replace with the getting data function from the pi4j library
 * @author Ylva
 *
 */

public class Data {
	/**
	 * This variable will hold all the data, will need some getter and setter methods though
	 */
	private float[][] array;
	
	/**
	 * This variable is a count to see in which state the program is
	 */
	private int count;
	
	/**
	 * Default constructor, sets the count to 0 and runs the update method
	 */
	public Data(){
		this.update();
		count=0;
	}
	/**
	 * This is used to re read the array, needs to be ran before the rest of this class can operate
	 * This will also reset the count
	 */
	public void update(){
		array = ExcelReader.read();
	}
	/**
	 * This method will return the specified row of data
	 * @return an array of size 6 with the float values for the different datasets
	 */
	public float[] returnLatest(){
		float[] temp = new float[array[0].length];//creates a new array with the length of one row of the base array
		if (count>=20001){
			System.err.println("The count is out of bounds, cannot return row, stopping program.\nYou need to adapt your code");
			throw new ArrayIndexOutOfBoundsException();
			
		}
		for(int i = 0; i<temp.length;i++){
			temp[i]=array[count][i];
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
	public float[][] returnAll(){
		return array;
	}
}
