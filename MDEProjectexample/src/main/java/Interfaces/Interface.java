package Interfaces;

import CentralStation.Environment;
import CentralStation.Environment.PointSystem;
import rover.RobotInterface;

public interface Interface {
	
	void onRewardPointRecieved(int rewardPoints, RobotInterface robot,PointSystem pointSystem);
	void init();


}
