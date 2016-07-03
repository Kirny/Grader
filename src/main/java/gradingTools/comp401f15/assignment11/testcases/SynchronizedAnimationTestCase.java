package gradingTools.comp401f15.assignment11.testcases;

import framework.grading.testing.TestCaseResult;
import grader.basics.execution.BasicProjectExecution;
import util.misc.ThreadSupport;
import util.trace.TraceableListener;

public class SynchronizedAnimationTestCase extends CommandInterpreterAnimationTestCase  implements TraceableListener{
	public SynchronizedAnimationTestCase(String methodTag) {
		super(methodTag);
	}
	protected void waitForThreads() {
		ThreadSupport.sleep(2000);
	}
	protected void callAsynchronousMethods() {
		Object retVal = BasicProjectExecution.timedInvoke(commandInterpreter, foundMethod);
		retVal = BasicProjectExecution.timedInvoke(commandInterpreter, foundMethod);
		
	}

	protected synchronized TestCaseResult computeResult() {
		if (childThread1 == null) {
			return fail ("No property notification from a thread");
		}
		if (eventInParentThread 
//				|| numThreadsAfterExecution <= numThreadsBeforeExecution
				) {
  			return fail ("No threads created");
  		}
		int numThreadsCreated = numThreadsAfterExecution - numThreadsBeforeExecution;
		if (numThreadsCreated < 2) {
			return partialPass(0.5, "A single thread created, but there can be race conditions:" + foundMethod);
		}
		if (childThread2 == null) {
//			return fail ("No property notification from second thread");
			return pass ();

		}
		if (child2AfterChild1 && !child1AfterChild2) {
			return pass();
		}	
		return partialPass(0.3, "Interleaved threads");
	}

}
