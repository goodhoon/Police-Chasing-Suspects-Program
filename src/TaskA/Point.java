/*
 *Author : KHP
 */
 
package TaskA;


public class Point {
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }

	public int getY() {	return y; }
	
	public synchronized double getClosestPoint(Point dest) {
		return Math.sqrt(Math.pow((dest.x - this.x), 2) + Math.pow((dest.y - this.y), 2));}
	
	public synchronized String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
}
