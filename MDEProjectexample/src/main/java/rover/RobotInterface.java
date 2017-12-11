package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.Lock;
import CentralStation.LocationController;
public interface RobotInterface {
	
	
	
	public String getRobotID();

	public void beginMission(Strategy str);
	
	
	public boolean isAtPosition(Point2D.Double p);
	public void setDestination(Point2D.Double p);
	public Point2D.Double getRobotPosition();
	public void missionUpdate();
	
	public void onPhysicalRoomSwitched(int newRoomID,int oldRoomID);
	public void onLogicalRoomSwitched(int newRoomID,int oldRoomID);
	public void onPhysicalAreaLeave(int oldRoomID);
	public void onLogicalAreaLeave(int oldRoomID);
	public void onPhysicalAreaEnter(int newRoomID);
	public void onLogicalAreaEnter(int newRoomID);
	public void onMissionComplete();
}
