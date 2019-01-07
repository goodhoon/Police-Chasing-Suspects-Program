/*
 *Author : KHP
 */
 
package TaskB;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import TaskA.Police;
import TaskA.Unit;


public class StatusBoard extends JFrame {

	private DefaultTableModel dtm;
	private Vector<String> vectors;
	private HashMap<String, Vector<String>> hashMap;
	private JTable poliTable;
	
	public StatusBoard() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setSize(1200, 620);
		setLocation(100, 100);
		setLayout(new BorderLayout());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		hashMap = new HashMap<String, Vector<String>>();
		
		vectors = new Vector<String>();
		vectors.add("ID");
		vectors.add("Location");
		vectors.add("Status");
		vectors.add("Police Dog");
		vectors.add("Suspect");
		
		dtm = new DefaultTableModel(vectors, 0);
		poliTable = new JTable(dtm);
		
		poliTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		poliTable.getTableHeader().setPreferredSize(new Dimension(poliTable.getTableHeader().getWidth(), 50));
		
		poliTable.setFont(new Font("Arial", Font.PLAIN, 20));
		poliTable.setRowHeight(50);
		
		poliTable.setShowHorizontalLines(false);
		poliTable.setShowVerticalLines(false);
		
		poliTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		for(int column = 0 ; column < poliTable.getColumnCount() ; ++column) {
			poliTable.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
		}
		
		JScrollPane scrollPane = new JScrollPane(poliTable);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(scrollPane, BorderLayout.CENTER);
	}
	
	
	public void updateUnit(Unit unit) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				
				Vector<String> rowVector;
				
				if(unit instanceof Police) {
					
					Police poli = (Police) unit;
					
					synchronized (hashMap) {
						if(hashMap.containsKey(poli.getID())) {
							rowVector = hashMap.get(poli.getID());
							rowVector.set(1, poli.getPoint().toString());
							rowVector.set(2, Police.STATUS_ARRAY[poli.getStatus()]);
							rowVector.set(3, poli.hasDog() ? "YES" : "NO");
							rowVector.set(4, poli.counterpartID() == null ? "-" : poli.counterpartID());
							poliTable.updateUI();
						} else {
							rowVector = new Vector<String>();
							rowVector.addElement(poli.getID());
							rowVector.addElement(poli.getPoint().toString());
							rowVector.addElement(Police.STATUS_ARRAY[poli.getStatus()]);
							rowVector.addElement(poli.hasDog() ? "YES" : "NO");
							rowVector.addElement(poli.counterpartID() == null ? "-" : poli.counterpartID());
							
							dtm.addRow(rowVector);
							hashMap.put(poli.getID(), rowVector);
						}	
					}
				}
			}
		});
	}
}
