package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Strategies {
	
	Point2D.Double[] unsortedPoints;
	Point2D.Double[] optimizedPath;
	
	public Strategies (Point2D.Double[] path){
		unsortedPoints=path;
	}
	
	public Point2D.Double[] getPath() {
		
		return optimizedPath;
	}
	
	public Point2D.Double[] getOriginalPoints() {
		return unsortedPoints;
	}
}
