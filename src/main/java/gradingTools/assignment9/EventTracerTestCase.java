package gradingTools.assignment9;

import util.trace.TraceableBus;
import util.trace.TraceableListener;
import util.trace.Tracer;
import framework.grading.testing.BasicTestCase;
import grader.basics.execution.NotRunnableException;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/31/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventTracerTestCase extends BasicTestCase {
    public EventTracerTestCase(String name) {
        super(name);

        TraceableBus.addTraceableListener(new TraceableListener() {
            @Override
            public void newEvent(Exception e) {
                System.out.println("Saw an event: " + e);
            }
        });
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        try {
            if (autoGrade)
                throw new NotAutomatableException();
            project.launch("");
            Tracer.info("Foo bar");
            return pass();

        } catch (NotRunnableException e) {
            throw new NotGradableException();
        }
    }
}
