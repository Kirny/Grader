package gradingTools.assignment11.tools;

import java.util.List;

import scala.Option;
import tools.classFinder2.ClassFinder;
import framework.grading.testing.NotAutomatableException;
import framework.project.ClassDescription;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpecialClassFinder {

    public static Option<ClassDescription> getLocatable(Project project, boolean autoGrade) throws NotAutomatableException {

        List<ClassDescription> descriptions = project.getClassesManager().get().findClassesAndInterfacesByTag("locatable");
        if (descriptions.isEmpty())
            return ClassFinder.get(project).findByTag("locatable", autoGrade);

        ClassDescription description = null;
        for (ClassDescription d : descriptions) {
            if (d.getJavaClass().getSuperclass() == Object.class)
                description = d;
        }
        if (description == null)
            return Option.empty();
        return Option.apply(description);
    }

    public static Option<ClassDescription> getBoundedShape(Project project, boolean autoGrade) throws NotAutomatableException {
        List<ClassDescription> descriptions = project.getClassesManager().get().findClassesAndInterfacesByTag("bounded shape");
        if (descriptions.isEmpty())
            return ClassFinder.get(project).findByTag("bounded shape", autoGrade);

        ClassDescription description = null;
        for (ClassDescription d : descriptions) {
            if (description == null)
                description = d;

            for (ClassDescription d2 : descriptions) {
                if (d != d2) {
                    if (d.getJavaClass().isAssignableFrom(d2.getJavaClass()))
                        description = d;
                }
            }
        }
        return Option.apply(description);
    }

}
