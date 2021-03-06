package gradingTools.comp401f15.assignment1.testcases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import grader.sakai.project.SakaiProject;
import gradingTools.sharedTestCase.CheckStyleTestCase;
import gradingTools.sharedTestCase.MinDeclaredMethodsTestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import wrappers.framework.project.ProjectWrapper;


public class MinDeclaredMethodsInSameOrDifferentClassTestCase extends MinDeclaredMethodsTestCase {
	 public MinDeclaredMethodsInSameOrDifferentClassTestCase(int aMinimum) {
	        super(aMinimum);
	    }
	  
	    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
	        SakaiProject aProject = ((ProjectWrapper) project).getProject();
	        // need a more intelligent check than this
	        if (aProject.getClassesManager().getClassDescriptions().size() > 1)
	        	return pass();
	        return super.test(project, autoGrade);

	    }
}

