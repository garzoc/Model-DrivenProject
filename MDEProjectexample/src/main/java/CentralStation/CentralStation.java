package CentralStation;

import java.awt.Point;
import java.awt.geom.Point2D;

import Interfaces.Interface;
import rover.MissionController;
import rover.RobotInterface;
import rover.Strategy;

public class CentralStation {
	private RobotInterface[] robots;
	private int numberOfAttachedRobots=0;
	private Interface gui;
	private int stationID;
	public final Environment environment;
	
	public CentralStation(Interface i, Environment finder,int maximumNumberOfRobots){
		stationID=0;
		robots=new RobotInterface[maximumNumberOfRobots];
		gui = i;
		this.environment = finder;
	}
	
	
	protected CentralStation(int ID,int maximumNumberOfRobots){
		stationID=ID;
		robots=new RobotInterface[maximumNumberOfRobots];
		environment=null;
	}
	
	public RobotInterface getRobot(int robotID) {
		return robots[robotID];	
	}
	
	public void attachNewRobot(RobotInterface robot) {
		if(robots.length!=numberOfAttachedRobots) {
			
			robots[numberOfAttachedRobots++]=robot;
		}
	}


	public int getNumberOfRobots() {
		return 0;
	}
	public int getID() {
		return this.stationID;
	}
	
	public void setID(int id) {
		this.stationID=id;
	}
	
	public void setRewardPoint(int rewardPoint,String robotID,Environment.AreaType pointSystem) {
		gui.onRewardPointRecieved(rewardPoint, robotID, pointSystem);
	}
	
	
/*	
	public int setMission(Strategies mission) {
		return 0;
	}
	
	public void setDestination(int roverID,Point p) {
		
	}
	
	
	public Point getRoverPosition(int robotId) {
		return new Point(0,0);
	}
	
	public Point isAtPostion(int robotId,Point p) {
		return new Point(0,0);
	}
	
	public void roverMoveTo(int robotID,Point position) {
		
	}
*/	
	
	
	
	
}
