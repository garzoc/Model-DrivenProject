package CentralStation;

import java.awt.geom.Point2D;

public class LocationController {
	private Point2D.Double[] boundries;
	private boolean stateLocked=false;
	private int numberOfRobotsInside=0;
	private int ID;
	
	
	
	public LocationController(double x,double y,double width,double height){
		Point2D.Double[] b= {new Point2D.Double(x,y),new Point2D.Double(width,height)};
		this.boundries=b;
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
	public double getX() {
		return boundries[0].getX();
	}
	
	public double getY() {
		return boundries[0].getY();
	}
	
	public double getWidth() {
		return boundries[1].getX();
	}
	
	public double getHeight() {
		return boundries[1].getY();
	}
	

}
