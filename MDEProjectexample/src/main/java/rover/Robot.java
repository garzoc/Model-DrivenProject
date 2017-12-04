package rover;

import java.awt.Point;

import project.AbstractRobotSimulator;
import project.LocationController;

public class Robot extends AbstractRobotSimulator implements RobotInterface {

	public Robot(Point position, String name) {
		super(new project.Point(position.getX(), position.getY()), name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginMission(Strategies str) {
		// TODO Auto-generated method stub
		MissionController m=new MissionController(str,this);
		m.start();
		
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
		return super.isAtPosition(new project.Point(p.getX(), p.getY()));
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
