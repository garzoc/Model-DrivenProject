package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.Singleton;



public class MissionController implements Runnable {

	private final Strategy strategy;
	private final RobotInterface robot;
	private volatile int tick=0;
	MissionController(Strategy strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
	}
	
	
	public void run() {
		Point2D.Double[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.setDestination(missionPoints[missionProgress]);
		
		while(missionProgress!=missionPoints.length) {
			
			//System.out.println("robot x "+robot.getRobotPosition().getX()+" y pos is "+robot.getRobotPosition().getY());//impotant
			tick=missionProgress;
			if(robot.isAtPosition(missionPoints[missionProgress])){
				System.out.println("is at Position");
				if(missionProgress+1!=missionPoints.length) {
					robot.setDestination(missionPoints[++missionProgress]);
				}else {
					break;
				}
			}
    	   
		}this.
		robot.onMissionComplete();
	}
}
