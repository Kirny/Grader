package grader.project.graded;

import grader.settings.ABeginActionModel;
import bus.uigen.ObjectEditor;
import bus.uigen.attributes.AttributeNames;
import bus.uigen.undo.ExecutableCommand;

public class AGradedProjectTextOverviewAR implements ExecutableCommand{
	public Object execute(Object theFrame) {

//		ObjectEditor.setPropertyAttribute(AGraderFilesSetterModel.class, "DownloadFolder", AttributeNames.LABEL_WIDTH, 90);
//		ObjectEditor.setPropertyAttribute(AGraderFilesSetterModel.class, "TextEditor", AttributeNames.LABEL_WIDTH, 90);

		ObjectEditor.setPropertyAttribute(AGradedProjectTextOverview.class, "*", AttributeNames.COMPONENT_WIDTH, 200);
//		ObjectEditor.setPropertyAttribute(AGradedProjectOverview.class, "*", AttributeNames.LABELLED, false);
//
//		ObjectEditor.setPropertyAttribute(AGradedProjectOverview.class, "textOverview", AttributeNames.VISIBLE, true);
//		ObjectEditor.setPropertyAttribute(AGradedProjectOverview.class, "photo", AttributeNames.VISIBLE, true);



		return null;
	}

}
