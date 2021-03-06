package CentralStation;

import java.awt.geom.Point2D;

import CentralStation.Environment.AreaType;
import rover.RobotInterface;

public class LocationController {
	private Point2D.Double[] boundries;
	private volatile RobotInterface owner;
	private int numberOfRobotsInside=0;
	private int ID;
	public final int REWARD_POINTS;
	private AreaType areaType;
	private String locationName;
	
	
	
	public LocationController(double x,double y,double width,double height,AreaType type,String name,int rewardPoints){
		Point2D.Double[] b= {new Point2D.Double(x,y),new Point2D.Double(width,height)};
		this.boundries=b;
		this.areaType=type;
		REWARD_POINTS=rewardPoints;
		this.locationName=name;
		
	}
	
	public AreaType getAreaType() {
		return this.areaType;
	}
	
	public String getLocationName() {
		return this.locationName;
	}
	
	protected void setLocationName(String locationName) {
		this.locationName=locationName;
	}
	
	protected void setID(int ID) {
		this.ID=ID;
	}
	
	public int getID() {
		return ID;
	}
	
	 public void LockArea(RobotInterface robot) {
		 while(!tryAndAquire(robot)) {robot.pause(1);
		// System.out.println("owner ID "+owner.getID()+" robot ID "+robot.getID());
		 }
		// System.out.println("locking "+robot.getID());
		if(this.LocationIsAccessbile(robot)) {
			//System.out.println("locking "+robot.getID());
			this.owner = robot;
		}
	}
	  public void UnlockArea(RobotInterface robot) {
		if(this.LocationIsAccessbile(robot)) {
			
			this.owner = null;
		}
	}
	
	public boolean isLocked() {
		return this.owner!=null;
	}
	
	synchronized private boolean tryAndAquire(RobotInterface robot) {
		//if(this.owner!=null)
		//System.out.println("owner ID "+owner.getID()+" robot ID "+robot.getID());
		if( this.owner==null || this.owner.getID()==robot.getID()) {
			this.owner=robot;
			return true;
		}
		return false;
	}
	
	 public boolean LocationIsAccessbile(RobotInterface robot) {
		//if(this.owner!=null)
		//System.out.println("owner ID "+owner.getID()+" robot ID "+robot.getID());
		return this.owner==null || this.owner.getID()==robot.getID();	
	}
	
	/*public void robotOnEnter() {
		numberOfRobotsInside ++;
	}
	public void robotOnLeave() {
		numberOfRobotsInside --;
	}*/
	
	protected double getX() {
		return boundries[0].getX();
	}
	
	protected double getY() {
		return boundries[0].getY();
	}
	
	protected double getWidth() {
		return boundries[1].getX();
	}
	
	protected double getHeight() {
		return boundries[1].getY();
	}
	

}
