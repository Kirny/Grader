package grader.sakai.project;

import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.annotations.Visible;
import util.misc.ClearanceManager;
import util.models.LabelBeanModel;
import util.models.PropertyListenerRegisterer;
import grader.assignment.GradingFeatureList;
import grader.project.Project;

import java.beans.PropertyChangeListener;

import bus.uigen.uiFrame;


public interface ProjectStepper  extends ClearanceManager, PropertyListenerRegisterer, PropertyChangeListener{
	public boolean setProject(SakaiProject newVal) ;
	
	public void output();
	
	public void sources() ;
	public double getScore() ;
	public void setScore(double newVal) ;
	public  void waitForClearance() ;
	public SakaiProjectDatabase getProjectDatabase() ;

	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) ;
	public void setOnyen(String anOnyen) ;
	public boolean setProject(String anOnyen) ;
	public boolean isAutoRun() ;
    public void setAutoRun(boolean newVal);
    public void autoRun() ;
    public boolean hasMoreSteps();
	
	public void setHasMoreSteps(boolean newVal);
    public SakaiProject getProject();
    void runProjectsInteractively();
    public void configureNavigationList();

	boolean preDone();

	void done();

	String getNavigationFilter();

	void setNavigationFilter(String newVal);

	boolean preGetGradingFeatures();

	boolean preAutoGrade();

	void autoGrade();

	GradingFeatureList getGradingFeatures();

	boolean isAllGraded();

	boolean preNext();

	void next();

	boolean prePrevious();

	void previous();

	boolean preRunProjectsInteractively();

	void move(boolean forward);
	public boolean isAutoAutoGrade() ;
    public void setAutoAutoGrade(boolean newVal) ;
    public void autoAutoGrade() ;

	void setOEFrame(uiFrame aFrame);

	uiFrame getOEFrame();

	LabelBeanModel getPhoto();
	

}
