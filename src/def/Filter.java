package def;

import org.ejml.simple.SimpleMatrix;

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
		/*double[][] rData = { 
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1}};*/
		//SimpleMatrix R = new SimpleMatrix(rData);
		SimpleMatrix H = SimpleMatrix.identity(4);
		SimpleMatrix R = SimpleMatrix.identity(4);
		SimpleMatrix Q = SimpleMatrix.identity(4);
		double[][] xHatDat={
				{1},
				{0},
				{0},
				{0}
		};
		SimpleMatrix xHat = new SimpleMatrix(xHatDat);
		/*
		SimpleMatrix[] P;
		P[0] = SimpleMatrix.identity(4);*/
		//Looking at the above code we run into an error when we have an array of P matrices
		//What we can do however is just use P and Pold for P[k] and P[k-1] we will also have Pest for the estimate of P[k]
		//Something that we need to take into account is that the math starts at K=1 and uses a k0 and p0 location
		//We however do not have this since we take data from our array which starts at 0, care need to be taken to ensure we get the correct data
		SimpleMatrix P0 = SimpleMatrix.identity(4);
		SimpleMatrix Pest = SimpleMatrix.identity(4);
		SimpleMatrix Pold = SimpleMatrix.identity(4);
		SimpleMatrix P = SimpleMatrix.identity(4);
		System.out.println(P);
	}
}
