package grader.junit;

import java.awt.Color;

import bus.uigen.ObjectEditor;
import bus.uigen.attributes.AttributeNames;
import bus.uigen.models.AMainClassListLauncher;
import bus.uigen.undo.ExecutableCommand;

public class AGradableJUnitTestAR implements ExecutableCommand {

	@Override
	public Object execute(Object arg0) {
		ObjectEditor.setAttribute(AGradableJUnitTest.class, AttributeNames.USE_NAME_AS_LABEL, true);
//		ObjectEditor.setAttribute(AGradableJUnitTest.class, AttributeNames.COMPONENT_FOREGROUND, Color.RED);
//		ObjectEditor.setAttribute(AGradableJUnitTest.class, AttributeNames.CONTAINER_BACKGROUND, Color.RED);
// Cannot be overidden by temp dynamic attribute
//		ObjectEditor.setAttribute(AGradableJUnitTest.class, AttributeNames.COMPONENT_FOREGROUND, Color.RED);



		return null;
	}

}
