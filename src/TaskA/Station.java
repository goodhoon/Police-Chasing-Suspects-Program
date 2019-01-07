/*
 *Author : KHP
 */

package TaskA;

public class Station extends Facility {
	
	private String stationName;
	
	public Station(Controller control, int locX, int locY, String name, int acceptableNum) {
		super(control, locX, locY, acceptableNum, 0);
		stationName = name;
	}
	
}
