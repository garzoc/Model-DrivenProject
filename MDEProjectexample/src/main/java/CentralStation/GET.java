package CentralStation;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import CentralStation.Environment.AreaType;
import Interfaces.Interface;
import project.Point;
import rover.RobotInterface;;

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
	
	private synchronized static Lock tryAcquireLock(RobotInterface robot) {
		if(stateLocked) {
			Lock l=new Lock(robot);
			locks.add(l);
			return l;
		}
		stateLocked=true;
		return null;
	}
	
	public static void Lock(RobotInterface robot) {
		
		//System.out.println("trying lock "+robot.getID());
			Lock l;
			if((l=tryAcquireLock(robot))!=null) { 
				//System.out.println("lock "+robot.getID());
				l.lock();
			}
		
		
		
	}
	
	
	public synchronized static void Unlock() {	
		//System.out.println("try unlocking");
		Lock lock;
		if(null!=(lock=locks.poll())){
			lock.unlock();
		}else {
			stateLocked=false;
		}
	}
	//get the the locationController 
	public static LocationController locationByOrder(Point2D.Double location) {
		return GET.CentralStation().environment.getLocationControllerByTypeOrder(location);
	}
	//get the location by specified type of area (either logical or physical) 
	public static LocationController locationByType(Point2D.Double location, AreaType area) {
		return GET.CentralStation().environment.getLocationControllerByType(location,area);
	}
	//get the LocationController by specified id
	public static LocationController locationByID(int roomID) {
		return GET.CentralStation().environment.getControllerByID(roomID);
	}
	
	
	
	
}
