/*
 *Author : KHP
 */

package TaskA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import TaskB.StatusBoard;


public class Controller {
	
	private ArrayList<Police> policeList;
	private ArrayList<Suspect> suspectList;
	private Kennel kennel;
	private ArrayList<Station> stationList;	
	
	private ExecutorService executorService;
	private StatusBoard statusBoard;

	
	public Controller() {
		policeList = readPolice("police.csv");
		suspectList = readSuspect("suspects.csv");

		kennel = new Kennel(this, 50, 50, suspectList.size() / 2);
		stationList = new ArrayList<Station>();
		stationList.add(new Station(this, 25, 5, "Downtown", policeList.size() / 4));
		stationList.add(new Station(this, 80,30, "Midtown", policeList.size() / 4));
		stationList.add(new Station(this, 10, 90, "Uptown", policeList.size() / 4));
		stationList.add(new Station(this, 70, 80, "Lazytown", policeList.size() / 4));
		
		executorService = Executors.newFixedThreadPool(policeList.size() + suspectList.size() + 1 + stationList.size());
	}

	public void start() {
		Collection<Unit> collection = new ArrayList<Unit>();
		collection.add(kennel);
		collection.addAll(policeList);
		collection.addAll(suspectList);
		collection.addAll(stationList);
		
		try {	
			int count = 0;
			int maxCycles = 100;
			long lastCycle = 0;
			
			while (count<maxCycles) {
				if(System.currentTimeMillis() - lastCycle >= 1000) {
					System.out.println(count);
					for(Unit collect : collection) { 
						executorService.execute(collect); 
					}
					lastCycle = System.currentTimeMillis();
					count++;
				}
			}
			
			executorService.shutdown();
			writeToFile("police-output.csv", "id,x.location,y.location,status,dog,suspect", policeList.toArray(new Police[policeList.size()]));
			writeToFile("suspects-output.csv", "id,x.location,y.location,status,police unit", suspectList.toArray(new Suspect[suspectList.size()]));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public synchronized ArrayList<Police> readPolice (String filename) {
	    ArrayList<Police> policeUnits = new ArrayList<Police>();
	    String str;
		    
		    try {
			Scanner policeData = new Scanner(Paths.get(filename));
		 	str = policeData.nextLine();
			while (policeData.hasNext()) {
				policeData.hasNext();
				str = policeData.nextLine();
				String[] arr = str.split(",");        
				Police police = new Police(this, arr[0], arr[1], arr[2], arr[3], arr[4], "");
				policeUnits.add(police);
				}
		    policeData.close();
		    } catch (IOException e) {
		        System.err.println ("IOException Error");
		    }
	    return policeUnits;
	}

	public synchronized ArrayList<Suspect> readSuspect (String filename){   

		ArrayList<Suspect> suspects = new ArrayList<Suspect>();
	    String str;
		    
		    try {
			Scanner suspectsData = new Scanner(Paths.get(filename));
		 	str = suspectsData.nextLine();
		    
			while (suspectsData.hasNext()) {
				suspectsData.hasNext();
				str = suspectsData.nextLine();
				String[] arr = str.split(",");          
				Suspect suspect = new Suspect(this, arr[0], arr[1], arr[2], arr[3], "");
				suspects.add(suspect);
				}
		    suspectsData.close();		
		    } catch (IOException e) {
		        System.err.println ("IOException Error");
		    }
		return suspects;
	}
	
	public synchronized void writeToFile(String path, String header, Person[] personList) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));	
			bw.write(header);
			for(Person person : personList) {
				bw.newLine();
				bw.write(person.toString());
			}		
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Suspect getColsestSuspect(Police police) {
		int minIndex = Integer.MIN_VALUE;
		double minDistance = Integer.MAX_VALUE;
		
		for(int i = 0 ; i < suspectList.size() ; i++) {
			Suspect suspect = suspectList.get(i);
			
			if(suspect.getStatus() == Suspect.UNASSIGNED) {
				double distance = police.getPoint().getClosestPoint(suspect.getPoint());
				if(distance < minDistance) {
					minIndex 	= i;
					minDistance	= distance;
				}
			}
		}
		
		if (minIndex == Integer.MIN_VALUE) {
			return null;
		} else {
			return suspectList.get(minIndex);
		}
	}
	
	public synchronized Station getClosestStation(Police police) {
		int minIndex = Integer.MIN_VALUE;
		double minDistance = Double.MAX_VALUE;
		
		for(int i = 0 ; i < stationList.size() ; i++) {
			Station station = stationList.get(i);
			
			if(station.acceptable()) {
				double distance = police.getPoint().getClosestPoint(station.getPoint());
				if(distance < minDistance) {
					minIndex 	= i;
					minDistance	= distance;
				}
			}
		}
		
		return stationList.get(minIndex);
	}
	
	public synchronized Kennel getKennel() {
		return kennel;
	}

	public synchronized Suspect getSuspect(String suspectId) {
		for(Suspect suspect : suspectList) {
			if(suspect.getID().equals(suspectId))
				return suspect;
			}
		return null;
	}

	public synchronized void setStatusBoard(StatusBoard board) {
		statusBoard = board;
	}
	
	public synchronized void updateUnit(Unit unit) {
		statusBoard.updateUnit(unit);
	}
	
}
