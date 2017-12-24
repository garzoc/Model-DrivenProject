package CentralStation;

import rover.RobotInterface;

public class Lock {
	
	private boolean waiting=false;
	private  volatile char tick=0;
	private RobotInterface robot;
	public Lock(RobotInterface robot) {
		robot=robot;
	}
	
	protected void lock() {
		this.waiting=true;
		while(waiting) { 
			tick=2;
			if(robot!=null &&robot.getMission()==Thread.currentThread())robot.pause(1);
		}
		
	}
	
	protected void unlock() {
		this.waiting=false;
	}
}
