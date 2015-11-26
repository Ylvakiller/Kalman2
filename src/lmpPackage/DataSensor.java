package lmpPackage;

public class DataSensor {

	
	protected TypeSensor name;
	protected double coordinateX;
	protected double coordinateY;
	protected double coordinateZ;
	protected byte adress;
	
	public DataSensor(TypeSensor name,byte adress) {
		this.name = name;
		this.adress = adress;
	
	}
	public TypeSensor getName() {
		return name;
	}



	public void setName(TypeSensor name) {
		this.name = name;
	}



	public double getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(double coordinateX) {
		this.coordinateX = coordinateX;
	}

	public double getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(double coordinateY) {
		this.coordinateY = coordinateY;
	}

	public double getCoordinateZ() {
		return coordinateZ;
	}

	public void setCoordinateZ(double coordinateZ) {
		this.coordinateZ = coordinateZ;
	}

	public byte getAdress() {
		return adress;
	}

	public void setAdress(byte adress) {
		this.adress = adress;
	}

}
