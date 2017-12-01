package project;

import CentralStation.Singleton;
import project.AbstractRobotSimulator;
import project.Point;

public class RobotAvatar extends AbstractRobotSimulator {
	
	//private Mission mission;
	
	public RobotAvatar(Point position, String name) {
		super(position, name);
		//mission=null;
	}
	
	/*public void beginMission(Strategies strategy) {
		if(!this.hasMission()) {
			mission=new Mission(strategy,this);
	
			mission.start();
		}
		
		Singleton.LockAccess();
			Singleton.getCentralStation();
		Singleton.unlockAccess();
	}
	
	public void onMissionComplete() {
		mission=null;
	}
	
	public boolean hasMission(){
		return mission!=null;
	}*/


	@Override
	public String toString() {
		return "Robot " + this.getName();
	}

}
