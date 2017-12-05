package project;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import CentralStation.CentralStation;
import CentralStation.Singleton;
import Interfaces.GUI;
import project.AbstractSimulatorMonitor;
import java.awt.Point;
import java.awt.geom.Point2D;

import rover.Robot;
import rover.Strategy;
import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		EnvironmentDescription e = new EnvironmentDescription();

		Color c = Color.GRAY;

		Boundary w1 = new HorizontalBoundary(-5.0f, -5.0f, 5.0f, e, c);

		Boundary w2 = new HorizontalBoundary(5.0f, -5.0f, 5.0f, e, c);

		Boundary w3 = new VerticalBoundary(5.0f, -2.0f, 2.0f, e, c);

		Boundary w4 = new VerticalBoundary(-5.0f, -2.0f, 2.0f, e, c);

		AbstractWall roomWall1 = new HorizontalWall(-0f, 2f, -2f, e, c);

		AbstractWall roomWall2 = new HorizontalWall(-0f, 5f, 3.5f, e, c);
		
		AbstractWall roomWall3 = new HorizontalWall(0f, -5f, -3.5f, e, c);

		AbstractWall roomWall4 = new VerticalWall(0f, -2f, 2f, e, c);

		AbstractWall roomWall5 = new VerticalWall(5f, -5f, -3.5f, e, c);
		
		AbstractWall roomWall6 = new VerticalWall(5f, 5f, 3.5f, e, c);
		
		AbstractWall roomWall7 = new VerticalWall(-5f, 5f, 3.5f, e, c);
		
		AbstractWall roomWall8 = new VerticalWall(-5f, -5f, -3.5f, e, c);
		
		AbstractWall roomWall9 = new VerticalWall(0f, -5f, -3.5f, e, c);
		
		AbstractWall roomWall10 = new VerticalWall(0f, 5f, 3.5f, e, c);

//		Set<RobotAvatar> robots = new HashSet<>();

//		RobotAvatar robot1 = new RobotAvatar(new Point(0, 0), "Robot 1");
//		RobotAvatar robot2 = new RobotAvatar(new Point(1, 3), "Robot 2");
		
		Set<Robot> robots = new HashSet<>();

		
		Robot robot1 = new Robot(new Point(-3, 7), "Robot 1");
		Robot robot2 = new Robot(new Point(-3, -7), "Robot 2");
		
		robots.add(robot1);
		robots.add(robot2);
//		robot2.setDestination(new Point(-3,5));
//		robot1.setDestination(new Point(-3,5));
		//AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);
		
		new Singleton(new GUI(robots, e));
		
		Singleton.getCentralStation().attachNewRobotIncremental(robot1);
		Singleton.getCentralStation().attachNewRobotIncremental(robot2);
		
		Point2D.Double[] pl1 = {new Point2D.Double(-3,3), new Point2D.Double(-3,-4), new Point2D.Double(-3,-7)};
		Point2D.Double[] pl2 = {new Point2D.Double(-3,-4), new Point2D.Double(3,-4), new Point2D.Double(3,-7)};
		//Point[] pl1 = {new Point(3,-3), new Point(-3,-3), new Point(-7,-3)};
//		Point[] pl2 =
//		pl[2] = new Point(3,3);
//		pl[3] = new Point(-3,3);
		Strategy plan1 = new Strategy(pl1);
		Strategy plan2 = new Strategy(pl2);
		
		Singleton.getCentralStation().getRobot(0).beginMission(plan1);
		System.out.println("runnong second robot");
		Singleton.getCentralStation().getRobot(1).beginMission(plan2);
		//robot2.setDestination(new Point(5,5));
		
/*	
		CentralStation k=Singleton.getCentralStation();
		k.setID(4);
			
			System.out.println(Singleton.getCentralStation().getID());
			Point[] x= {new Point(2,2),new Point(4,4)};
*/
		
			
		
		
		

	}
	
	

}
