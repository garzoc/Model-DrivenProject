package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import project.AbstractRobotSimulator;
import CentralStation.Environment;
import CentralStation.GET;
import CentralStation.LocationController;

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
	
	private void getRewardPoints() {
		LocationController lc;
			
		lc=GET.CentralStation().environment.getLocationControllerByType(this.getRobotPosition(),Environment.AreaType.PHYSICAL);
		if(lc!=null) {
			GET.CentralStation().setRewardPoint(lc.REWARD_POINTS,this,Environment.AreaType.PHYSICAL);
		}
	
		lc=GET.CentralStation().environment.getLocationControllerByType(this.getRobotPosition(),Environment.AreaType.LOGICAL);
		if(lc!=null) {
			GET.CentralStation().setRewardPoint(lc.REWARD_POINTS,this,Environment.AreaType.LOGICAL);
		}
		
	}
	
	

	
	@Override
	public void overwriteMission(Strategy str){
			this.termiateMission();
			m= new MissionController(str,this);
			m.start();
		
		
	}


	

	@Override
	public void missionUpdate(){
		if(System.currentTimeMillis()-time>1000) {
			getRewardPoints();	
			time=System.currentTimeMillis();
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

	@Override
	public void onMissionComplete() {
		System.out.println("mission completed");
	}
	//Leave a old room and then enter a new room
	@Override
	public void onPhysicalRoomSwitched(int newRoomID,int oldRoomID) {
		System.out.println("hi a new physical room"+newRoomID+" vs "+oldRoomID);
		GET.CentralStation().environment.getControllerByID(oldRoomID).UnlockArea(this);
		GET.CentralStation().environment.getControllerByID(newRoomID).LockArea(this);
	}
	
	@Override
	public void onLogicalRoomSwitched(int newRoomID,int oldRoomID){
		//System.out.println("hi a new logical room"+newRoomID+" vs "+oldRoomID);
	}
	//Leave a room and then enter outside
	@Override
	public void onPhysicalAreaLeave(int oldRoomID) {
		System.out.println("Bye physical "+oldRoomID);
		GET.CentralStation().environment.getControllerByID(oldRoomID).UnlockArea(this);
	}
	
	
	//Enter a room from outside
	@Override
	public void onPhysicalAreaEnter(int newRoomID) {
		//System.out.println("welcome physical "+newRoomID);
		GET.CentralStation().environment.getControllerByID(newRoomID).LockArea(this);	
	}
	
	@Override
	public void onLogicalAreaEnter(int newRoomID) {
		System.out.println("welcome logic "+newRoomID);
		//this.termiateMission();
		
		/*this.setDestination(this.getPosition());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void onLogicalAreaLeave(int oldRoomID) {
		System.out.println("Bye logic"+oldRoomID);
	}
	
	
	
	//Tell the robot go to Point p
	@Override
	public void setDestination(Point2D.Double p) {		
		super.setDestination(convertCoord(p));
		
	}
	
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
	public int getRobotName() {
		super.getName();
		return 0;
	}



}
