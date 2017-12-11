package CentralStation;

import java.awt.geom.Point2D;

import CentralStation.Environment.AreaType;

public class LocationController {
	private Point2D.Double[] boundries;
	private boolean stateLocked=false;
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
	
	public void LockArea() {
		stateLocked = true;
	}
	public void UnlockArea() {
		stateLocked = false;
	}
	
	public boolean LocationIsAccessbile() {
		return !stateLocked;
	}
	
	public void robotOnEnter() {
		numberOfRobotsInside ++;
	}
	public void robotOnLeave() {
		numberOfRobotsInside --;
	}
	
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
