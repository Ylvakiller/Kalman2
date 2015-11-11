package def;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * This class will hold all the functionality for the filter
 * This class will need to be run from a runner (and as such can be quickly put into a treaded program)
 * @author Ylva
 *
 */
public class Filter {
	/*
	 * steps we need to go to
	 * 1, get new data
	 * make new matrix A with gyro data
	 * Calculate Xhat and P estimates
	 * Compute Kalman gain
	 * convert acc to euler anges 
	 */
	public static void test(){
		double[][] matrixData = { {1,2,3}, {2,5,3}};
		RealMatrix m = MatrixUtils.createRealMatrix(matrixData);
	}
}
