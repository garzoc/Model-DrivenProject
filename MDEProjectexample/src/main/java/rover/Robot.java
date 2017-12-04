package rover;

import java.awt.Point;
import java.awt.geom.Point2D;

import project.AbstractRobotSimulator;
import project.LocationController;

public class Robot extends AbstractRobotSimulator implements RobotInterface {

	MissionController m;
	
	public Robot(Point position, String name) {
		super(new project.Point(position.getX(), position.getY()), name);
		m=null;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginMission(Strategies str) {
		// TODO Auto-generated method stub
		if(m==null) {
			MissionController m=new MissionController(str,this);
			new Thread(m).start();
			m.run();
		}
		
	}
	
	

	@Override
	public boolean missionComplete(MissionController mission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkAreaAccesability(LocationController location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAtPosition(Point2D.Double p) {
		// TODO Auto-generated method stub
		Point2D.Double cp= this.getRobotPosition();
		double robotRadius=0.91;
		//cp.x=Math.round(cp.getX()*1000)/1000;
		//cp.y=Math.round(cp.getY()*1000)/1000;
		
		//cp.x=Math.round(cp.getX()*10)/10;
		//cp.y=Math.round(cp.getY()*10)/10;
		
		boolean o=cp.getX()-p.getX()>-robotRadius &&  cp.getX()-p.getX()<robotRadius && cp.getY()-p.getY()<robotRadius &&  cp.getY()-p.getY()>-robotRadius?true:false;
		
		//System.out.println("difference x is " +(cp.getX()-p.getX()));
		System.out.println("cp is "+cp.getX()+" p is "+p.getX()+" difference X is " +(cp.getX()-p.getX())+" is "+ (cp.getX()-p.getX()>-robotRadius &&  cp.getX()-p.getX()<robotRadius));
		System.out.println("cp is "+cp.getY()+" p is "+p.getY()+" difference y is " +(cp.getY()-p.getY())+" is "+ (cp.getY()-p.getY()>-robotRadius &&  cp.getY()-p.getY()<robotRadius));
		System.out.println(o);
		
		//return cp.getY()==p.getY()&&cp.getX()==p.getX();
		return o;
		
		
		//return super.isAtPosition(new project.Point(p.getX(), p.getY()));
	}

	

	@Override
	public void onMissionComplete() {
		// TODO Auto-generated method stub
		System.out.println("mission completed");
	}

	@Override
	public void setDestination(Point2D.Double p) {
		// TODO Auto-generated method stub
		
		super.setDestination(new project.Point(p.getX(), p.getY()));
		
	}

	@Override
	public Point2D.Double getRobotPosition() {
		// TODO Auto-generated method stub
		project.Point p=super.getPosition();
		
		return new Point2D.Double(((double)Math.round(p.getX()*1000))/1000,((double)Math.round(p.getZ()*1000)/1000));
	}





}
