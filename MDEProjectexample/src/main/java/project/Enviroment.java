package project;

import java.util.HashMap;

public class Enviroment {
	static HashMap map = new HashMap(4,(float) 0.75);
	
	Enviroment(RobotAvatar[] roverList){
		
	}
	
	public void update(String id,int value) {
		
		map.put(id, value);
	}
	
	boolean pointIsAccessible(Point targetPoint,Point srcPoint) {
		return false;	
	}
	
	Point[] getPath(Point targetPoint,Point srcPoint) {
		return null;	
	}
	
	boolean isValidPoint(Point point) {
		return false;	
	}
}
