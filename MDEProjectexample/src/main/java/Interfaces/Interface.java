package Interfaces;

import CentralStation.Environment;
import rover.RobotInterface;

public interface Interface {
	
	void onRewardPointRecieved(int rewardPoints, RobotInterface robot,Environment.AreaType pointSystem);
	void init();


}
