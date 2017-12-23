package Interfaces;
import java.awt.Point;
import java.awt.geom.Point2D;

import rover.Robot;
import rover.RobotInterface;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import CentralStation.Environment;
import CentralStation.Environment.PointSystem;
import CentralStation.GET;
import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;

import rover.Strategy;
import simbad.sim.EnvironmentDescription;
public class GUI extends AbstractSimulatorMonitor implements Interface{
	
	
	public GUI( Set<Robot> robots, EnvironmentDescription e) {
		super(robots, e);
		
		
	}
	

	@Override
	public void update(AbstractRobotSimulator arg0) {
		
		
	}


	public void onRewardPointRecieved(int rewardPoints, RobotInterface robot,PointSystem pointSystem) {
		
		
	}


	@Override
	public void init() {
		InputManager i=new InputManager();
		i.run();
	}
	

	
	

}
