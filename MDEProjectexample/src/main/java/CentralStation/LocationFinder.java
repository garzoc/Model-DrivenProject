package CentralStation;

import java.awt.geom.Point2D;

public class LocationFinder {
	private LocationController[] controllers;
	int numberOfAttachedControllers = 0;
	public LocationFinder(int maxNumberOfController){
		controllers = new LocationController[maxNumberOfController];
	}
	
	public LocationFinder(LocationController[] c){
		controllers = new LocationController[c.length];
		for(int i=0;i<c.length;i++) {
			this.attachNewController(c[i]);
		}
		
	}
	
	public LocationController getLocationContoller(Point2D.Double p) {
		for (int i=0;i<controllers.length;i++) {
			boolean w=p.getX() >controllers[i].getX() && p.getX() <controllers[i].getX()+ controllers[i].getWidth();
			boolean h=p.getY() >controllers[i].getY() && p.getY() <controllers[i].getY()+ controllers[i].getHeight();
			if(w && h) {
				return controllers[i];
			}
		}
		return null;
	}
	
	public void attachNewControllers(LocationController[] c) {
		for(int i=0;i<c.length;i++) {
			attachNewController(c[i]);
		}
	}
	
	public boolean attachNewController(LocationController p) {
		if(numberOfAttachedControllers!=controllers.length) {
			p.setID(numberOfAttachedControllers);
			controllers[numberOfAttachedControllers++]=p;
			return true;
		}
		return false;
	}
}
