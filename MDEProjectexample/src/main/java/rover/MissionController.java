package rover;

import java.awt.Point;



public class MissionController extends Thread {

	private final Strategies strategy;
	private final Robot robot;
	
	MissionController(Strategies strategy,Robot robot){
		this.strategy=strategy;
		this.robot=robot;
	}
	
	
	public void run() {
		Point[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		while(missionProgress!=missionPoints.length) {
			
			if(robot.isAtPosition(missionPoints[missionProgress])){
				missionProgress++;
				robot.setDestination(missionPoints[missionProgress]);
			}
    	   
		}
		robot.onMissionComplete();
	}
}
