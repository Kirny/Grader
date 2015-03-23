package framework.grading.testing;

import framework.project.Project;


/**
 * All test cases should extend this class.
 * Subclasses will implement the {@link TestCase#test(framework.project.Project, boolean)} method.
 * This method should call and return one of the following helper functions:
 * <ul>
<<<<<<< HEAD
 *     <li>{@link framework.grading.testing.BasicTestCase#pass(boolean)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#pass(String, boolean)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#partialPass(double, boolean)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#partialPass(double, String, boolean)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#fail(String, boolean)}</li>
=======
 *     <li>{@link framework.grading.testing.BasicTestCase#pass()}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#pass(String)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#partialPass(double)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#partialPass(double, String)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#fail(String)}</li>
>>>>>>> working
 * </ul>
 *
 * An example:
 * <pre>
 * {@code
 * return partialPass(0.5, "Only got half of the points");
 * }
 * </pre>
 */
public abstract class BasicTestCase implements TestCase {

    protected Checkable checkable;
    protected String name;

    public BasicTestCase(String name) {
        this.name = name;
    }
   



    @Override
    public void setCheckable(Checkable checkable) {
        this.checkable = checkable;
    }

    @Override
    public String getName() {
        return name;
    }

    protected TestCaseResult partialPass(double percentage, boolean autograded) {
        return new TestCaseResult(percentage, name, autograded);
    }

    protected TestCaseResult partialPass(double percentage, String notes, boolean autograded) {
        return new TestCaseResult(percentage, notes, name, autograded);
    }
    protected TestCaseResult partialPass(double percentage, String notes) {
        return partialPass(percentage, notes, true);
    }

    protected TestCaseResult pass() {
        return new TestCaseResult(true, name, true);
    }

    protected TestCaseResult pass(boolean autograded) {
        return new TestCaseResult(true, name, autograded);
    }

    protected TestCaseResult pass(String notes) {
        return new TestCaseResult(true, notes, name, true);
    }
    protected TestCaseResult pass(String notes, boolean autograded) {
        return new TestCaseResult(true, notes, name, autograded);
    }

    protected TestCaseResult fail(String notes) {
        return new TestCaseResult(false, notes, name, true);
    }

    protected TestCaseResult fail(String notes, boolean autograded) {
        return new TestCaseResult(false, notes, name, autograded);

    }
}
