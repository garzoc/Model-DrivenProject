package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import CentralStation.Environment.AreaType;
import CentralStation.GET;
public interface RobotInterface {
	
	

	public int getID();
	public void setID(int ID);
	
	public MissionController getMission();

	public void overwriteMission(Strategy str);
	
	default public void termiateMission() {
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
	
	@SuppressWarnings("static-access")
	default public void pause(long millis) {
			try {
				if(getMission()!=null && Thread.currentThread()!=getMission()) {
					getMission().sleep(millis);
					setDestination(getRobotPosition());
				}else {
					setDestination(getRobotPosition());
					getMission().sleep(millis);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
	}
	
	
	
	public String getRobotName();

	public void beginMission(Strategy str);
	
	
	public boolean isAtPosition(Point2D.Double p);
	public void setDestination(Point2D.Double p);
	
	public Point2D.Double getRobotPosition();
	
	/*
	 * Event Triggers
	 * */
	public void onRoomSwitched(int newRoomID,int oldRoomID,AreaType areatype);
	public void onAreaEnter(int newRoomID,AreaType areatype);
	public void onAreaLeave(int oldRoomID,AreaType areatype);

	public void onMissionBegin();
	public void missionUpdate();
	public void onMissionComplete();	
	default public void onMissionTerminated() {
		setDestination(getRobotPosition());
	}
}
