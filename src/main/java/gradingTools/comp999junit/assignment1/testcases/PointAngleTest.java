package gradingTools.comp999junit.assignment1.testcases;


//import org.junit.Test;
import util.annotations.Group;
import util.annotations.IsExtra;
import util.annotations.IsRestriction;
import util.annotations.MaxValue;

@Group(AbstractPointTest.ANGLE_TESTS)
public class PointAngleTest extends AbstractPointTest {
	
	
	protected void checkComputations (double aComputedAngle, double aComputedRadius, double aCorrectAngle, double aCorrectRadius) {
		assertAngle(aComputedAngle, aCorrectAngle);
//		assertRadius(aComputedRadius, aCorrectRadius);
	}	

}
