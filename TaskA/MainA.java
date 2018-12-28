/*
 *Author : KHP
 */
 
package TaskA;

import TaskB.StatusBoard;


public class MainA {

	public static void main(String[] args) {
		Controller controller = new Controller();
		StatusBoard statusBoard = new StatusBoard();		

		controller.setStatusBoard(statusBoard);
		statusBoard.setVisible(false);
		controller.start();
	}
	
}
