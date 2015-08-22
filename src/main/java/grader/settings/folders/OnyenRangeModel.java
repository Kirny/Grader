package grader.settings.folders;

import java.beans.PropertyChangeListener;

import util.models.PropertyListenerRegisterer;

public interface OnyenRangeModel extends PropertyListenerRegisterer{
	public String getStartingOnyen() ;
	public void setDisplayedStartingOnyen(String startingOnyen) ;
	public String getEndingOnyen();

	public void setDisplayedEndingOnyen(String endingOnyen) ;
	String getGoToOnyen();
	void setGoToOnyen(String goToOnyen);

}
