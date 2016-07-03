package framework.execution;

import grader.basics.execution.RunningProject;
import grader.config.StaticConfigurationUtils;

import java.io.InputStream;

import util.trace.Tracer;
import util.trace.console.ConsoleOutput;

public class ARunnerOutputStreamProcessor extends ABasicRunnerOutputStreamProcessor implements Runnable {
//	protected Scanner scanner ;
//	protected InputStream out;
//	protected RunningProject runner;
	public ARunnerOutputStreamProcessor(InputStream aProcessErrorOut, RunningProject aRunner, /*Semaphore aSemaphore,*/ String aProcessName, Boolean anOnlyProcess) {
		super(aProcessErrorOut, aRunner, /*aSemaphore,*/ aProcessName, anOnlyProcess);
		
	}
	@Override
	protected void maybeTraceLine(String s) {
		if (StaticConfigurationUtils.getTrace()) {
			
			if (Tracer.isInfo(s))
				return; // do not create console output info for an info event
		ConsoleOutput consoleOutput = ConsoleOutput.newCase(s, this);
		String infoString = Tracer.toInfo(consoleOutput, consoleOutput.getMessage());
		if (infoString != null)
			runner.appendProcessOutput(processName, infoString);
		}
	}
//@Override
//public void processLine(String s) {
//	System.out.println("Process line:" + s);
//	runner.appendCumulativeOutput(s + "\n"); // append cumulative output
////	if (processName != null) {
//	
//		System.out.println(outPrefix + s);
//	
//
////		runner.appendErrorOutput(processName, s + "\n");
//		runner.appendProcessOutput(processName, s + "\n"); // append this process output
////	}
//		
//		if (StaticConfigurationUtils.getTrace()) {
//			
//			if (Tracer.isInfo(s))
//				return; // do not create console output info for an info event
//		ConsoleOutput consoleOutput = ConsoleOutput.newCase(s, this);
//		String infoString = Tracer.toInfo(consoleOutput, consoleOutput.getMessage());
//		if (infoString != null)
//			runner.appendProcessOutput(processName, infoString);
//		}
//}
//		
//
////			@Override
////			public void run() {
////				while (scanner.hasNextLine()) {
////					String line = scanner.nextLine();
////					System.err.println(line);
////					runner.appendErrorOutput(line + "\n");
////				}
////				scanner.close();
////			}
		
	}
