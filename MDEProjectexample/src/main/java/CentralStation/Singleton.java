package CentralStation;

import java.util.LinkedList;

import Interfaces.Interface;
import project.Point;;

public class Singleton {
	
	private static CentralStation c;
	private static LinkedList<Thread> threads;
	private static boolean stateLocked;
	public Singleton(Interface i){
		c=new CentralStation(i,1,4);
		threads=new LinkedList<Thread>();
		stateLocked=false;
		
		/*Point s=new Point(1,1);
		set(s);
		System.out.println(s.getX());*/
		
	}
	
	/* public void set(Point p) {
		p.setX(4);	
	}*/
	public static CentralStation getCentralStation() {
		//if(!stateLocked) {
			return c;
		//}
		//return null;
	}
	
	public static void LockAccess() {
		Thread t=Thread.currentThread();
		if(stateLocked) {
			threads.add(t);
			try {
				t.wait();
				stateLocked=true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void unlockAccess() {
		stateLocked=false;
		Thread thread;
		if(null!=(thread=threads.poll())){
			thread.notify();
		}
	}
	
	
	
	
	
}
