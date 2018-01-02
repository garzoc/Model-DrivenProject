package CentralStation;

import rover.RobotInterface;

public class Lock {
	
	private volatile boolean waiting=false;
	private volatile RobotInterface robot;
	public Lock(RobotInterface robot) {
		robot=robot;
	}
	
	protected void lock() {
		this.waiting=true;
		while(waiting) if(robot!=null &&robot.getMission()==Thread.currentThread())robot.pause(1);
		
		
	}
	
	protected void unlock() {
		this.waiting=false;
	}
}
