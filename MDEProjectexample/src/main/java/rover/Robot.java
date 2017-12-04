package rover;

import java.awt.Point;

import project.AbstractRobotSimulator;
import project.LocationController;

public class Robot extends AbstractRobotSimulator implements RobotInterface {

	public Robot(project.Point position, String name) {
		super(position, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginMission(Strategies str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean missionComplete(MissionController mission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkAreaAccesability(LocationController location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAtPosition(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void onMissionComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDestination(Point p) {
		// TODO Auto-generated method stub
		super.setDestination(new project.Point(p.getX(), p.getY()));
		
	}





}
