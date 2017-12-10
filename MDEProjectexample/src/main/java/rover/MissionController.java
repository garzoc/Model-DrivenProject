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

	//Robot running function 
	public void run() {
		Point2D.Double[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.setDestination(missionPoints[missionProgress]);
		LocationController lc;
		
		//boolean controllerExists=false;
		//as long as the robot is DONE with the mission, keep looping
		boolean missionHasFinished=false; 
		while(!missionHasFinished) {
			tick=missionProgress;
			//get the current location of the robot
			lc=GET.CentralStation().environment.getLocationController(robot.getRobotPosition());
			onRoomEnter(lc);
						
			char val=(char) (missionPoints.length>missionProgress+1?1:0);
			//check if the robot has reached the Point and if next room is locked 
			if(robot.isAtPosition(missionPoints[missionProgress]) && checkBeforeEnter(missionPoints[val+missionProgress])){
				//If mission is finished continue the mission
				if(missionProgress+1!=missionPoints.length) {
					robot.setDestination(missionPoints[++missionProgress]);
				}else {//otherwise break the loop 
					missionHasFinished=true;
				}
			}
    	   
		}
		robot.onMissionComplete();
	}
	//
	private void onRoomEnter(LocationController newRoom) {
		if(switchedRooms(newRoom)) {		
				if (isRoom(newRoom)) { 
					if(isRoom(GET.CentralStation().environment.getControllerByID(locationID))) {
						robot.onNewRoomEnter(newRoom.getID(),locationID);
					}else {
						robot.onAreaEnter(newRoom.getID());
					}
					locationID=newRoom.getID();
				}else {
					robot.onAreaLeave(locationID);
					locationID=-1;
				}
				
		}
	}
	
	private boolean isRoom(LocationController r) {
		if(r!=null) return true;
		return false;
	}
	
	
	private boolean switchedRooms(LocationController r) {
		
		if((isRoom(r)  && r.getID()!=locationID) || (!isRoom(r) && locationID!=-1)) {
			return true;
		}
		return false;
	}
	
	private boolean checkBeforeEnter(Point2D.Double p) {
		LocationController lc=GET.CentralStation().environment.getLocationController(p);
		
		if(isRoom(lc)&& locationID != lc.getID()) {
			return lc.LocationIsAccessbile(); 
		}
		return true;
	}
	
	
}
