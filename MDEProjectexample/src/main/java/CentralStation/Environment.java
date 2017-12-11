package CentralStation;

import java.awt.geom.Point2D;


public class Environment {
	
	public enum AreaType {
	    LOGICAL,
	    PHYSICAL,
	}
	
	private LocationController[] rooms;
	int numberOfAttachedControllers = 0;
	public Environment(int maxNumberOfController){
		rooms = new LocationController[maxNumberOfController];
	}
	
	public Environment(LocationController[] c){
		rooms = new LocationController[c.length];
		for(int i=0;i<c.length;i++) {
			this.attachNewController(c[i]);
		}
		
	}
	
	public LocationController getLocationController(Point2D.Double p) {
		for (int i=0;i<rooms.length;i++) {
			boolean w=p.getX() >rooms[i].getX() && p.getX() <rooms[i].getX()+ rooms[i].getWidth();
			boolean h=p.getY() >rooms[i].getY() && p.getY() <rooms[i].getY()+ rooms[i].getHeight();
			if(w && h) {
				return rooms[i];
			}
		}
		return null;
	}
	
	public LocationController getLocationControllerByType(Point2D.Double p,AreaType areaType) {
		for (int i=0;i<rooms.length;i++) {
			boolean w=p.getX() >rooms[i].getX() && p.getX() <rooms[i].getX()+ rooms[i].getWidth();
			boolean h=p.getY() >rooms[i].getY() && p.getY() <rooms[i].getY()+ rooms[i].getHeight();
			if(w && h && areaType == rooms[i].getAreaType()) {
				return rooms[i];
			}
		}
		return null;
	}
	
	public LocationController getLocationControllerByTypeOrder(Point2D.Double p) {
		LocationController lc=null;
		for (int i=0;i<rooms.length;i++) {
			boolean w=p.getX() >rooms[i].getX() && p.getX() <rooms[i].getX()+ rooms[i].getWidth();
			boolean h=p.getY() >rooms[i].getY() && p.getY() <rooms[i].getY()+ rooms[i].getHeight();
			if(w && h) {
				if(AreaType.LOGICAL==rooms[i].getAreaType()) {
					return rooms[i];
				}else {
					lc=rooms[i];
				}
			}
		}
		return lc;
	 
	}
	
	
	public LocationController getControllerByID(int ID) {
		if(ID<numberOfAttachedControllers && ID >=0) {
			return rooms[ID];
		}
		return null;
	}
	
	protected void attachNewControllers(LocationController[] c) {
		for(int i=0;i<c.length;i++) {
			attachNewController(c[i]);
		}
	}
	
	public boolean attachNewController(LocationController p) {
		if(numberOfAttachedControllers!=rooms.length) {
			p.setID(numberOfAttachedControllers);
			rooms[numberOfAttachedControllers++]=p;
			return true;
		}
		return false;
	}
}
