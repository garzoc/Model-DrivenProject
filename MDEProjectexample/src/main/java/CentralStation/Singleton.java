package CentralStation;

import project.SimulatorMonitor;

public class Singleton {
	private static CentralStation c;
	
	Singleton(SimulatorMonitor m){
		c=new CentralStation(2,6);
		
	}
	static CentralStation getCentralStation() {
		return c;
	}
	
	
	
}
