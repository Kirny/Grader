package grader.trace.settings;

import java.util.Date;

import grader.sakai.project.SakaiProjectDatabase;
import grader.settings.GraderSettingsModel;
import bus.uigen.trace.ConstantsMenuAdditionEnded;
import util.trace.TraceableInfo;

public class AutomaticNavigationStarted extends GraderSettingsInfo {

	SakaiProjectDatabase projectDatabase;
	
	public AutomaticNavigationStarted(String aMessage, GraderSettingsModel aGradingSettingsModel, SakaiProjectDatabase aProjectDatabase, Object aFinder) {
		super(aMessage, aGradingSettingsModel, aFinder);
		projectDatabase = aProjectDatabase;

//		 gradingSettingsModel = aGradingSettingsModel;
	}
	
	public SakaiProjectDatabase getProjectDatabase() {
		return projectDatabase;
	}
	public void setProjectDatabase(SakaiProjectDatabase projectDatabase) {
		this.projectDatabase = projectDatabase;
	}
	public static AutomaticNavigationStarted newCase(GraderSettingsModel aGradingSettingsModel, SakaiProjectDatabase aProjectDatabase, Object aFinder) {
		NavigationStarted.newCase(aGradingSettingsModel, aProjectDatabase, aFinder);	
		String aMessage = "Automatic Navigation Started";
		AutomaticNavigationStarted retVal = new AutomaticNavigationStarted(aMessage, aGradingSettingsModel, aProjectDatabase, aFinder);
		retVal.announce();		
		return retVal;
	}

}
