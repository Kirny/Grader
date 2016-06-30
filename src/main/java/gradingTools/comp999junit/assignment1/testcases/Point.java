package gradingTools.comp999junit.assignment1.testcases;
import util.annotations.Explanation;
import util.annotations.Tags;
//@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Tags({"Point"})
@Explanation("Location in Java coordinate System.")
public interface Point {
	public int getX(); 
	public int getY(); 	
	public double getAngle(); 
	public double getRadius();
//	void print(String aString, ECPoint aPoint); 
	void print(); 
	Point translate(Point aPoint, int anXDelta, int aYDelta); 


}
