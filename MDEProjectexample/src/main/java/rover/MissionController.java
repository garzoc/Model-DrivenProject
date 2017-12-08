package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.RoomController;
import CentralStation.GET;



public class MissionController extends Thread {

	private final Strategy strategy;
	private final RobotInterface robot;
	private volatile int tick=0;
	private int locationID;
	
	MissionController(Strategy strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
		RoomController cl;
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
		RoomController lc=GET.CentralStation().environment.getLocationController(robot.getRobotPosition());
		
		//boolean controllerExists=false;
		while(missionProgress!=missionPoints.length) {
			tick=missionProgress;
			
			lc=GET.CentralStation().environment.getLocationController(robot.getRobotPosition());
			onRoomEnter(lc);
						
			char val=(char) (missionPoints.length>missionProgress+1?1:0);
			if(robot.isAtPosition(missionPoints[missionProgress]) && checkBeforeEnter(missionPoints[val+missionProgress])){
				if(missionProgress+1!=missionPoints.length) {
					robot.setDestination(missionPoints[++missionProgress]);
				}else {
					break;
				}
			}
    	   
		}
		robot.onMissionComplete();
	}
	
	private void onRoomEnter(RoomController newRoom) {
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
	
	private boolean isRoom(RoomController r) {
		if(r!=null) return true;
		return false;
	}
	
	
	private boolean switchedRooms(RoomController r) {
		
		if((isRoom(r)  && r.getID()!=locationID) || (!isRoom(r) && locationID!=-1)) {
			return true;
		}
		return false;
	}
	
	private boolean checkBeforeEnter(Point2D.Double p) {
		RoomController lc=GET.CentralStation().environment.getLocationController(p);
		
		if(isRoom(lc)) {
			return lc.LocationIsAccessbile(); 
		}
		return true;
	}
	
	
}
