package demoAndTest;

import java.beans.PropertyChangeListener;

import util.misc.ClearanceManager;
import util.models.PropertyListenerRegistrar;

public interface DemoAndTestingClearanceManager extends ClearanceManager, PropertyListenerRegistrar{
	public boolean isAutoProceed();
	public void setAutoProceed(boolean autoProceed) ;
	public String getStepDescription() ;
	public void setStepDescription(String stepDescription) ;
	public long getAutoPauseTime();
	public void setAutoPauseTime(long autoPauseTime) ;
	public boolean isAutoPerformStep() ;
	public void setAutoPerformStep(boolean autoPerformStep) ;


}
