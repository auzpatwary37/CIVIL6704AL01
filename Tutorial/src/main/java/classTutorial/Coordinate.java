package classTutorial;

public class Coordinate {

	//These two variables are attributes of a object of type Coordinate
	private double X;
	private double Y;
	
	/**
	 * This is the constructor that creates an object of type Coordinate
	 * @param x
	 * @param y
	 */
	public Coordinate(double x, double y) {
		X = x;
		Y = y;
	}
	
	/**
	 * This is a method that returns X
	 * @return
	 */
	public double getX() {
		return X;
	}
	
	/**
	 * This is a method that returns Y
	 * @return
	 */
	public double getY() {
		return Y;
	}
	
	public static void main(String[] args) {
		
		Coordinate c = new Coordinate(1,1); //Here, the new keyword create the object
		
		Coordinate d = c; // this line do not create an object, rather assigns the same object that is in c to d. 
		
		
		System.out.println("The x value of d = "+ d.getX());
		
		
	}

}
