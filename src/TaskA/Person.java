/*
 *Author : KHP
 */

package TaskA;


public abstract class Person extends Unit {
	
	protected String id;
	protected int status = 0;
	protected String counterpartID;
	
	
	protected Person(Controller control, String personID, String locX,
			         String locY, int state, String counterId) {
		super(control, locX, locY);
		id = personID;
		status = state;
		counterpartID = counterId;
	}

	
	public synchronized int getStatus() {
		return status;
	}
	
	public synchronized void setStatus(int state) {
		status = state;
	}

	public synchronized String getID() {
		return id;
	}
	
	public synchronized String counterpartID() {
		return counterpartID;
	}
	
	public synchronized static int statusStoI(String[] statusList, String status) {
		for(int index = 0 ; index < statusList.length ; index++) {
			if(status.equals(statusList[index])) { return index; }
		}
		return 0;
	}
	
}
