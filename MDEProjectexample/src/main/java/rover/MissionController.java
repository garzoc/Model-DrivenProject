package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.LocationController;
import CentralStation.Environment;
import CentralStation.Environment.AreaType;
import CentralStation.GET;



class MissionController extends Thread {
	final static int NO_AREA=-1;
	private final Strategy strategy;
	private final RobotInterface robot;

	private int[] oldRoomIDTracker;//test
	private volatile boolean forcedTermination;
	
	MissionController(Strategy strategy,RobotInterface robot){
		this.strategy=strategy;
		this.robot=robot;
		strategy.createPathByRoom();

		
		LocationController lc;
	
		forcedTermination=false;
		
		//test code block code
		oldRoomIDTracker=new int[AreaType.values().length];
		for(int i=0;i<oldRoomIDTracker.length;i++) {
			if((lc=GET.locationByType(robot.getRobotPosition(),AreaType.values()[i]))!=null) {
				oldRoomIDTracker[i]=lc.getID();
				
			}else {
				oldRoomIDTracker[i]=NO_AREA;
			}
		}
	
	}

	//Robot running function 
	public void run() {
		Point2D.Double[] missionPoints = strategy.getOriginalPoints();
		int missionProgress = 0;
		robot.onMissionBegin();
		
		LocationController lc;
		
		//boolean controllerExists=false;
		//as long as the robot is DONE with the mission, keep looping
		while(!forcedTermination) {
			//System.out.println("hello "+robot.getRobotName());
			robot.missionUpdate();
			lc=GET.locationByOrder(robot.getRobotPosition());
			//GET.Lock(robot);
			testForRoom(lc);
			//GET.Unlock();
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
				//System.out.println("hello "+robot.getRobotName());
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
	

	private int getLayerPriority(AreaType areaType) {
		
		return areaType.ordinal();
	}
	
	private int getOldRoomID(AreaType areaType) {
		
		return oldRoomIDTracker[getLayerPriority(areaType)];
	}
	
private void setRoomOldID(AreaType areaType,int ID) {
		
		 oldRoomIDTracker[areaType.ordinal()]=ID;
	}
	
	private void testForRoom(LocationController lc) {//change name if this method as it is unclear what it does otherwise
		
		int highestLayerFound=0;
		if(isRoom(lc))
			highestLayerFound=getLayerPriority(lc.getAreaType());
		else
			highestLayerFound=NO_AREA;
		
		
		
		
		
		for(int i=0;i<=highestLayerFound;i++) {
			lc=GET.locationByType(robot.getRobotPosition(),AreaType.values()[i]);
			if(isRoom(lc)) {
				if(switchedLocation(lc.getID(),lc.getAreaType())) {
					if(isRoom(GET.locationByID(getOldRoomID(lc.getAreaType())))){
						robot.onRoomSwitched(lc.getID(), getOldRoomID(lc.getAreaType()),lc.getAreaType());
					}else{
						robot.onAreaEnter(lc.getID(), lc.getAreaType());
					}
					setRoomOldID(lc.getAreaType(),lc.getID());
				}
				
			}else if(switchedLocation(NO_AREA,AreaType.values()[i])) {
				robot.onAreaLeave(getOldRoomID(AreaType.values()[i]),AreaType.values()[i]);
				setRoomOldID(AreaType.values()[i],NO_AREA);
			}/*else {
				System.out.println("leaving ");
			}*/
		}
		
		for(int i=highestLayerFound+1;i<AreaType.values().length;i++) {
			if(switchedLocation(NO_AREA,AreaType.values()[i])) {
				robot.onAreaLeave(getOldRoomID(AreaType.values()[i]),AreaType.values()[i]);
				setRoomOldID(AreaType.values()[i],NO_AREA);
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
	
	
	private boolean switchedLocation(int newRoomID,AreaType areaType) {
		boolean ifISROOM=(isRoom(newRoomID)  && GET.locationByID(newRoomID).getID()!=oldRoomIDTracker[getLayerPriority(areaType)]);
		boolean ifIsNotRoom= (!isRoom(newRoomID) && oldRoomIDTracker[getLayerPriority(areaType)]!=NO_AREA);
		
		if( ifISROOM||ifIsNotRoom) {
			return true;
		}
	
		return false;
	}
	
	/*private boolean switchedLocation(int newRoomID,AreaType) {
		boolean ifISROOM=(isRoom(newRoomID) && newRoomID!=layerdRoomIDTracker[GET.locationByID(newRoomID).getAreaType().ordinal()]);
		boolean ifIsNotRoom= (!isRoom(newRoomID) && layerdRoomIDTracker[GET.locationByID(newRoomID).getAreaType().ordinal()]!=NO_AREA);
		
		if( ifISROOM||ifIsNotRoom) {
			return true;
		}
	
		return false;
	}*/
	
	
	private boolean checkBeforeEnter(Point2D.Double target) {//change thos method as it is not safe
		//LocationController lc=GET.locationByOrder(target);
		
		//uncommented code checks if the robot is close to entering a new area
		/*Point2D.Double currentPos=robot.getRobotPosition();
		//Y=kx+m;
		//calculate the coefficient of the linear equation
		double k=target.getY()==currentPos.getY()?0:(target.getY()-currentPos.getY())/(target.getX()-currentPos.getX());
		//calcuate the coefficient value in degrees
		double angle= Math.toDegrees(Math.atan(k));
		//set the distance from which the robot should detect a new room
		double hypotenuse=1.5;
		//calculate the difference in x values
		double deltaX=Math.cos(angle)*hypotenuse;
		//calculate the difference in x values
		double deltaY=Math.sqrt(Math.pow(hypotenuse, 2)-Math.pow(deltaX, 2));
		//Set the correct orientation in  
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
		if(isRoom(test)&&switchedLocation(test.getID(),AreaType.PHYSICAL)) {
			System.out.println("about to enter a new room "+test.getLocationName());
		}*/
		
		
		LocationController lc=GET.locationByOrder(target);
		
		if(isRoom(lc)) {
			int topLayer=getLayerPriority(lc.getAreaType());
			for(int i=0;i<=topLayer;i++) {
				lc=GET.locationByType(target,AreaType.values()[i]);
				if(isRoom(lc) && !lc.LocationIsAccessbile(robot)) {
					return false;
				}
			}
		}
		return true;

	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}
	
	
}
