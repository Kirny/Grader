package grader.demo.scores;

import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FinalGradeRecorder;
import grader.spreadsheet.csv.ASakaiCSVFeatureGradeManager;

public class ATracingFinalScoreRecorder extends ASakaiCSVFeatureGradeManager implements FinalGradeRecorder {
//	FinalGradeRecorder finalGradeRecorder, featureGradeRecorder;
	public ATracingFinalScoreRecorder(SakaiProjectDatabase aProjectDatabase) {
		super(aProjectDatabase);
	}
	@Override
	public void setGrade(String aStudentName, String anOnyen, double aScore) {
		super.setGrade(aStudentName, anOnyen, aScore);
		System.out.println("Trapped the set grade call for:" + anOnyen + "," + aScore);
	}
	@Override
	public double getGrade(String aStudentName, String anOnyen) {
		System.out.println("Trapped the get grade call for:" + anOnyen );

		return super.getGrade(aStudentName, anOnyen);
	}
	

}
