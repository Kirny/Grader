package grader.requirements.interpreter;

import util.misc.Common;
import framework.execution.RunningProject;
import grader.requirements.interpreter.specification.CSVRequirementsSpecification;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import gradingTools.assignment7.testCases.GetInvalidTestCase;

public class InterpretedVariablesSubstituter {
	public static final String VAR_PREFIX = "$";
	public static final String ACTUAL_OUTPUT = "$actualoutput";
	public static final String MODEL_OUTPUT = "$modeloutput";
	public static final String INPUT = "$input";
	public static final String SOURCE = "$source";
	public static final String FILE_SUFFIX = ".txt";

	public static String getValue(SakaiProject aRunningProject, CSVRequirementsSpecification aSpecification, 
			int aRequirementNumber, 
			String anOutput,
			String anExpression) {
		String anExpressionLC = anExpression.toLowerCase();
		if (!anExpression.startsWith(VAR_PREFIX)) {
			return anExpression;
		} else if (ACTUAL_OUTPUT.equals(anExpressionLC)) {
			return getActualOutput(anOutput);
		} else if (MODEL_OUTPUT.equals(anExpressionLC)) {
			return getModelOutput(aSpecification, aRequirementNumber);
		} else if (INPUT.equals(anExpressionLC)) {
			return getInput(aSpecification, aRequirementNumber);
		} else if (SOURCE.equals(anExpressionLC)) {
			return getSource(aRunningProject);
		}
		else return "";
		
	}
	
	public static String getSource(SakaiProject project) {
//		SakaiProject project = aRunningProject.getProject();
		return project.
				getClassesTextManager().getEditedAllSourcesText(project.getSourceFileName());
		
//		return Common.toText(aRunningProject.getProject().getSourceFileName()).toString();
	}
	
	public static boolean isFileName(String aString) {
		return (aString.endsWith(FILE_SUFFIX));
			
	}
	
	public static String toFullFileName(String aFileName) {
		SakaiProjectDatabase aProjectDatabase = ASakaiProjectDatabase.getCurrentSakaiProjectDatabase();
		String anAssignmentDataFoldername = aProjectDatabase.getAssignmentDataFolder().getMixedCaseAbsoluteName();
		return anAssignmentDataFoldername+ "/" + aFileName;
	}
	
	public static String toString(String aFileOrText) {
		if (isFileName(aFileOrText))
			return Common.toText(toFullFileName( aFileOrText)).toString();
		else
	        return aFileOrText.replace("\\n", "\n"); // to support new lines, csv reader gobbles up single slashes and leaves souble slashes unchanged

		
	}
	public static String getActualOutput(
			String anOutput) {
		return anOutput; // cannot be a file
	}
	public static String getModelOutput(CSVRequirementsSpecification aSpecification, int aRequirementNumber) {
		String aModelOutput = aSpecification.getModelOutput(aRequirementNumber);
		return toString(aModelOutput);
	}
	
	public static String getInput(CSVRequirementsSpecification aSpecification, 
			int aRequirementNumber) {
		String anInput = aSpecification.getInput(aRequirementNumber);
		return toString(anInput);
	}
	
	public static void main (String[] args) {
		String s = "1\\n2\\n";
		String s2 = s.replace("\\n", "\n");
		System.out.println(s2);
	}

}
