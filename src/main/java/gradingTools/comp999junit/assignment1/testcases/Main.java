package gradingTools.comp999junit.assignment1.testcases;

import java.util.Scanner;

import bus.uigen.ObjectEditor;

public class Main {
	public static void main(String args[]) {
		Scanner aScanner = new Scanner (System.in);
		String anXLine = aScanner.nextLine();
		String aYLine = aScanner.nextLine();
		int anX = Integer.parseInt(anXLine);
		int aY = Integer.parseInt(aYLine);
		Point aPoint =  new CartesianPoint (anX, aY);
		System.out.println ("" + aPoint.getRadius());
		System.out.println ("" + aPoint.getAngle());
//		
	}

}
