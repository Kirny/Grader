package grader.trace.interaction_logger;

import grader.interaction_logger.manual_grading_stats.AllStudentsProblemHistory;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.steppers.OverviewProjectStepper;
import grader.trace.GraderInfo;
import grader.trace.steppers.AutoAutoGradeSet;

public class SavedAllStudentsProblemGradingHistoryInfo extends GraderInfo{
	AllStudentsProblemHistory savedProblemGradingHistory;
	
	public SavedAllStudentsProblemGradingHistoryInfo(String aMessage, 
			AllStudentsProblemHistory aSavedProblemGradingHistory,			
			Object aFinder) {
		super(aMessage, aFinder);
	}

	public AllStudentsProblemHistory getSavedProblemGradingHistory() {
		return savedProblemGradingHistory;
	}

	public void setSavedProblemGradingHistory(
			AllStudentsProblemHistory savedProblemGradingHistory) {
		this.savedProblemGradingHistory = savedProblemGradingHistory;
	}

	
	
	

}
