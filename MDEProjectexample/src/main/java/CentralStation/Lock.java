package CentralStation;

public class Lock {
	
	private boolean waiting=false;
	private  volatile char tick=0;
	
	protected void lock() {
		this.waiting=true;
		
		while(waiting) tick=2;
		
	}
	
	protected void unlock() {
		this.waiting=false;
	}
}
