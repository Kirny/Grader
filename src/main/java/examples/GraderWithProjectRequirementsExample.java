package examples;

import framework.grading.FrameworkProjectRequirements;
import wrappers.grader.sakai.project.ProjectDatabaseWrapper;
import examples.checkers.testCases.ErrorMessageTestCase;
import examples.checkers.testCases.FailingTestCase;
import examples.checkers.testCases.PassingTestCase;

/**
 * This demonstrates how you can use the FrameworkProjectRequirements object with the "grader" system
 */
public class GraderWithProjectRequirementsExample {

    public static void main(String[] args) {
        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        // Create a project database, which will contain the project grading criteria
        ProjectDatabaseWrapper database = new ProjectDatabaseWrapper(bulkFolder, dataFolder);

        // Create the project requirements and add them to the database. Nothing special is needed
        FrameworkProjectRequirements requirements = new FrameworkProjectRequirements() {{
            addFeature("Test feature 1", 20, new PassingTestCase());
            addFeature("Test feature 2", 15, new FailingTestCase());
            addFeature("Test feature 3", 10, new ErrorMessageTestCase());
        }};
        database.addProjectRequirements(requirements);

        // You can still run it as normal
        database.nonBlockingRunProjectsInteractively();
    }
}
