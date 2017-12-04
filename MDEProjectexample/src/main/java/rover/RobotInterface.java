package rover;

import java.awt.Point;

import project.LocationController;

public interface RobotInterface {
	

	
	void beginMission(Strategies str);
	
	boolean missionComplete(MissionController mission);
	
	boolean checkAreaAccesability(LocationController location);
	boolean isAtPosition(Point p);
	void setDestination(Point p);
	
	void onMissionComplete();
}
