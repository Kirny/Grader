package gradingTools.assignment3.testCases;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import grader.basics.project.ClassDescription;
import grader.basics.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScannerBeanPatternTestCase extends BasicTestCase {
    public ScannerBeanPatternTestCase() {
        super("Scanner bean follows bean pattern");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Look in each class for something that satisfies the bean class requirements
        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {

            // There should be a string property with a getter and setter
            try {
                BeanInfo info = Introspector.getBeanInfo(description.getJavaClass());
                for (PropertyDescriptor descriptor : info.getPropertyDescriptors()) {
                    if (descriptor.getPropertyType() == String.class && descriptor.getReadMethod() != null &&
                            descriptor.getWriteMethod() != null)
                        return pass(autoGrade);
                }
            } catch (IntrospectionException e) {
                // Do nothing if it fails
            }
        }
        return fail("Couldn't find a class that satisfies the bean class requirements (string w/ a getter and setter).", autoGrade);
    }
}

