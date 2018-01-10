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

	LinkedList<Integer> pointsA=new LinkedList<Integer>();
	LinkedList<Integer> pointsB=new LinkedList<Integer>();
	public void onRewardPointRecieved(int rewardPoints, RobotInterface robot,PointSystem pointSystem) {
		try {
		if(robot!=null) {
		if(GET.CentralStation()!=null) {
			while(GET.CentralStation().getNumberOfRobots()!=pointsA.size() || GET.CentralStation().getNumberOfRobots()!=pointsB.size()) {
				if(GET.CentralStation().getNumberOfRobots()>pointsA.size()) pointsA.add(new Integer(0));
				if(GET.CentralStation().getNumberOfRobots()>pointsB.size()) pointsB.add(new Integer(0));
			}
		}
		if(pointSystem ==PointSystem.A && robot.getID()<pointsA.size())pointsA.set(robot.getID(), rewardPoints);
		if(pointSystem ==PointSystem.B && robot.getID()<pointsA.size())pointsB.set(robot.getID(), rewardPoints);
			
		int sum=0;
		for(int i=0;i<pointsA.size();i++){
			if(pointsA.get(i)!=0)sum+=pointsA.get(i);
		}
		//System.out.println("System A has "+sum);
		for(int i=0;i<pointsB.size();i++){
			if(pointsB.get(i)!=0)sum+=pointsB.get(i);
		}
		//System.out.println("System B has "+sum);
		}
		
		}catch(Exception e) {}
		
	}


	@Override
	public void init() {
		InputManager i=new InputManager();
		i.run();
	}
	

	
	

}
