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
		if((cl=GET.CentralStation().environment.getLocationController(robot.getRobotPosition()))!=null) {
			locationID=cl.getID();
		}else {
			locationID=-1;
		}
	}
	
	public void run() {
		Point2D.Double[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.setDestination(missionPoints[missionProgress]);
		LocationController lc=GET.CentralStation().environment.getLocationController(robot.getRobotPosition());
		
		boolean controllerExists=false;
		while(missionProgress!=missionPoints.length) {
			tick=missionProgress;
			
			lc=GET.CentralStation().environment.getLocationController(robot.getRobotPosition());
			onRoomEnter(lc);
			
			//System.out.println("robot x "+robot.getRobotPosition().getX()+" y pos is "+robot.getRobotPosition().getY());//impotant
			
			char val=(char) (missionPoints.length>missionProgress+1?1:0);
			if(robot.isAtPosition(missionPoints[missionProgress]) && checkBeforeEnter(missionPoints[val+missionProgress])){
				//Singleton.LockAccess();
				//Singleton.UnlockAccess();
				//System.out.println("is at Position");
				if(missionProgress+1!=missionPoints.length) {
					robot.setDestination(missionPoints[++missionProgress]);
				}else {
					break;
				}
			}
    	   
		}
		robot.onMissionComplete();
	}
	
	private void onRoomEnter(LocationController newRoom) {
		if(isNewRoom(newRoom)) {
			robot.onNewRoomEnter(newRoom.getID());
			newRoom.LockArea();
			LocationController nlc;
			if ((nlc=GET.CentralStation().environment.getControllerByID(locationID))!=null) nlc.UnlockArea();
				locationID=newRoom.getID();
		}
	}
	
	private boolean isNewRoom(LocationController r) {
		if(r!=null && r.getID()!=locationID) return true;
		return false;
	}
	
	private boolean checkBeforeEnter(Point2D.Double p) {
		LocationController lc=GET.CentralStation().environment.getLocationController(p);
		
		if(isNewRoom(lc)) {
			return lc.LocationIsAccessbile(); 
		}
		return true;
	}
	
	
}
