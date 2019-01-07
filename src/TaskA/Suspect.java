/*
 *Author : KHP
 */

package TaskA;

public class Suspect extends Person {
	protected static final int JAILED = 0;
	protected static final int CAUGHT = 1;
	protected static final int ASSIGNED	= 2;
	protected static final int UNASSIGNED = 3;
	protected static final String[] STATUS_ARRAY = {"Jailed", "Caught", "Assigned", "Unassigned"};

	public Suspect(Controller control, String id, String locX,
			       String locY, String status, String policeID) {
		super(control, id, locX, locY, statusStoI(STATUS_ARRAY, status), policeID);
	}
	
	public synchronized String toString() {
		return (id + "," + locationX + "," + locationY + "," + STATUS_ARRAY[status] + "," + (counterpartID != null ? counterpartID : ""));
	}
	
	public synchronized void tick() {}
	
}
