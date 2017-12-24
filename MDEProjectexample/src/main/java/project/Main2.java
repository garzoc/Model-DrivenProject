package project;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;


import CentralStation.Environment;
import CentralStation.Environment.AreaType;
import CentralStation.LocationController;
import CentralStation.GET;
import Interfaces.GUI;
import Interfaces.InputManager;

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
public class Main2 {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub

		EnvironmentDescription e = new EnvironmentDescription();
		
		Color c = Color.GRAY;
		Color red = Color.RED;
		
		Boundary w1 = new HorizontalBoundary(-5.0f, -5.0f, 5.0f, e, red);

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
		
		
		Set <Robot> robots = new HashSet<>();
		LocationController [] controllers = {
			new LocationController(-5,0,5,5,AreaType.PHYSICAL,"Room1",5),	
		};
		
		Robot robot1 = new Robot(new Point2D.Double(7, -3), "Robot 1");
		Robot robot2 = new Robot(new Point2D.Double(-7,-3 ), "Robot 2");
		Robot robot3 = new Robot(new Point2D.Double(-7, 3), "Robot 3");
		Robot robot4 = new Robot(new Point2D.Double(7, 3), "Robot 4");
		
		robots.add(robot1);
		robots.add(robot2);
		robots.add(robot3);
		robots.add(robot4);

		
		new GET(new GUI(robots, e), new Environment(controllers));
		
		GET.CentralStation().attachNewRobot(robot1);
		GET.CentralStation().attachNewRobot(robot2);
		GET.CentralStation().attachNewRobot(robot3);
		GET.CentralStation().attachNewRobot(robot4);

		//plans need to be rewritten with new coordinates
		Point2D.Double[] pl1 = {new Point2D.Double(3,-3), new Point2D.Double(-4,-3), new Point2D.Double(-7,-3)};
		Point2D.Double[] pl2 = {new Point2D.Double(-2.5,-3), new Point2D.Double(-2.5,3), new Point2D.Double(-7,3)};
		Point2D.Double[] pl3 = {new Point2D.Double(-3,3), new Point2D.Double(3,3), new Point2D.Double(7,3)};
		Point2D.Double[] pl4 = {new Point2D.Double(2.5,3), new Point2D.Double(2.5,-3), new Point2D.Double(7,-3)};
		

		Strategy plan1 = new Strategy(pl1);
		Strategy plan2 = new Strategy(pl2);
		Strategy plan3 = new Strategy(pl3);
		Strategy plan4 = new Strategy(pl4);
		
		
		GET.CentralStation().getRobot(0).beginMission(plan1);
		System.out.println("runnong second robot");
		GET.CentralStation().getRobot(1).beginMission(plan2);
		System.out.println("running thrid robot");
		GET.CentralStation().getRobot(2).beginMission(plan3);
		System.out.println("running forth robot");
	}

}