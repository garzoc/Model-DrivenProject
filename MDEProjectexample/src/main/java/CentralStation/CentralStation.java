package CentralStation;

import java.awt.Point;

import project.Mission;
import project.RobotAvatar;

public class CentralStation {
	private RobotAvatar[] robotAvatars;
	private int stationID;
	
	public CentralStation(){
		stationID=0;
		robotAvatars=null;
	}
	
	protected CentralStation(int ID,int maximumNumberOfRobots){
		stationID=ID;
		robotAvatars=new RobotAvatar[maximumNumberOfRobots];
	}
	
	public Point getRoverPosition(int robotId) {
		return new Point(0,0);
	}
	
	public void roverMoveTo(int robotID,Point position) {
		
	}
	
	public void attachNewRobot(RobotAvatar robot) {
		
	}
	
	public int getNumberOfRobots() {
		return 0;
	}
	
	public int setMission(int robotID,Mission mission) {
		return 0;
	}
	
	protected int getID() {
		return this.stationID;
	}
	
	
}
