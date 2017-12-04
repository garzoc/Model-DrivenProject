package rover;

import java.awt.Point;

public class Strategies {
	
	Point[] unsortedPoints;
	Point[] optimizedPath;
	
	Strategies (Point[] path){
		unsortedPoints=path;
	}
	
	public Point[] getPath() {
		
		return optimizedPath;
	}
	
	public Point[] getOriginalPoints() {
		return unsortedPoints;
	}
}
