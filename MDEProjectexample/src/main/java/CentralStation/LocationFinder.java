package CentralStation;

import java.awt.geom.Point2D;

public class LocationFinder {
	private LocationController[] controllers;
	int numberOfAttachedControllers = 0;
	public LocationFinder(int maxNumberOfController){
		controllers = new LocationController[maxNumberOfController];
	}
	
	public LocationFinder(LocationController[] c){
		controllers = c;
		numberOfAttachedControllers = c.length;
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
	
	public boolean attachNewController(LocationController p) {
		if(numberOfAttachedControllers!=controllers.length) {
			controllers[numberOfAttachedControllers++]=p;
			return true;
		}
		return false;
	}
}
