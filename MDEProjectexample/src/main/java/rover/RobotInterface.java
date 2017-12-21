package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.GET;
public interface RobotInterface {
	
	/*class ROVER_DATA {
		private int ID=-1;
		protected MissionController mission;
		
    }
	
	ROVER_DATA rover=new ROVER_DATA();
	
	default void setID(int ID) {
		if(rover.ID<0) {
			rover.ID=ID;
		}
	};
	
	default public int getRobotID() {
		return rover.ID;
	}*/
	
	
	
	//public void overwriteMission(Strategy str);
	//public void termiateMission();
	
	/*default public void overwriteMission(Strategy str){
		rover.mission.forceTerminat();
		try {
			rover.mission.join();
			rover.mission=new MissionController(str,this);
			rover.mission.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	default public void termiateMission() {
		rover.mission.forceTerminat();
		try {
			rover.mission.join();
			this.setDestination(this.getRobotPosition());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}*/
	
	public int getID();
	public void setID(int ID);
	
	public MissionController getMission();

	public void overwriteMission(Strategy str);
	
	default public void termiateMission() {//only call from external class not running on the same thread
		if(getMission()!=null) {
			getMission().forceTerminat();
			//check if the method called was from an internal instance of MissionContoller
			if(Thread.currentThread()!=getMission()){//The mission controller has thread as a superclass
				try {
					getMission().join();
					setDestination(getRobotPosition());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public int getRobotName();

	public void beginMission(Strategy str);
	
	
	public boolean isAtPosition(Point2D.Double p);
	public void setDestination(Point2D.Double p);
	
	public Point2D.Double getRobotPosition();
	
	/*
	 * Event Triggers
	 * */
	
	
	public void onPhysicalRoomSwitched(int newRoomID,int oldRoomID);
	public void onLogicalRoomSwitched(int newRoomID,int oldRoomID);
	public void onPhysicalAreaEnter(int newRoomID);
	public void onPhysicalAreaLeave(int oldRoomID);
	public void onLogicalAreaEnter(int newRoomID);
	public void onLogicalAreaLeave(int oldRoomID);
	public void onMissionBegin();
	public void missionUpdate();
	public void onMissionComplete();	
	default public void onMissionTerminated() {
		setDestination(getRobotPosition());
	}
}
