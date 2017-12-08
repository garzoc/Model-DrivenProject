package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.Lock;
import CentralStation.LocationController;
public interface RobotInterface {
	
	

	
	void beginMission(Strategy str);
	

	
	boolean isAtPosition(Point2D.Double p);
	void setDestination(Point2D.Double p);
	Point2D.Double getRobotPosition();
	
	void onNewRoomEnter(int roomID);
	void onMissionComplete();
}
