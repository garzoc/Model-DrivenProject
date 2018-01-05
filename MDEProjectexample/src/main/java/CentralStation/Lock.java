package CentralStation;

import rover.RobotInterface;

public class Lock {
	
	private volatile boolean waiting=false;
	private volatile RobotInterface robot;
	public Lock(RobotInterface robot) {
		this.robot=robot;
		this.waiting=true;
	}
	
	protected void lock() {
		System.out.println("lock "+robot.getID());
		while(waiting) {if(robot!=null &&robot.getMission()==Thread.currentThread())robot.pause(1);
			//System.out.println("lock "+robot.getID());
		}
			System.out.println("unlock");
		
	}
	
	protected void unlock() {
		this.waiting=false;
	}
}
