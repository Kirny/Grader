package gradingTools.assignment9.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/29/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListenerAndPainterTagTestCase extends BasicTestCase {
    public ListenerAndPainterTagTestCase() {
        super("Paint Listener and Observable Painter tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        List<ClassDescription> class1 = project.getClassesManager().get().findClassByTag("Paint Listener");
        List<ClassDescription> class2 = project.getClassesManager().get().findClassByTag("Observable Painter");
        if (class1.isEmpty()) {
            if (class2.isEmpty())
                return fail("Neither paint listener nor observable painter are tagged.",autoGrade);
            else
                return partialPass(0.5, "Paint listener is not tagged.", autoGrade);
        }
        if (class2.isEmpty())
            return partialPass(0.5, "Observable painter is not tagged.", autoGrade);
        return pass(autoGrade);
    }
}

