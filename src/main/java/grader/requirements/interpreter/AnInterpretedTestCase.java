package grader.requirements.interpreter;

import wrappers.framework.project.ProjectWrapper;
import framework.execution.RunningProject;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.requirements.interpreter.checkers.InterpretedChecker;
import grader.requirements.interpreter.checkers.InterpretedCheckerRegistry;
import grader.requirements.interpreter.checkers.InterpretedCheckerResult;
import grader.requirements.interpreter.specification.CSVRequirementsSpecification;
import grader.sakai.project.SakaiProject;
import gradingTools.utils.RunningProjectUtils;

public class AnInterpretedTestCase extends BasicTestCase{
	CSVRequirementsSpecification csvRequirementsSpecification;
	int featureNumber;
	String input;
	
	public AnInterpretedTestCase(String aName, CSVRequirementsSpecification aCSVRequirementsSpecification,
							int aFeatureNum
							) {
		super(aName);
		csvRequirementsSpecification = aCSVRequirementsSpecification;
		featureNumber = aFeatureNum;
	}

	@Override
	public TestCaseResult test(Project project, boolean autoGrade)
			throws NotAutomatableException, NotGradableException {
		SakaiProject aSakaiProject = ((ProjectWrapper) project).getProject();
		String anInput = InterpretedVariablesSubstituter.getInput(csvRequirementsSpecification, featureNumber);
		Integer aTimeOut = csvRequirementsSpecification.getTimeOut(featureNumber);
		String anOutput = "";
		RunningProject runningProject = null;
		if (aTimeOut != null) {
		 runningProject = RunningProjectUtils.runProject(project, aTimeOut, anInput);
		anOutput = runningProject.await();
		} else { // use the I/O from last run, could also store I/O mapping in project
			anInput = aSakaiProject.getCurrentInput();
			anOutput = aSakaiProject.getCurrentOutput().toString();
		}
		String aComparator = csvRequirementsSpecification.getChecker(featureNumber);
		InterpretedChecker aChecker = InterpretedCheckerRegistry.getInterpretedChecker(aComparator);
		int numArgs = aChecker.getNumArgs();
		String[] anArgs = new String[numArgs];
		String allArgs = "";
		for (int i = 0; i < numArgs; i++) {
			String anArg = csvRequirementsSpecification.getArg(featureNumber, i);
			String anActualArg = InterpretedVariablesSubstituter.getValue(aSakaiProject, csvRequirementsSpecification, featureNumber, anOutput, anArg);
			anArgs[i] = anActualArg;
			allArgs += " " + anArg;
		}
		InterpretedCheckerResult aResult = aChecker.check(anArgs);
		String aFunctionCall = aComparator + " " + allArgs;
		
//		if (checkable instanceof Feature && aResult.isSucceeded()) {
		if (aResult.isSucceeded()) {
			return pass("");
		} else 
			return fail(aFunctionCall + " failed \n" + aResult.getNotes());
	}

}
