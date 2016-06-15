import util.annotations.Explanation;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import bus.uigen.ObjectEditor;
@Explanation("Uses Cartesian representation.")
@Tags({"cartesian", "point"})
public class ACartesianPoint implements Point {	
	protected int x, y;
	public ACartesianPoint(int theX, int theY) {
		x = theX;
		y = theY;
	}
	public ACartesianPoint(double theRadius, double theAngle) {
		x =  (int) (theRadius*Math.cos(theAngle));
		y = (int) (theRadius*Math.sin(theAngle));
	}
	public int getX() { return x; }
	public int getY() { return y; } 	
	public double getAngle() { 
	//	return Math.atan2(y, x); 
		return 0;
	}
	@Tags({"radius", "getter"})	
	public double getRadius() { 
		return Math.sqrt(x*x + y*y); 
//		return 0;
	}	
	public static void main(String args[]) {
		Point point =  new ACartesianPoint (50, 100);
		ObjectEditor.edit(point);
		point = new ACartesianPoint(100, Math.PI/4);
		ObjectEditor.edit(point);
//		
	}
}
