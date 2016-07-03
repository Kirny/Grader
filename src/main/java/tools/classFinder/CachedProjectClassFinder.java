package tools.classFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import scala.Option;
import grader.basics.project.ClassDescription;
import grader.basics.project.Project;

/**
 * This keeps track of classes and their corresponding tags. If it has no tag then it asks the user.
 * TODO: This whole class finder system is confusing and needs to be refactored.
 */
public class CachedProjectClassFinder {

    private Project project;
    private Map<String, Option<ClassDescription>> cache = new HashMap<String, Option<ClassDescription>>();

    public CachedProjectClassFinder(Project project) {
        this.project = project;
    }

    public Option<ClassDescription> get(String tag) {
        if (!cache.containsKey(tag)) {
            List<ClassDescription> classDescriptions = new ArrayList<ClassDescription>(project.getClassesManager().get().getClassDescriptions());
            Collections.sort(classDescriptions, new Comparator<ClassDescription>() {
                @Override
                public int compare(ClassDescription o1, ClassDescription o2) {
                    return o1.getJavaClass().getCanonicalName().compareTo(o2.getJavaClass().getCanonicalName());
                }
            });

            ClassDescription description = (ClassDescription) JOptionPane.showInputDialog(null,
                    "Which class is: \"" + tag + "\"? (Click Cancel if doesn't exist)",
                    "Class finder", JOptionPane.QUESTION_MESSAGE, null,
                    classDescriptions.toArray(),
                    null
            );
            if (description == null)
                cache.put(tag, Option.<ClassDescription>empty());
            else
                cache.put(tag, Option.apply(description));
        }
        return cache.get(tag);
    }
}
