package gradingTools.assignment9.testCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tools.CompilationNavigation;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.ConstructorDeclaration;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaintListenerListenersTestCase extends BasicTestCase {

    public PaintListenerListenersTestCase() {
        super("PaintListener views are registered as listeners test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        List<ClassDescription> classDescriptions = project.getClassesManager().get().findClassByTag("PaintListener");
        if (classDescriptions.isEmpty())
            return fail("No class tagged \"PaintListener\"");
        ClassDescription classDescription = new ArrayList<>(classDescriptions).get(0);

        // Get the views
        Class<?> paintListener = classDescription.getJavaClass();
        List<ClassDescription> views = new ArrayList<>();
        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {
            if (!description.getJavaClass().isInterface() && paintListener.isAssignableFrom(description.getJavaClass())) {
                views.add(description);
            }
        }

        // Count how many views register themselves as listeners in the constructor
        double listenerCount = 0;
        String notes = "";
        for (ClassDescription view : views) {
            // Get the constructors
            try {
                ClassOrInterfaceDeclaration classDef = CompilationNavigation.getClassDef(view.parse());
                List<ConstructorDeclaration> constructors = CompilationNavigation.getConstructors(classDef);

                // Look for one assignment in any constructor
                boolean found = false;
                for (ConstructorDeclaration constructor : constructors) {
                    String code = constructor.toString();
                    if (code.contains(".addPropertyChangeListener(this)")) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    listenerCount++;
                else
                    notes += "PaintListener view " + view.getJavaClass().getSimpleName() + " doesn't register itself as a listener in its constructor.\n";
            } catch (IOException e) {
                // Don't do anything here.
            }
        }

        double count = views.size();
        return partialPass(listenerCount / count, notes);
    }
}

