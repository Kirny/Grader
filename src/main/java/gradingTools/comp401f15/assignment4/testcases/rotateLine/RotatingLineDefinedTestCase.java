package gradingTools.comp401f15.assignment4.testcases.rotateLine;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.util.IntrospectionUtil;
import java.lang.reflect.Constructor;

/**
 *
 * @author Andrew Vitkus
 */
public class RotatingLineDefinedTestCase extends BasicTestCase {
    private static final String TAG = "RotatingLine";
    private static final String[] classDescriptions = new String[]{null, TAG, ".*"+TAG+".*", ".*"+TAG+".*"};

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        Class aClass = IntrospectionUtil.findClass(project, 
                            classDescriptions[0],
                            classDescriptions[1],
                            classDescriptions[2],
                            classDescriptions[3]);
        if (aClass == null) {
            return fail("Cannot find rotating line class");
        } else {
            Constructor[] constructors = aClass.getConstructors();
            for(Constructor c : constructors) {
                if (c.getParameterCount() == 0) {
                    return pass();
                }
            }
            return partialPass(0.5, "Missing default constructor");
        }
    }
    
    public RotatingLineDefinedTestCase() {
        super("Rotating Line Class Properly Defined Test Case");
    }
}