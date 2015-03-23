package grader.execution;

public class ExecutionSpecificationSelector {
	public static ExecutionSpecification executionSpecification;

	public static ExecutionSpecification getExecutionSpecification() {
		if (executionSpecification == null) {
			executionSpecification = new AnExecutionSpecification();
			executionSpecification.loadFromConfiguration();
		}
		return executionSpecification;
	}

	public static void setExecutionSpecification(
			ExecutionSpecification executionSpecification) {
		ExecutionSpecificationSelector.executionSpecification = executionSpecification;
	}

	

}
