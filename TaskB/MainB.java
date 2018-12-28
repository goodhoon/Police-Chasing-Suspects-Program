/*
 *Author : KHP
 */

package TaskB;

import TaskA.Controller;


public class MainB {
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		StatusBoard statusBoard = new StatusBoard();
		
		controller.setStatusBoard(statusBoard);
		statusBoard.setVisible(true);
		controller.start();
	}
	
}
