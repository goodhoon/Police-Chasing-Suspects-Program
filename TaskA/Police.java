/*
 *Author : KHP
 */
 
package TaskA;


public class Police extends Person {
	
	protected static final int STANDBY = 0;
	protected static final int MOVE_TO_KENNEL = 1;
	protected static final int AT_KENNEL = 2;
	protected static final int MOVE_TO_SUSPECT = 3;
	protected static final int AT_SCENE = 4;
	protected static final int RETURNING = 5;
	public static final String[] STATUS_ARRAY = { "Standby", "Approaching Kennel", "At Kennel",
												  "Approaching Suspect", "At Scene", "Returning" };
	public boolean hasDog = false;
	private int atSceneWait = 0;
	

	public Police(Controller control, String id, String locX,
				  String locY, String status, String dog, String suspectID) {
		super(control, id, locX, locY, statusStoI(STATUS_ARRAY, status), suspectID);
		hasDog = (dog.equals("No") ? false : true);
	}
	
	
	public synchronized boolean hasDog() {
		return hasDog;
	}
	
	public synchronized String toString() {
		return (id + "," + locationX + "," + locationY + "," + STATUS_ARRAY[status] + "," + (hasDog ? "Yes" : "No") + "," + (counterpartID != null ? counterpartID : ""));
	}

	public synchronized void standBy() {
		
		Suspect suspect = controller.getColsestSuspect(this);

		if(suspect != null) {
			counterpartID = suspect.id;
			suspect.counterpartID = this.id;
			this.status = MOVE_TO_KENNEL;
			suspect.status = Suspect.ASSIGNED;
		}
		
	}
	
	public abstract class WhenMoving {
		abstract void onArrive();
	}
	
	public synchronized void moveTo(Point point, int moves, int doneStatus) {
		moveTo(point, moves, doneStatus, null);
	}
		
	public synchronized void moveTo(Point dest, int moves, int doneStatus, WhenMoving doMore) {   
	        if ((Math.abs(locationX - dest.getX()) + Math.abs(locationY - dest.getY())) <= moves) {
	        		status = doneStatus;
		            locationX = dest.getX();
		        	locationY = dest.getY();
				if(doMore != null)
					doMore.onArrive();
	        } else {
	        	if ((Math.abs(locationX - dest.getX()) > moves)) {
	        		if (locationX < dest.getX()) {
	        			locationX += moves;
	        		} else if (locationX > dest.getX()) {
	        			locationX -= moves;
	        		}
	        	} else if ((Math.abs(locationY - dest.getY()) > moves)) {
	        		if (locationY < dest.getY()) {
	        			locationY += moves;
	        		} else if (locationY > dest.getY()) {
	        			locationY -= moves;
	        		}
	        	} else {
	        		if (locationX < dest.getX()) {
	        			locationX += (dest.getX() - locationX);
	        			locationY += (moves - (dest.getX() - locationX));
	        		} else if (locationX > dest.getX()) {
	        			locationX += (locationX - dest.getX());
	        			locationY += (moves - (locationX - dest.getX()));
	        		}
	        	}
	        }
	}
		
	public synchronized void moveToKennel() {
		Kennel kennel = controller.getKennel();
		moveTo(kennel.getPoint(), 3, AT_KENNEL);
	}
	
	public synchronized void atKennel() {
		Kennel kennel = controller.getKennel();

		if(hasDog) {
			if(kennel.increase()) {
				hasDog = false;
				status = RETURNING;
			}
		} else {
			if(kennel.decrease())
				hasDog = true;
				status = MOVE_TO_SUSPECT;
		}
	}	
	
	public synchronized void moveToSuspect() {
		Suspect suspect = controller.getSuspect(counterpartID);
		
		if(suspect != null) {
			moveTo(suspect.getPoint(), 4, AT_SCENE, new WhenMoving() {
				synchronized void onArrive() {
				suspect.status = Suspect.CAUGHT;
				}
			});
		}
		
	}

	public synchronized void atScene() {
		atSceneWait++;
		if(atSceneWait == 4) {
			status = MOVE_TO_KENNEL;
			atSceneWait = 0;
		}
	}
	
	public synchronized void returning() {

		Station policeStation = controller.getClosestStation(this);
		
		if(policeStation != null) {

			moveTo(policeStation.getPoint(), 3, STANDBY, new WhenMoving() {
				synchronized void onArrive() {
					policeStation.increase();
					Suspect suspect = controller.getSuspect(counterpartID);
					suspect.status = Suspect.JAILED;
				}
			});
		}
	}
	
	public synchronized void tick() {
		switch (status) {
		case STANDBY: standBy(); break;
		case MOVE_TO_KENNEL: moveToKennel(); break;
		case AT_KENNEL: atKennel(); break;
		case MOVE_TO_SUSPECT: moveToSuspect(); break;
		case AT_SCENE: atScene(); break;
		case RETURNING: returning(); break;
		}
		System.out.println(this);
	}
	
}
