package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import project.LocationController;

public interface RobotInterface {
	

	
	void beginMission(Strategies str);
	
	boolean missionComplete(MissionController mission);
	
	boolean checkAreaAccesability(LocationController location);
	boolean isAtPosition(Point2D.Double p);
	void setDestination(Point2D.Double p);
	Point2D.Double getRobotPosition();
	
	void onMissionComplete();
}
