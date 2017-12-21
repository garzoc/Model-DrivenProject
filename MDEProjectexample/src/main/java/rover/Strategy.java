package rover;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import CentralStation.Environment.AreaType;
import CentralStation.GET;
import CentralStation.LocationController;

public class Strategy {
	
	Point2D.Double[] unsortedPoints;
	Point2D.Double[] optimizedPath;
	int[] pathsByRooms;
	
	public Strategy (Point2D.Double[] path){
		unsortedPoints=optimizedPath=path;
	}
	
	public Point2D.Double[] getPath() {
		return optimizedPath;
	}
	
	public Point2D.Double[] getOriginalPoints() {
		return unsortedPoints;
	}
	/*
	 * this only works as long as the robot has a mission point in each room it passes
	 * and it only accounts for physical areas, logical are ignored
	 * */
	public boolean createPathByRoom() {
		List<Integer> tmp=new ArrayList<Integer>();
		int oldPhysicalRoomID=-1;
		LocationController lc;
		for(int i=0;i<getPath().length;i++) {
			lc=GET.locationByType(getPath()[i], AreaType.PHYSICAL);
			if(lc !=null) {
				tmp.add(lc.getID());
				oldPhysicalRoomID=lc.getID();
			}
		}
		pathsByRooms=new int[tmp.toArray().length];
		int counter=0;
		for(Object i:tmp.toArray()) {
			pathsByRooms[counter++]=(int)i;
		}
		
		return true;
	}
	
	public int[] getPathByRoom() {
		return pathsByRooms;
	}
}
