package def;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This class will read the data from the supplied excel file for the second kalman filter assignment
 * @author Ylva
 *
 */
public class ExcelReader {
	/**
	 * This method will read in the data from the csv to a String format
	 * @return returns an array of 20001 values, in 6 columns
	 */
 public static float[][] read(){
	 Scanner scanner;
	 float[][] array =  new float[20001][6];
	try {
		int x = 0;
		int y=0;
		scanner = new Scanner(new File("./data/KF Assignment 2 data.csv"));
		while(scanner.hasNextLine()){
			y=0;
			String line = scanner.nextLine();
			String[] fields = line.split(",");
			while(y<=5){
				array[x][y]=Float.parseFloat(fields[y]);
				y++;
			}
			x++;
		}
	     scanner.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return array;
 }
}

