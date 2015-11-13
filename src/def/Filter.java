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
		
		Data data = new Data();
		int kMax = data.length();
		for(int i = 0; i<kMax;i++){
			float[] newDat=data.getNextBaseValue();
			float p = newDat[0];
			float q = newDat[0];
			float r = newDat[0];
			double[][] A={
					{1,-p,-q,-r},
					{p,1,r,-q},
					{q,-r,1,p},
					{r,q,-p,1}
			};
			/*
			 * var A;
					var p = measVals[0][k];
					var q = measVals[1][k];
					var r = measVals[2][k];
					A = Matrix.I(4).add($M([
						[0,-p,-q,-r],
						[p, 0, r,-q],
						[q,-r, 0, p],
						[r, q,-p, 0]
					]).x(T/2));
					var g = 9.81;
					var θ = Math.asin(Math.radians(measVals[3][k])/g);
					var φ = Math.asin(-Math.radians(measVals[4][k]/g*Math.cos(θ)));
					var ω = 0;
					k++;
					z[k] = quaternion(ω, θ, φ);
					var eXk = A.x(Xh[k-1]);
					var ePk = A.x(P[k-1]).x(A.transpose()).add(Q); 
					K[k]	= ePk.x(H.transpose()).x( (H.x(ePk).x(H.transpose()).add(R) ).inverse() );

					Xh[k]	= eXk.add( K[k].x( z[k].subtract(H.x(eXk)) ) ); //1 by 4 matrix.

					P[k]	= ePk.subtract( K[k].x(H).x(ePk));
					filteredData[k-1] = toEuler(Xh[k].elements[0][0], Xh[k].elements[1][0], Xh[k].elements[2][0], Xh[k].elements[3][0]);
			 */
		}
	}
}
