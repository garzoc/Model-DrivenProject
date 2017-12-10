package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.Lock;
import CentralStation.LocationController;
public interface RobotInterface {
	
	

	
	public void beginMission(Strategy str);
	

	
	public boolean isAtPosition(Point2D.Double p);
	public void setDestination(Point2D.Double p);
	public Point2D.Double getRobotPosition();
	
	public void onNewRoomEnter(int newRoomID,int oldRoomID);
	public void onAreaLeave(int oldRoomID);
	public void onAreaEnter(int newRoomID);
	public void onMissionComplete();
}
