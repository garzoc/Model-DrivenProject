package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.LocationController;
import CentralStation.Environment;
import CentralStation.GET;



public class MissionController extends Thread {

	private final Strategy strategy;
	private final RobotInterface robot;
	private volatile int tick=0;
	private int physicalLocationID;
	private int logicalLocationID;
	
	MissionController(Strategy strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
		LocationController cl;
		physicalLocationID=-1;
		logicalLocationID=-1;
		if(isLogical(cl= GET.getLocationByOrder(robot.getRobotPosition()))) {
			logicalLocationID=cl.getID();
		}
		if(isPhysical(cl)){
			physicalLocationID=cl.getID();
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
			robot.missionUpdate();
			
			lc=GET.getLocationByOrder(robot.getRobotPosition());
			if(isLogical(lc)) {
				onRoomEnter(lc);
				lc= GET.getLocationByType(robot.getRobotPosition(), Environment.AreaType.PHYSICAL);
			}else {
				if(logicalLocationID!=-1) robot.onLogicalAreaLeave(logicalLocationID);
				logicalLocationID=-1;
			}
			onRoomEnter(lc);
			
		
			char val=(char) (missionPoints.length>missionProgress+1?1:0);
			//check if the robot has reached the Point and if next room is locked 
			if(robot.isAtPosition(missionPoints[missionProgress]) && checkBeforeEnter(missionPoints[val+missionProgress])){
				//If mission is not finished continue the mission
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
		//if the robot has entered a new area and if that area is a room
		if(switchedLocation(newRoom)) {		
				//if its a new room, UNlock the old room and Lock the new room		
			if(isPhysical(newRoom)) {
				if(isRoom(physicalLocationID)) {
					robot.onPhysicalRoomSwitched(newRoom.getID(),physicalLocationID);	
				}else {
						//otherwise, robot is entering the new room from outside
						//lock the new room 
					robot.onPhysicalAreaEnter(newRoom.getID());
				}//update the locationID to the current  room 
				physicalLocationID=newRoom.getID();
				
			}else if(isLogical(newRoom)){
				if(isRoom(logicalLocationID)) {
					robot.onLogicalRoomSwitched(newRoom.getID(),logicalLocationID);	
				}else {
					robot.onLogicalAreaEnter(newRoom.getID());
				}//update the locationID to the current  room 
				logicalLocationID=newRoom.getID();
			}else {
				if(physicalLocationID!=-1) robot.onPhysicalAreaLeave(physicalLocationID);
				if(logicalLocationID!=-1) robot.onLogicalAreaLeave(logicalLocationID);
				physicalLocationID=-1;
				logicalLocationID=-1;
			}
		}

	}
	
	private boolean isRoom(LocationController lc) {
		if(lc!=null) return true;
		return false;
	}
	
	private boolean isRoom(int roomID) {
		if(GET.getLocationByID(roomID)!=null) return true;
		return false;
	}
	
	private boolean isPhysical(LocationController room) {
		if(isRoom(room) && room.getAreaType()==Environment.AreaType.PHYSICAL) {
			return true;
		}
		return false;
	}
	
	private boolean isLogical(LocationController room) {
		
		if(isRoom(room) && room.getAreaType()==Environment.AreaType.LOGICAL) {
			return true;
		}
		return false;
	}
	
	private boolean switchedLocation(LocationController room) {
		
		if((isRoom(room)  && isPhysical(room) && room.getID()!=physicalLocationID) || (!isRoom(room) && physicalLocationID!=-1)) {
			return true;
		}
		if((isRoom(room)  && isLogical(room) && room.getID()!=logicalLocationID) || (!isRoom(room) && logicalLocationID!=-1)) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkBeforeEnter(Point2D.Double p) {//needs tp be updated for logical areas
		LocationController lc=GET.getLocationByOrder(p);
		
		if(isLogical(lc) && logicalLocationID != lc.getID()) {
			LocationController lc2=GET.getLocationByType(p,Environment.AreaType.PHYSICAL);
			return lc.LocationIsAccessbile() && lc2.LocationIsAccessbile(); 
		}
		
		if(isPhysical(lc) && physicalLocationID != lc.getID()) {
			return lc.LocationIsAccessbile(); 
		}
		
		
		return true;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}
	
	
}
