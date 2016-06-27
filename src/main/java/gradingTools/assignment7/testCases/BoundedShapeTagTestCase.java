package gradingTools.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.util.BasicProjectIntrospection;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedShapeTagTestCase extends BasicTestCase {
    public BoundedShapeTagTestCase() {
        super("BoundedShape tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Look for a class with the "Locatable" tag and that isn't inheriting from something else
        //Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("BoundedShape");
        if (BasicProjectIntrospection.findClass(project, "BoundedShape") != null) {
            return pass();
        } else {
            return fail("Can't find class tagged BoundedShape");
        }
        
        //if (classDescription.isDefined())
        //    return pass(autoGrade);
        //return fail("No superclass tagged \"BoundedShape\"", autoGrade);
    }
}

