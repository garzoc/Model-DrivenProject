package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.LocationController;
import CentralStation.Environment;
import CentralStation.Environment.AreaType;
import CentralStation.GET;



class MissionController extends Thread {

	private final Strategy strategy;
	private final RobotInterface robot;
	private volatile int tick=0;
	private int physicalLocationID;
	private int logicalLocationID;
	private boolean forcedTermination;
	
	MissionController(Strategy strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
		strategy.createPathByRoom();

		
		LocationController cl;
		physicalLocationID=-1;
		logicalLocationID=-1;
		if(isLogical(cl= GET.locationByOrder(robot.getRobotPosition()))) {
			logicalLocationID=cl.getID();
			physicalLocationID=GET.locationByType(robot.getRobotPosition(),AreaType.PHYSICAL).getID();
		}
		if(isPhysical(cl)){
			physicalLocationID=cl.getID();
		}
		forcedTermination=false;
	
	}

	//Robot running function 
	public void run() {
		Point2D.Double[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.onMissionBegin();
		
		robot.setDestination(missionPoints[missionProgress]);
		LocationController lc;
		
		//boolean controllerExists=false;
		//as long as the robot is DONE with the mission, keep looping
		while(!forcedTermination) {
			tick=missionProgress;
			
			robot.missionUpdate();
			
			lc=GET.locationByOrder(robot.getRobotPosition());
			if(isLogical(lc)) {
				onRoomEnter(lc);
				lc= GET.locationByType(robot.getRobotPosition(), AreaType.PHYSICAL);
			}
			onRoomEnter(lc);
		
			//check if the robot has reached the Point and if next room is locked
			//check if the room  of point that going to be reached is unlcoked
			if(checkBeforeEnter(missionPoints[missionProgress])) {
				if(robot.isAtPosition(missionPoints[missionProgress])){
					//If mission is not finished continue the mission
					if(missionProgress+1!=missionPoints.length) {
						robot.setDestination(missionPoints[++missionProgress]);
					}else {//otherwise break the loop 
						break;
					}
				}
				robot.setDestination(missionPoints[missionProgress]);
			}else {
				robot.pause(1);
			}
    	   
		}
		if(!forcedTermination) {
			robot.onMissionComplete();
		}else {
			robot.onMissionTerminated();
		}
	}
	//
	
	protected void forceTerminat() {
		this.forcedTermination=true;
	}
	
	//check if the robot enter anew room, if so update the relevant info for robot 
	private void onRoomEnter(LocationController newRoom) {//change name if this method as it is unclear what it does otherwise
		//if the robot has entered a new area and if that area is a room
		if(switchedLocation(newRoom)) {		
				//if its a new room, UNlock the old room and Lock the new room		
			if(isPhysical(newRoom)) {
				if(isRoom(physicalLocationID)) {
					robot.onRoomSwitched(newRoom.getID(),physicalLocationID,AreaType.PHYSICAL);	
				}else {
						//otherwise, robot is entering the new room from outside
						//lock the new room 
					robot.onAreaEnter(newRoom.getID(),AreaType.PHYSICAL);
				}//update the locationID to the current  room 
				physicalLocationID=newRoom.getID();
				
			}else if(isLogical(newRoom)){
				if(isRoom(logicalLocationID)) {
					robot.onRoomSwitched(newRoom.getID(),logicalLocationID,AreaType.LOGICAL);	
				}else {
					robot.onAreaEnter(newRoom.getID(),AreaType.LOGICAL);
				}//update the locationID to the current  room 
				logicalLocationID=newRoom.getID();
			}else {
				if(physicalLocationID!=-1) robot.onAreaLeave(physicalLocationID,AreaType.PHYSICAL);
				if(logicalLocationID!=-1) robot.onAreaLeave(logicalLocationID,AreaType.LOGICAL);
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
		if(GET.locationByID(roomID)!=null) return true;
		return false;
	}
	//check if the room is physical area
	private boolean isPhysical(LocationController room) {
		if(isRoom(room) && room.getAreaType()==AreaType.PHYSICAL) {
			return true;
		}
		return false;
	}
	
	private boolean isLogical(LocationController room) {
		
		if(isRoom(room) && room.getAreaType()==AreaType.LOGICAL) {
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
	
	private boolean checkBeforeEnter(Point2D.Double target) {//change thos method as it is not safe
		LocationController lc=GET.locationByOrder(target);
		
		//uncommented code checks if the robot is close to entering a new area
		Point2D.Double currentPos=robot.getRobotPosition();
		//Y=kx+m;
		//calculate the coefficient of the linear equation
		double k=target.getY()==currentPos.getY()?0:(target.getY()-currentPos.getY())/(target.getX()-currentPos.getX());
		//System.out.println(k);
		double angle= Math.toDegrees(Math.atan(k));
		double hypotenuse=1.5;
		double deltaX=Math.cos(angle)*hypotenuse;
		
		double deltaY=Math.sqrt(Math.pow(hypotenuse, 2)-Math.pow(deltaX, 2));
		
		deltaX=currentPos.getX()<target.getX()?Math.abs(deltaX):-deltaX;
		deltaY=currentPos.getY()<target.getY()?Math.abs(deltaY):-deltaY;
		//the testing point in front of the robot 
		Point2D.Double p=new Point2D.Double(currentPos.getX()+deltaX, currentPos.getY()+deltaY);
		//System.out.println("delta Position X "+deltaX+" Y "+deltaY +"  Position X "+currentPos.getX()+" Y "+target.getX()+" and verify "+(currentPos.getX()<target.getX()));
		//System.out.println("delta Position X "+deltaX+" Y "+deltaY);
		//System.out.println("old Position X "+currentPos.getX()+" Y "+currentPos.getY());
		//System.out.println("new Position X "+(currentPos.getX()+deltaX)+" Y "+(currentPos.getY()+deltaY));
		//check if the test point is in a new room
		LocationController test=GET.locationByType(p,AreaType.PHYSICAL);
		if(isRoom(test)&&switchedLocation(test)) {
			System.out.println("about to enter a new room "+test.getLocationName());
		}
		//double m=currentPos.getY()-currentPos.getX()*k;
		
		
		
		
		if(isLogical(lc) && logicalLocationID != lc.getID()) {
			LocationController lc2=GET.locationByType(target,AreaType.PHYSICAL);
			return lc.LocationIsAccessbile(robot) && lc2.LocationIsAccessbile(robot); 
		}
		
		if(isPhysical(lc) && physicalLocationID != lc.getID()) {
			return lc.LocationIsAccessbile(robot); 
		}
		
		
		
		
		return true;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}
	
	
}
