package rover;

import java.awt.Point;

import CentralStation.Singleton;



public class MissionController extends Thread {

	private final Strategies strategy;
	private final RobotInterface robot;
	
	MissionController(Strategies strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
	}
	
	
	public void run() {
		Point[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.setDestination(missionPoints[missionProgress]);
		while(missionProgress!=missionPoints.length) {
			System.out.println("running");//impotant
			if(robot.isAtPosition(missionPoints[missionProgress])){
				
				if(missionProgress+1!=missionPoints.length)robot.setDestination(missionPoints[++missionProgress]);
			}
    	   
		}
		robot.onMissionComplete();
	}
}
