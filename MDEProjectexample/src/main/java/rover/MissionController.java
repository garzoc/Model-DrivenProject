package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.LocationController;
import CentralStation.GET;



public class MissionController extends Thread {

	private final Strategy strategy;
	private final RobotInterface robot;
	private volatile int tick=0;
	private int locationID;
	
	MissionController(Strategy strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
		LocationController cl;
		if((cl=GET.CentralStation().LocationFinder().getLocationContoller(robot.getRobotPosition()))!=null) {
			locationID=cl.getID();
		}else {
			locationID=-1;
		}
	}
	
	public void run() {
		Point2D.Double[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.setDestination(missionPoints[missionProgress]);
		LocationController lc=GET.CentralStation().LocationFinder().getLocationContoller(robot.getRobotPosition());
		boolean controllerExists=false;
		while(missionProgress!=missionPoints.length) {
			tick=missionProgress;
			
			lc=GET.CentralStation().LocationFinder().getLocationContoller(robot.getRobotPosition());
			checkForNewRoom(lc);
			
			//System.out.println("robot x "+robot.getRobotPosition().getX()+" y pos is "+robot.getRobotPosition().getY());//impotant
					
			if(robot.isAtPosition(missionPoints[missionProgress])){
				//Singleton.LockAccess();
				//Singleton.UnlockAccess();
				System.out.println("is at Position");
				if(missionProgress+1!=missionPoints.length) {
					robot.setDestination(missionPoints[++missionProgress]);
				}else {
					break;
				}
			}
    	   
		}
		robot.onMissionComplete();
	}
	
	private void checkForNewRoom(LocationController lc) {
		
		if(lc!=null && lc.getID()!=locationID) {
			robot.onNewRoomEnter(lc);
			locationID=lc.getID();
		}
	}
	
	
}
