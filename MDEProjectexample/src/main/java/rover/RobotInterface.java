package rover;
import project.*;

public interface RobotInterface {
	
	void beginMissions(Strategies str);
	
	boolean missionComplete(Mission mission);
	
	boolean checkAreaAccesability(LocationController location);
}
