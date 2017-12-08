package CentralStation;

import java.awt.geom.Point2D;

public class Environment {
	private RoomController[] rooms;
	int numberOfAttachedControllers = 0;
	public Environment(int maxNumberOfController){
		rooms = new RoomController[maxNumberOfController];
	}
	
	public Environment(RoomController[] c){
		rooms = new RoomController[c.length];
		for(int i=0;i<c.length;i++) {
			this.attachNewController(c[i]);
		}
		
	}
	
	public RoomController getLocationController(Point2D.Double p) {
		for (int i=0;i<rooms.length;i++) {
			boolean w=p.getX() >rooms[i].getX() && p.getX() <rooms[i].getX()+ rooms[i].getWidth();
			boolean h=p.getY() >rooms[i].getY() && p.getY() <rooms[i].getY()+ rooms[i].getHeight();
			if(w && h) {
				return rooms[i];
			}
		}
		return null;
	}
	
	
	public RoomController getControllerByID(int ID) {
		if(ID<numberOfAttachedControllers && ID >=0) {
			return rooms[ID];
		}
		return null;
	}
	
	protected void attachNewControllers(RoomController[] c) {
		for(int i=0;i<c.length;i++) {
			attachNewController(c[i]);
		}
	}
	
	public boolean attachNewController(RoomController p) {
		if(numberOfAttachedControllers!=rooms.length) {
			p.setID(numberOfAttachedControllers);
			rooms[numberOfAttachedControllers++]=p;
			return true;
		}
		return false;
	}
}
