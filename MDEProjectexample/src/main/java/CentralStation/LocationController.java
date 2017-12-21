package CentralStation;

import java.awt.geom.Point2D;

import CentralStation.Environment.AreaType;
import rover.RobotInterface;

public class LocationController {
	private Point2D.Double[] boundries;
	private RobotInterface owner;
	private int numberOfRobotsInside=0;
	private int ID;
	public final int REWARD_POINTS;
	private AreaType areaType;
	
	
	
	public LocationController(double x,double y,double width,double height,AreaType type,int rewardPoints){
		Point2D.Double[] b= {new Point2D.Double(x,y),new Point2D.Double(width,height)};
		this.boundries=b;
		this.areaType=type;
		REWARD_POINTS=rewardPoints;
		
	}
	
	public AreaType getAreaType() {
		return this.areaType;
	}
	
	
	
	protected void setID(int ID) {
		this.ID=ID;
	}
	
	public int getID() {
		return ID;
	}
	
	public void LockArea(RobotInterface robot) {
		if(this.LocationIsAccessbile(robot)) {
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
	
	public boolean LocationIsAccessbile(RobotInterface robot) {
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
