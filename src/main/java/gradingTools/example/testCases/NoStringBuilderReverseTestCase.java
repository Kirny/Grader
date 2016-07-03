package gradingTools.example.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import grader.basics.execution.RunningProject;
import grader.basics.project.Project;

/**
 * This test case looks for the usage of StringBuilder.reverse by using modified classes.
 * This requires the bootclasspath to be set by setting the VM option: -Xbootclasspath/p:./modifiedClasses
 */
public class NoStringBuilderReverseTestCase extends BasicTestCase {

    public NoStringBuilderReverseTestCase() {
        super("No string builder reverse test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        try {
            Integer preCount = (Integer) StringBuilder.class.getField("reverseCount").get(null);

            RunningProject runningProject = project.start("Hello world\n");
            runningProject.await();

            Integer postCount = (Integer) StringBuilder.class.getField("reverseCount").get(null);

            if (postCount.equals(preCount))
                return pass(autoGrade);
            else
                return fail("StringBuilder.reverse() was used", autoGrade);
        } catch (Exception e) {
            throw new NotAutomatableException();
        }
    }
}

