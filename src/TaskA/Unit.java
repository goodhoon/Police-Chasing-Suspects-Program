/*
 *Author : KHP
 */

package TaskA;


public abstract class Unit implements Runnable {
	
	protected Controller controller;
	protected int locationX;
	protected int locationY;

	
	public Unit(Controller control, String locX, String locY) {
		this.controller = control;
		this.locationX = Integer.parseInt(locX);
		this.locationY = Integer.parseInt(locY);
	}
	public Unit(Controller control, int locX, int locY) {
		controller = control;
		locationX = locX;
		locationY = locY;
	}
	
	public int getLocationX() { return locationX; }
	public int getLocationY() { return locationY; }
	public Point getPoint() { return new Point(locationX, locationY); }
	
	public void run() {
			synchronized (controller) {tick(); }
			controller.updateUnit(this);
	}
	
	protected abstract void tick();
	
}

