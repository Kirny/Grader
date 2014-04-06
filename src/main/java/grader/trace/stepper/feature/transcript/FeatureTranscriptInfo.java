package grader.trace.stepper.feature.transcript;

import grader.assignment.GradingFeature;
import grader.project.graded.OverviewProjectStepper;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.settings.GraderSettingsModel;
import grader.trace.stepper.SerializableStepperInfo;
import grader.trace.stepper.StepperInfo;
import grader.trace.stepper.feature.GradingFeatureInfo;
import bus.uigen.trace.ConstantsMenuAdditionEnded;
import util.trace.TraceableInfo;

public class FeatureTranscriptInfo extends GradingFeatureInfo {
String transcript;



public FeatureTranscriptInfo(String aMessage,
			SakaiProjectDatabase aSakaiProjectDatabase,
			OverviewProjectStepper aProjectStepper, SakaiProject aProject,
			GradingFeature aFeature,
			String aNotes, Object aFinder) {
		super(aMessage, aSakaiProjectDatabase, aProjectStepper, aProject, aFeature, aFinder);
		transcript = aNotes;
		// TODO Auto-generated constructor stub
	}

public String getTranscript() {
	return transcript;
}



public void setTranscript(String transcript) {
	this.transcript = transcript;
}

//@Override
//public String toCSVRow() {
//	return super.toCSVRow() 
//			+ "," + transcript;
//}

	
//	public static SerializableTranscriptInfo newCase(SakaiProjectDatabase aSakaiProjectDatabase, 
//			OverviewProjectStepper aProjectStepper, 
//			SakaiProject aProject,
//			String aNotes,
//			Object aFinder) {
//		String aMessage = "Overview Notes Manually Changed to:" + aNotes;
//		SerializableTranscriptInfo retVal = new SerializableTranscriptInfo(aMessage, aSakaiProjectDatabase, aProjectStepper, aProject, aNotes, aFinder);
//		retVal.announce();		
//		return retVal;
//	}
	

}