package project;

import java.util.Set;

import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;
import simbad.sim.EnvironmentDescription;


public class SimulatorMonitor extends AbstractSimulatorMonitor<RobotAvatar> {

	public SimulatorMonitor( Set<RobotAvatar> robots, EnvironmentDescription e) {
		super(robots, e);
	}

	/*@Override
	public void update(AbstractRobotSimulator robot) {
		

	}*/

	@Override
	public void update(RobotAvatar arg0) {
		Strategies strategy=new Strategies(null);
		if(arg0.getName() =="Robot 2")
			arg0.beginMission(strategy);
		// TODO Auto-generated method stub
		
	}

}
