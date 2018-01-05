package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import project.AbstractRobotSimulator;
import CentralStation.Environment;
import CentralStation.GET;
import CentralStation.LocationController;
import CentralStation.Environment.AreaType;
import CentralStation.Environment.PointSystem;

public class Robot extends AbstractRobotSimulator implements RobotInterface {

	private MissionController m;

	private long time;
	private boolean usePointSystemA;
	private int ID;
	
	public Robot(Point2D.Double position, String name) {
		super(convertCoord(position), name);
		m=null;
		time=System.currentTimeMillis();
		usePointSystemA=true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginMission(Strategy str) {
		
		if(m==null) {
			m=new MissionController(str,this);
			m.start();
		}
		
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID=ID;
	}
	
	public MissionController getMission() {
		return this.m;
	}
	

	@Override
	public boolean isAtPosition(Point2D.Double p) {//the super method version didn't work very well
		// TODO Auto-generated method stub
		Point2D.Double cp= this.getRobotPosition();
		double robotRadius=0.61;
		//cp.x=Math.round(cp.getX()*1000)/1000;
		//cp.y=Math.round(cp.getY()*1000)/1000;
		return cp.getX()-p.getX()>-robotRadius &&  cp.getX()-p.getX()<robotRadius && cp.getY()-p.getY()<robotRadius &&  cp.getY()-p.getY()>-robotRadius;
		
		//System.out.println("cp is "+cp.getX()+" p is "+p.getX()+" difference X is " +(cp.getX()-p.getX())+" is "+ (cp.getX()-p.getX()>-robotRadius &&  cp.getX()-p.getX()<robotRadius));
		//System.out.println("cp is "+cp.getY()+" p is "+p.getY()+" difference y is " +(cp.getY()-p.getY())+" is "+ (cp.getY()-p.getY()>-robotRadius &&  cp.getY()-p.getY()<robotRadius));	
	}
	
	/*private void getRewardPoints() {
		LocationController lc;
			
		lc=GET.CentralStation().environment.getLocationControllerByType(this.getRobotPosition(),Environment.AreaType.PHYSICAL);
		if(lc!=null) {
			GET.CentralStation().setRewardPoint(lc.REWARD_POINTS,this,PointSystem.A);
		}
	
		lc=GET.CentralStation().environment.getLocationControllerByType(this.getRobotPosition(),Environment.AreaType.LOGICAL);
		if(lc!=null) {
			GET.CentralStation().setRewardPoint(lc.REWARD_POINTS,this,PointSystem.B);
		}
		
	}*/
	
	

	//terminate the current mission and replaced it with new mission
	@Override
	public void overwriteMission(Strategy str){
			this.termiateMission();
			m= new MissionController(str,this);
			m.start();
		
		
	}


	
	
	@Override
	public void missionUpdate(){
		if(System.currentTimeMillis()-time>2000) {
			//getRewardPoints();	
			time=System.currentTimeMillis();
			LocationController lc=GET.locationByOrder(getRobotPosition());
			if(lc!=null) {
				if(lc.getAreaType()==AreaType.LOGICAL)
					GET.CentralStation().setRewardPoint(GET.locationByType(getRobotPosition(),AreaType.LOGICAL).REWARD_POINTS, this,PointSystem.B );
				else 
					GET.CentralStation().setRewardPoint(0, this,PointSystem.B );
				try {
				GET.CentralStation().setRewardPoint(GET.locationByType(getRobotPosition(),AreaType.PHYSICAL).REWARD_POINTS, this,PointSystem.A );
				}catch(Exception e) {}
			}else {
				GET.CentralStation().setRewardPoint(0, this,PointSystem.A );
				GET.CentralStation().setRewardPoint(0, this,PointSystem.B );
			}
		}
		
	}
	
	@Override
	public void onMissionBegin() {
		//GET.Lock();
			//System.out.println(m.getStrategy().getPathByRoom().length);
			//GET.locationByID(m.getStrategy().getPathByRoom()[0]).LockArea(this);
			//GET.locationByID(m.getStrategy().getPathByRoom()[1]).LockArea(this);
		//GET.Unlock();;
	}



	
	//Entering a room from another room
	public void onRoomSwitched(int newRoomID,int oldRoomID,AreaType areaType) {
		if(areaType==AreaType.PHYSICAL) {
			//LOCK handles concurrency and make sure that robots do not lock the room at the same time
			//System.out.println("switch Physical "+GET.locationByID(newRoomID).getLocationName()+ " robot name "+this.getID());
			GET.Lock(this);
				GET.CentralStation().environment.getControllerByID(newRoomID).LockArea(this);	
				GET.CentralStation().environment.getControllerByID(oldRoomID).UnlockArea(this);
			GET.Unlock();
			this.pause(2000);
			
		}else{
			/*GET.Lock(this);
				GET.CentralStation().environment.getControllerByID(newRoomID).LockArea(this);	
				GET.CentralStation().environment.getControllerByID(oldRoomID).UnlockArea(this);
			GET.Unlock();*/
	
		}
	}
	//Enter the area(rooms) from outside
	public void onAreaEnter(int newRoomID,AreaType areaType) {
		if(areaType==AreaType.PHYSICAL) {
			//System.out.println("entered physical "+GET.locationByID(newRoomID).getLocationName()+ " robot name "+this.getID());
			GET.Lock(this);
				GET.locationByID(newRoomID).LockArea(this);	
			GET.Unlock();
			this.pause(2000);
			
		}else{
			//System.out.println("Hi Logic "+GET.locationByID(newRoomID).getLocationName());
			/*GET.Lock(this);
				GET.locationByID(newRoomID).LockArea(this);	
			GET.Unlock();*/
		}
	}
	//leave the room and go outside 
	public void onAreaLeave(int oldRoomID,AreaType areaType) {
		
		if(areaType==AreaType.PHYSICAL) {
			GET.CentralStation().environment.getControllerByID(oldRoomID).UnlockArea(this);
			//System.out.println("bye physical "+GET.CentralStation().environment.getControllerByID(oldRoomID).getLocationName()+ " robot name "+this.getID());
		}else{
			//System.out.println("Bye Logic "+GET.locationByID(oldRoomID).getLocationName());
			//GET.CentralStation().environment.getControllerByID(oldRoomID).UnlockArea(this);
		}
	}
	
	@Override
	public void onMissionComplete() {
		System.out.println("mission completed");
	}
	
	
	//Tell the robot go to Point p
	@Override
	public void setDestination(Point2D.Double p) {		
		super.setDestination(convertCoord(p));
		
	}
	//convert back to the normal coordinate system
	private static project.Point convertCoord(Point2D.Double p){
		return new project.Point(-p.getY(),-p.getX());
	}
	

	@Override
	public Point2D.Double getRobotPosition() {
		// TODO Auto-generated method stub
		project.Point p=super.getPosition();
		
		return new Point2D.Double(-((double)Math.round(p.getZ()*1000)/1000),-((double)Math.round(p.getX()*1000))/1000);
	}

	@Override
	public String getRobotName() {
		return super.getName();
	}



}
