package CentralStation;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import CentralStation.Environment.AreaType;
import Interfaces.Interface;
import project.Point;;

public class GET {
	
	private static CentralStation c;
	private static volatile LinkedList<Lock> locks;
	private static volatile boolean stateLocked;
	
	public GET(Interface i, Environment finder){

		c=new CentralStation(i, finder,4);
		locks=new LinkedList<Lock>();
		stateLocked=false;
		
	}
	
	public static CentralStation CentralStation() {
		//if(!stateLocked) {
			return c;
		//}
		//return null;
	}
	
	public static void Lock() {
		//System.out.println(stateLocked);
		if(stateLocked) {
			Lock l=new Lock();
			locks.add(l);
			l.lock();	
		}
		stateLocked=true;
	}
	
	
	public static void Unlock() {
		stateLocked=false;
		Lock lock;
		if(null!=(lock=locks.poll())){
			lock.unlock();
		}
	}
	
	public static LocationController locationByOrder(Point2D.Double location) {
		return GET.CentralStation().environment.getLocationControllerByTypeOrder(location);
	}
	
	public static LocationController locationByType(Point2D.Double location, AreaType area) {
		return GET.CentralStation().environment.getLocationControllerByType(location,area);
	}
	
	public static LocationController locationByID(int roomID) {
		return GET.CentralStation().environment.getControllerByID(roomID);
	}
	
	
	
	
}
