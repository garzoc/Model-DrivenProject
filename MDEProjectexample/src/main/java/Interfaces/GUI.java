package Interfaces;
import java.awt.Point;
import rover.Robot;
import java.util.Set;


import CentralStation.Singleton;
import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;

import project.RobotAvatar;
import rover.Strategies;
import simbad.sim.EnvironmentDescription;
public class GUI extends AbstractSimulatorMonitor implements Interface{

	
	public GUI( Set<Robot> robots, EnvironmentDescription e) {
		super(robots, e);
		

		
	}
	

	@Override
	public void update(AbstractRobotSimulator arg0) {
		

		
	}


	public void onRewardPointRecieved(int rewardPoints, int robotID) {
		
		
	}
	
	

}
