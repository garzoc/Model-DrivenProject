package rover;

import java.awt.Point;

import CentralStation.Singleton;



public class MissionController extends Thread {

	private final Strategies strategy;
	private final int robotID;
	
	MissionController(Strategies strategy,int robotID){
		this.strategy=strategy;
		this.robotID=robotID;
	}
	
	
	public void run() {
		Point[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		while(missionProgress!=missionPoints.length) {
			
			if(Singleton.getCentralStation().getRobot(robotID).isAtPosition(missionPoints[missionProgress])){
				missionProgress++;
				Singleton.getCentralStation().getRobot(robotID).setDestination(missionPoints[missionProgress]);
				
			}
    	   
		}
		Singleton.getCentralStation().getRobot(robotID).onMissionComplete();
	}
}
