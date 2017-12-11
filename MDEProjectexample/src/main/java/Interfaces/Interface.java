package Interfaces;

import CentralStation.Environment;

public interface Interface {
	
	void onRewardPointRecieved(int rewardPoints, String robotID,Environment.AreaType pointSystem);

}
