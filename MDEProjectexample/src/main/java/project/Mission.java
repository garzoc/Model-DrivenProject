package project;

public class Mission extends Thread{
	private final Strategies strategy;
	private final RobotAvatar robot;
	
	Mission(Strategies strategy,RobotAvatar robot){
		this.strategy=strategy;
		this.robot=robot;
	}
	
	
	public void run() {
		Point[] missionPoints=strategy.getPoints();
		int missionProgress=0;
		while(missionProgress!=missionPoints.length) {
			if(robot.isAtPosition(missionPoints[missionProgress])){
				missionProgress++;
				robot.setDestination(missionPoints[missionProgress]);
			}
    	   
		}
		robot.onMissionComplete();
	}
}
