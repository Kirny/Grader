package gradingTools.assignment10.testCases;

import java.lang.reflect.Method;
import java.util.List;

import scala.Option;
import tools.classFinder2.ClassFinder;
import tools.classFinder2.ClassType;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import grader.basics.project.ClassDescription;
import grader.basics.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnimatingMethodTestCase extends BasicTestCase {

    private String tag;

    public AnimatingMethodTestCase(String tag) {
        super(tag + " animating method test case");
        this.tag = tag;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Get the command interpreter
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag("Command Interpreter", autoGrade, ClassType.CLASS);
        if (classDescription.isEmpty())
            return fail("Looking for method in command interpreter, but the class was not found.", autoGrade);

        // Get the method
        List<Method> methods = classDescription.get().getTaggedMethods(tag);
        if (methods.isEmpty())
            return fail("No method tagged: " + tag, autoGrade);

        // Check that it's parameterless
        if (methods.get(0).getParameterTypes().length == 0)
            return pass(autoGrade);
        return partialPass(0.5, tag + " method should be parameterless", autoGrade);
    }
}

