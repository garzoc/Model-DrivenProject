package Interfaces;
import java.util.Set;

import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;
import project.Point;
import project.RobotAvatar;
import simbad.sim.EnvironmentDescription;
public class GUI extends AbstractSimulatorMonitor implements Interface{

	public GUI( Set<RobotAvatar> robots, EnvironmentDescription e) {
		super(robots, e);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void update(AbstractRobotSimulator arg0) {
		// TODO Auto-generated method stub
		
	}


	public void onRewardPointRecieved() {
		
		
	}

}
