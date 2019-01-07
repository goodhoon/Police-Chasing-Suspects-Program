/*
 *Author : KHP
 */

package TaskA;


public abstract class Facility extends Unit {
	
	protected int acceptableNum;
	protected int currentNum = 0;
	
	protected Facility(Controller control, int locX, int locY,
			           int acceptableNumber, int currentNumber) {
		super(control, locX, locY);
		
		this.acceptableNum = acceptableNumber;
		this.currentNum = currentNumber;
	}
	
	public synchronized boolean acceptable() {
		return currentNum < acceptableNum;
	}
	
	public synchronized int getAcceptableNum() {
		return acceptableNum;
	}
	
	public synchronized int getCurrentNumber() {
		return currentNum;
	}
	
	public synchronized boolean increase() {
		if(currentNum < acceptableNum) {
			currentNum += 1;
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized boolean decrease() {
		if(0 < currentNum) {
			currentNum -= 1;
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized void tick() {
	}
	
}
