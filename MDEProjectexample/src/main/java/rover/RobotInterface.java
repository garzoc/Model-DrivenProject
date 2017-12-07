package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.Lock;
import project.LocationController;

public interface RobotInterface {
	
	

	
	void beginMission(Strategy str);
	

	
	boolean checkAreaAccesability(LocationController location);
	boolean isAtPosition(Point2D.Double p);
	void setDestination(Point2D.Double p);
	
	void onNewRoomEnter();
	
	Point2D.Double getRobotPosition();
	
	void onMissionComplete();
}
