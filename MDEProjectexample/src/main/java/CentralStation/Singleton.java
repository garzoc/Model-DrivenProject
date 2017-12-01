package CentralStation;

import java.util.LinkedList;

import Interfaces.GUI;

public class Singleton {
	
	private static CentralStation c;
	private static LinkedList<Object> threads;
	private static boolean stateLocked;
	public Singleton(GUI m){
		c=new CentralStation(2,6);
		threads=new LinkedList<Object>();
		stateLocked=false;
		
	}
	public static CentralStation getCentralStation() {
		if(!stateLocked) {
			return c;
		}
		return null;
	}
	
	public static void LockAccess() {
		Object o=new Object();
		if(stateLocked) {
			threads.add(o);
			try {
				o.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stateLocked=true;
	}
	
	public static void unlockAccess() {
		stateLocked=false;
		Object thread;
		if(null!=(thread=threads.poll())){
			thread.notify();
		}
	}
	
	
	
	
	
}
