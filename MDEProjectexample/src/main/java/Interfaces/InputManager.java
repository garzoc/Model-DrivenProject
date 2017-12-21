package Interfaces;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Scanner;

import CentralStation.GET;
import rover.Strategy;

public class InputManager implements Runnable{

	
	public LinkedList<String> split(String in,char splitter) {
		LinkedList<String> tmp=new LinkedList<String>();
		String currentString="";
		for(int i=0;i<in.length();i++) {
			if(in.charAt(i)==splitter) {
				tmp.add(currentString);
				currentString="";
			}else {
				currentString+=in.charAt(i);
			}
		}
		tmp.add(currentString);
			
		return tmp;
	}
	
	private String getValue(LinkedList<String>ls,String name) {
		for(int i=0;i<ls.size();i++) {
			//System.out.println("target "+name+" current "+((String)ls.get(i)));
			if(name.equals(ls.get(i))) {
				//System.out.println("good");
				return (String)ls.get(i+1);
			}
		}
		return null;
	}
	
	private double[] stringToDoubleArray(String arr) {
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

		double[] results = new double[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		        results[i] = Double.parseDouble(items[i]);
		    } catch (NumberFormatException nfe) {
		        //NOTE: write something here if you need to recover from formatting errors
		    };
		}
		return results;
	}
	
	
	public Point2D.Double[] createPathFromArray(String arr){
		double[] pointList=stringToDoubleArray(arr);
		Point2D.Double[] path=new Point2D.Double[pointList.length/2];
		for(int i=0;i<pointList.length;i+=2) {
			path[i/2]=new Point2D.Double(pointList[i], pointList[i+1]);
		}
		return path;
		
	}
		
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner( System.in );

	    // 2. Don't forget to prompt the user
		
	    System.out.println( "{command:changePath|robotID:3|path:[2.5,3,2.5,-3,7,-3]}" );
	    System.out.println( "{command:stop|robotID:2}");
	    System.out.println( "{command:stopALL}" );
	   // {new Point2D.Double(2.5,3), new Point2D.Double(2.5,-3), new Point2D.Double(7,-3)};

	    // 3. Use the Scanner to read a line of text from the user.
	   
	    while(true) {
	    		String input = scanner.nextLine();
	    		if(input!="stop") {
	    		LinkedList ls;
	    		ls=split(input,'{');
	    			if(ls.size()>1) {
	    				ls=split((String)ls.get(1),'}');
	    				System.out.println(ls.size());
	    				ls=split((String)ls.get(0),'|');
	    				int length=ls.size();
	    				for(int i=0;i<length;i++) {
	    					ls.addAll(split((String)ls.poll(),':'));//Unsafe type casting
	    				}
	    				
	    				switch(getValue(ls,"command")){
	    					case "stop":
	    						GET.CentralStation().getRobot(Integer.parseInt(getValue(ls,"robotID"))).termiateMission();
	    						break;
	    					case "changePath":
	    						Strategy x=new Strategy(createPathFromArray(getValue(ls,"path")));
	    						GET.CentralStation().getRobot(Integer.parseInt(getValue(ls,"robotID"))).overwriteMission(x);
	    						break;
	    					case "stopAll":
	    						for(int i=0;i<GET.CentralStation().getNumberOfRobots();i++)GET.CentralStation().getRobot(i).termiateMission();
	    						break;
	    					default:
	    						System.out.println("unrecognaized command");
	    					break;
	    				}
	    			}
	    
	    			// 4. Now, you can do anything with the input string that you need to.
	    			// Like, output it to the user.
	    			System.out.println(input);
	    		}
	    }
	   

	}

}
