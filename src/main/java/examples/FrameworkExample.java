package examples;

import framework.grading.FrameworkProjectRequirements;
import framework.grading.GradingManager;
import examples.checkers.testCases.ErrorMessageTestCase;
import examples.checkers.testCases.FailingTestCase;
import examples.checkers.testCases.PassingTestCase;

import java.io.IOException;

/**
 * This demonstrates using the "framework" grading system
 */
public class FrameworkExample {
    public static void main(String[] args) throws IOException {

        // Project grading criteria are saved in a FrameworkProjectRequirements object
        FrameworkProjectRequirements requirements = new FrameworkProjectRequirements() {{
            addFeature("Test feature 1", 20, new PassingTestCase());
            addFeature("Test feature 2", 15, new FailingTestCase());
            addFeature("Test feature 3", 10, new ErrorMessageTestCase());
        }};

        // The GraderManager is responsible for running the grading process
        GradingManager manager = new GradingManager("Example Assignment", requirements);
        manager.run();

    }
}
