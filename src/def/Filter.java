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
	/**
	 * Kalman Fusion FIlter
	 */
	public void KFF(){
		/*double[][] rData = { 
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1}};*/
		//SimpleMatrix R = new SimpleMatrix(rData);
		SimpleMatrix H = SimpleMatrix.identity(4);
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
		SimpleMatrix pEst = SimpleMatrix.identity(4);
		SimpleMatrix pOld = SimpleMatrix.identity(4);
		SimpleMatrix P = SimpleMatrix.identity(4);
		
		//System.out.println(P);
		double[][] xHat0Dat = {
				{1},
				{0},
				{0},
				{0}
		};
		SimpleMatrix xHatOld = new SimpleMatrix(xHat0Dat);
		Data data = new Data();
		int kMax = data.length();
		int i = 0;
		while( i<kMax){
			float[] newDat=data.getNextBaseValue();
			float pValue = newDat[0];
			float qValue = newDat[1];
			float rValue = newDat[2];
			double[][] aDat={
					{1,-pValue,-qValue,-rValue},
					{pValue,1,rValue,-qValue},
					{qValue,-rValue,1,pValue},
					{rValue,qValue,-pValue,1}
			};
			
			SimpleMatrix a = new SimpleMatrix(aDat);
			double[][] qDat = {
					{1,0,0,0},
					{0,1,0,0},
					{0,0,1,0},	
					{0,0,0,1}
			};
			SimpleMatrix qBig =new SimpleMatrix(qDat);
			double[][] rDat = {
					{1,0,0,0},
					{0,1,0,0},
					{0,0,1,0},	
					{0,0,0,1}
			};
			SimpleMatrix rBig =new SimpleMatrix(rDat);
			double T = 0.01;
			a = a.scale(T/2);
			double g = 9.81;
			double theta = 1/(Math.sin(Math.toRadians(newDat[3])/g));
			double phi = 1/(Math.sin(-Math.toRadians(newDat[4])/(g*Math.cos(theta))));//different from randy's code, however mathematically correct this time
			double omega = 0;//It appears that randy had a small mistake in his math, this code is the correct code according to the doc
			SimpleMatrix Z = this.toQuaternion(theta,phi,omega);
			SimpleMatrix xHatEstimate = a.mult(xHatOld);
			pEst = a.mult(pOld).mult(a.transpose()).plus(qBig);
			SimpleMatrix K = pEst.mult(H.transpose()).mult((H.mult(pEst).mult(H.transpose()).plus(rBig)).invert());
			xHat = xHatEstimate.plus(K.mult(Z.minus(H.mult(xHatEstimate))));
			P = pEst.minus(K.mult(H).mult(pEst));
			pOld=P;
			xHatOld = xHat;
			this.storeData(i, xHat, data);
			i++;
		}
		System.out.println("Filtered everything");
	}
	
	/**
	 * This method converts euler angles to quaternion values
	 * @param theta The given euler roll
	 * @param phi The given euler pitch
	 * @param omega The given euler yaw
	 * @return A matrix with the quaternion data
	 */
	private SimpleMatrix toQuaternion(double theta, double phi, double omega){
		theta = theta/2;//Since the formula for the conversion between euler and quaternion only uses these values devided by 2 this is easier to do
		phi = phi/2;
		omega = omega/2;
		double[][] tempDat={	//quaternion data
				{Math.cos(phi)*Math.cos(theta)*Math.cos(omega)+Math.sin(phi)*Math.sin(theta)*Math.sin(omega)},
				{Math.sin(phi)*Math.cos(theta)*Math.cos(omega)+Math.cos(phi)*Math.sin(theta)*Math.sin(omega)},
				{Math.cos(phi)*Math.sin(theta)*Math.cos(omega)+Math.sin(phi)*Math.cos(theta)*Math.sin(omega)},
				{Math.cos(phi)*Math.cos(theta)*Math.sin(omega)+Math.sin(phi)*Math.sin(theta)*Math.cos(omega)}
		};
		SimpleMatrix Z = new SimpleMatrix(tempDat);
		return Z;
	}
	
	private void storeData(int i, SimpleMatrix quat, Data data){
		double q0 = quat.get(0, 0);
		double q1 = quat.get(1, 0);
		double q2 = quat.get(2, 0);
		double q3 = quat.get(3, 0);
		double roll = Math.atan2(2*(q0*q1+q2*q3), (1-2*(q1*q1+q2*q2)));

		double pitch = Math.asin(2*(q0*q2-q1*q3));

		double yaw = Math.atan2(2*(q0*q3+q1*q2), (1-2*(q2*q2+q3*q3)));
		data.setFilteredData(i, (float)roll,  (float)pitch,  (float)yaw);
		/*
		 * var array = [];
				array[0] = Math.degrees(Math.atan2(2*(q0*q1+q2*q3),1-2*(q1*q1+q2*q2)));
				array[1] = Math.degrees(Math.asin(2*(q0*q2-q3*q1)));
				array[2] = Math.degrees(Math.atan2(2*(q0*q3+q1*q2),1-2*(q2*q2+q3*q3)));
		 */
	}
}
