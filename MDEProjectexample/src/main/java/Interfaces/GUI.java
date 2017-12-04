package Interfaces;
import java.awt.Point;
import java.util.Set;

import CentralStation.Singleton;
import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;

import project.RobotAvatar;
import rover.Strategies;
import simbad.sim.EnvironmentDescription;
public class GUI extends AbstractSimulatorMonitor implements Interface{

	
	public GUI( Set<RobotAvatar> robots, EnvironmentDescription e, Strategies plan) {
		super(robots, e);
		
		Point[] pl1 = {new Point(3,-3), new Point(-3,-3), new Point(-7,-3)};
//		Point[] pl2 =
//		pl[2] = new Point(3,3);
//		pl[3] = new Point(-3,3);
		Strategies plan1 = new Strategies(pl1);
		
		Singleton.getCentralStation().getRobot(0).beginMission(plan1);
		
		
	}
	

	@Override
	public void update(AbstractRobotSimulator arg0) {
		

		
	}


	public void onRewardPointRecieved(int rewardPoints, int robotID) {
		
		
	}
	
	

}
