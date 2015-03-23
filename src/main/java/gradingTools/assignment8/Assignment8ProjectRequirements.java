package gradingTools.assignment8;

import framework.grading.FrameworkProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import gradingTools.assignment6.testCases.*;
import gradingTools.assignment8.testCases.*;

/**
 * Project 8 requirements.
 * See the rubric here: http://www.cs.unc.edu/~dewan/comp401/current/Recitations/
 */
public class Assignment8ProjectRequirements extends FrameworkProjectRequirements {

    public Assignment8ProjectRequirements() {

        // Add due date/times with a 30 minute grace period
        addDueDate("10/24/2013 00:30:00", 1.05);
        addDueDate("10/26/2013 00:30:00", 1);
        addDueDate("10/29/2013 11:30:00", 0.9);
        addDueDate("10/31/2013 11:30:00", 0.75);

        // Part 1
        addFeature("Shapes implement PropertyListenerRegisterer", 20, new ShapesRegisterListenerTestCase());
        addFeature("Collection of observers", 5, new ListenerCollectionTestCase());
        addFeature("Setters notify observers", 10, new SettersNotifyTestCase());

        // Part 2
        addFeature("Console View tag", 5, new ConsoleViewTagTestCase());
        addFeature("Console View is a listener", 5, new ConsoleViewListenerTestCase());
        addFeature("Console View is a listener", 25,
                new ConsoleViewConstructorParameterTestCase(),
                new ConsoleViewConstructorAddListenerTestCase());

        // Part 3
        addManualFeature("Move demo", 10,
                new QuestionTestCase("Does the avatar move?", "Move test case"),
                new QuestionTestCase("Are the move events printed to the console?", "Move event test case"));
        addManualFeature("Rotate demo", 10,
                new QuestionTestCase("Do the avatar parts rotate?", "Rotate test case"),
                new QuestionTestCase("Are the rotate events printed to the console?", "Rotate event test case"));
        addManualFeature("Text demo", 10,
                new QuestionTestCase("Does the avatar's text change?", "Text test case"),
                new QuestionTestCase("Is the text event printed to the console?", "Text event test case"));

        // Extra credit
        addManualFeature("Awesome demo", 5, true);

        // Define the restrictions
        addRestriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case"));
        addRestriction("Interface object assignments.", -5, new InterfaceTypeTestCase("Interface type test case"));
        addRestriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case"));
        addRestriction("Main class in correct package.", -5, new MainClassTestCase("Assignment8", "Main method test case"));
        addRestriction("No System.exit()", -5, new SystemExitTestCase("System.exit test case"));

    }
}
