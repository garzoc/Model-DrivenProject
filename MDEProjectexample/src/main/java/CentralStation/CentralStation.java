package CentralStation;

import java.awt.Point;

import Interfaces.Interface;
import project.RobotAvatar;
import rover.MissionController;
import rover.RobotInterface;
import rover.Strategies;

public class CentralStation {
	private RobotInterface[] robots;
	private Interface gui;
	private int stationID;
	
	public CentralStation(Interface m,int stationID,int maximumNumberOfRobots){
		stationID=0;
		robots=null;
		gui=m;
	}
	
	public CentralStation(){
		stationID=0;
		robots=null;
	}
	
	protected CentralStation(int ID,int maximumNumberOfRobots){
		stationID=ID;
		robots=new RobotInterface[maximumNumberOfRobots];
	}
	
	public RobotInterface getRobot(int robotID) {
		return robots[robotID];
		
	}
	
	public void attachNewRobot(RobotInterface robot) {
		
	}
	
	public void attachNewRobotIncremental(RobotInterface robot) {
		
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
	
	protected void setRewardPoint(int rewardPoint,int robotID) {
		gui.onRewardPointRecieved(rewardPoint, robotID);
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
