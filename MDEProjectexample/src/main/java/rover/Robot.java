package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import project.AbstractRobotSimulator;
import CentralStation.LocationController;

public class Robot extends AbstractRobotSimulator implements RobotInterface {

	MissionController m;
	
	public Robot(Point2D.Double position, String name) {
		super(convertCoord(position), name);
		m=null;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginMission(Strategy str) {
		// TODO Auto-generated method stub
		if(m==null) {
			MissionController m=new MissionController(str,this);
			m.start();
			//new Thread(m).start();
			//m.run();
	
		}
		
	}
	

	@Override
	public boolean isAtPosition(Point2D.Double p) {
		// TODO Auto-generated method stub
		Point2D.Double cp= this.getRobotPosition();
		double robotRadius=0.61;
		//cp.x=Math.round(cp.getX()*1000)/1000;
		//cp.y=Math.round(cp.getY()*1000)/1000;

		
		return cp.getX()-p.getX()>-robotRadius &&  cp.getX()-p.getX()<robotRadius && cp.getY()-p.getY()<robotRadius &&  cp.getY()-p.getY()>-robotRadius;
		
		//System.out.println("cp is "+cp.getX()+" p is "+p.getX()+" difference X is " +(cp.getX()-p.getX())+" is "+ (cp.getX()-p.getX()>-robotRadius &&  cp.getX()-p.getX()<robotRadius));
		//System.out.println("cp is "+cp.getY()+" p is "+p.getY()+" difference y is " +(cp.getY()-p.getY())+" is "+ (cp.getY()-p.getY()>-robotRadius &&  cp.getY()-p.getY()<robotRadius));
		
		
		//return super.isAtPosition(new project.Point(p.getX(), p.getY()));
	}

	

	@Override
	public void onMissionComplete() {
		// TODO Auto-generated method stub
		System.out.println("mission completed");
	}
	
	@Override
	public void onNewRoomEnter(int roomID) {
		// TODO Auto-generated method stub
		//System.out.println("hi a new room"+lc.getID());
	}

	@Override
	public void setDestination(Point2D.Double p) {
		// TODO Auto-generated method stub
		
		super.setDestination(convertCoord(p));
		
	}
	
	private static project.Point convertCoord(Point2D.Double p){
		return new project.Point(-p.getY(),-p.getX());
	}
	

	@Override
	public Point2D.Double getRobotPosition() {
		// TODO Auto-generated method stub
		project.Point p=super.getPosition();
		
		return new Point2D.Double(-((double)Math.round(p.getZ()*1000)/1000),-((double)Math.round(p.getX()*1000))/1000);
	}

	





}
