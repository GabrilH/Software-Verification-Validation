package sut;

public class Point2D {

	private int x, y;
	
	// public Point2D() {};  // switch constructors to use Ctor or Fields in class Point2DGeneratorTest
	
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public double distanceOrigin() {
		return Math.sqrt(x*x+y*y);
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
	
	public boolean equals(Object o) {
		return o instanceof Point2D && ((Point2D)o).x==x && ((Point2D)o).y==y;
	}
}
