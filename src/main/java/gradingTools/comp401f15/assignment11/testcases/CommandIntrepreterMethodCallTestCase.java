package gradingTools.comp401f15.assignment11.testcases;

import static grader.util.ExecutionUtil.restoreOutputAndGetRedirectedOutput;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import bus.uigen.ObjectEditor;
import bus.uigen.oadapters.ObjectAdapter;
import bus.uigen.trace.PropertyChangeEventInfo;
import framework.execution.RunningProject;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.util.ExecutionUtil;
import grader.util.IntrospectionUtil;
import gradingTools.sharedTestCase.MethodDefinedTestCase;
import gradingTools.sharedTestCase.MethodExecutionTestCase;
import gradingTools.sharedTestCase.MethodExecutionTestCase.MethodEnvironment;
import util.misc.BroadcastingClearanceManager;
import util.trace.TraceableBus;
import util.trace.TraceableListener;

public abstract class CommandIntrepreterMethodCallTestCase extends MethodDefinedTestCase {

	public CommandIntrepreterMethodCallTestCase(String classTag, String methodTag, Object returnType,
			Object... parameterTypes) {
		super(classTag, methodTag, returnType, parameterTypes);
	}

	// protected abstract void processFinally();
	protected abstract TestCaseResult callMethods();

	Object scannerBeanInstance;
	Object bridgeSceneInstance;
	protected Class<?> commandInterpreterClass;

	protected Object commandInterpreter;
	protected Object broadcastingClearanceManagerInstance;
	Project project;

	public TestCaseResult test(Project aProject, boolean autoGrade)
			throws NotAutomatableException, NotGradableException {
		project = aProject;
		// parentThread = null;
		// childThread1 = null;
		// childThread2 = null;
		// child2AfterChild1 = false;
		// child1AfterChild2 = false;
		TestCaseResult superValue = super.test(aProject, autoGrade);
		if (foundMethod == null) {
			return fail(superValue.getNotes());
		}
		ExecutionUtil.redirectOutput();

		try {
			commandInterpreter = IntrospectionUtil.getInstance(aProject, this, "CommandInterpreter");
			
			Class<?> bridgeSceneClass = IntrospectionUtil.findClass(aProject, null, "BridgeScene",
					".*[bB]ridge.*[sS]cene.*", ".*[bB]ridge[sS]cene.*");
			bridgeSceneInstance = IntrospectionUtil.getInstance(aProject, this, "BridgeScene");

			if (commandInterpreter == null || bridgeSceneInstance == null) {
				commandInterpreterClass = IntrospectionUtil.getOrFindClass(aProject, this, "CommandInterpreter");
//				Class<?> bridgeSceneClass = IntrospectionUtil.findClass(aProject, null, "BridgeScene",
//						".*[bB]ridge.*[sS]cene.*", ".*[bB]ridge[sS]cene.*");
				Class<?> scannerBeanClass = IntrospectionUtil.findClass(aProject, null, "ScannerBean",
						".*[sS]canner.*[bB]ean.*", ".*[sS]canner[bB]ean.*");
				Class<?> broadcastingClearanceManagerClass = IntrospectionUtil.getOrFindClass(aProject, this,
						".*BroadcastingClearanceManager.*");

				Constructor<?> commandInterpreterConstructor = null;
				Constructor<?> bridgeSceneConstructor;
				Constructor<?> scannerBeanConstructor = null;
				Constructor<?> clearanceManagerConstructor = null;

				boolean bridgeFirst = true;
				boolean bridgeOnly = false;
				boolean clearanceManagerFirst = false;
				boolean usesClearanceManager = false;
				try {
					Constructor<?>[] commandInterpreterConstructors = commandInterpreterClass.getConstructors();
					for (Constructor<?> c : commandInterpreterConstructors) {
						Object[] params = c.getParameterTypes();
						if (params.length == 1) {
							if (((Class<?>) params[0]).isAssignableFrom(bridgeSceneClass)
							/*
							 * && ((Class<?>)params[1]).isAssignableFrom(
							 * scannerBeanClass)
							 */) {
								commandInterpreterConstructor = c;
								bridgeOnly = true;
								break;
							}
						}
						if (params.length != 2 && !bridgeOnly) {
							System.out.println("Discarding constructor with parms" + Arrays.asList(params));
							continue;
						}
						commandInterpreterConstructor = c;

						if (scannerBeanClass != null && ((Class<?>) params[0]).isAssignableFrom(bridgeSceneClass)
								&& ((Class<?>) params[1]).isAssignableFrom(scannerBeanClass)) {
							// commandInterpreterConstructor = c;
							bridgeFirst = true;
						} else if (scannerBeanClass != null && ((Class<?>) params[0]).isAssignableFrom(scannerBeanClass)
								&& ((Class<?>) params[1]).isAssignableFrom(bridgeSceneClass)) {
							// commandInterpreterConstructor = c;
							bridgeFirst = false;
						} else if (broadcastingClearanceManagerClass != null
								&& ((Class<?>) params[0]).isAssignableFrom(broadcastingClearanceManagerClass)
								&& ((Class<?>) params[1]).isAssignableFrom(bridgeSceneClass)) {
							// commandInterpreterConstructor = c;
							usesClearanceManager = true;
							clearanceManagerFirst = true;
						} else if (broadcastingClearanceManagerClass != null
								&& ((Class<?>) params[0]).isAssignableFrom(bridgeSceneClass)
								&& ((Class<?>) params[1]).isAssignableFrom(broadcastingClearanceManagerClass)) {
							usesClearanceManager = true;
							clearanceManagerFirst = false;
						}
					}
					if (!bridgeOnly) {
						// Objects.requireNonNull(commandInterpreterConstructor);
						if (!usesClearanceManager)
							scannerBeanConstructor = scannerBeanClass.getConstructor();

						else
							clearanceManagerConstructor = broadcastingClearanceManagerClass.getConstructor();

					}
					bridgeSceneConstructor = bridgeSceneClass.getConstructor();
					// if (usesClearanceManager)
					// clearanceManagerConstructor =
					// broadcastingClearanceManagerClass.getConstructor();
					// scannerBeanConstructor =
					// scannerBeanClass.getConstructor();
				} catch (Exception e) {
					e.printStackTrace(System.out);
					return fail(
							"Couldn't find correct constructor for CommandInterpreter, BridgeScene, or ScannerBean");
				}
				if (commandInterpreterConstructor == null) {
					return fail("Couldn't find correct constructor for CommandInterpreter");

				}
				boolean[] ret = new boolean[3];
				if (!bridgeOnly && !usesClearanceManager) {

					scannerBeanInstance = IntrospectionUtil.getInstance(aProject, this, "ScannerBean");
					if (scannerBeanInstance == null) {
						scannerBeanInstance = ExecutionUtil.timedInvoke(scannerBeanConstructor, new Object[] {});
						IntrospectionUtil.putInstance(aProject, this, "ScannerBean", scannerBeanInstance);
					}
				}
				bridgeSceneInstance = IntrospectionUtil.getInstance(aProject, this, "BridgeScene");
				if (bridgeSceneInstance == null) {
				bridgeSceneInstance = ExecutionUtil.timedInvoke(bridgeSceneConstructor, new Object[] {});
				IntrospectionUtil.putInstance(aProject, this, "BridgeScene", bridgeSceneInstance);

				}
				if (usesClearanceManager) {
					broadcastingClearanceManagerInstance = IntrospectionUtil.getInstance(aProject, this, "BroadcastingClearanceManager");
					if (broadcastingClearanceManagerInstance != null) {
					broadcastingClearanceManagerInstance = ExecutionUtil.timedInvoke(clearanceManagerConstructor,
							new Object[] {});
					IntrospectionUtil.putInstance(aProject, this, "BroadcastingClearanceManager", broadcastingClearanceManagerInstance);

					}
				}

				// if (bridgeOnly) {
				//
				// }
				// int i = 0;
				if (bridgeOnly) {
					commandInterpreter = ExecutionUtil.timedInvoke(commandInterpreterConstructor,
							new Object[] { bridgeSceneInstance });
				} else if (usesClearanceManager && clearanceManagerFirst) {
					commandInterpreter = ExecutionUtil.timedInvoke(commandInterpreterConstructor,
							new Object[] { broadcastingClearanceManagerInstance, bridgeSceneInstance });
				} else if (usesClearanceManager && !clearanceManagerFirst) {
					commandInterpreter = ExecutionUtil.timedInvoke(commandInterpreterConstructor,
							new Object[] { bridgeSceneInstance, broadcastingClearanceManagerInstance });
				} else if (bridgeFirst) {
					commandInterpreter = ExecutionUtil.timedInvoke(commandInterpreterConstructor,
							new Object[] { bridgeSceneInstance, scannerBeanInstance });
				} else {
					commandInterpreter = ExecutionUtil.timedInvoke(commandInterpreterConstructor,
							new Object[] { scannerBeanInstance, bridgeSceneInstance });
				}
				if (commandInterpreter == null || bridgeSceneInstance == null)
					return fail("Could not instantiate aCommand Interpreter");
				IntrospectionUtil.putInstance(aProject, this, "CommandInterpreter", commandInterpreter);

			}


			return callMethods();
			// parentThread = Thread.currentThread();
			// ObjectAdapter anAdapter =
			// ObjectEditor.toObjectAdapter(bridgeSceneInstance);
			// TraceableBus.addTraceableListener(this);
			//
			//
			//
			// Object retVal = ExecutionUtil.timedInvoke(aCommandInterpreter,
			// foundMethod);
			// retVal = ExecutionUtil.timedInvoke(aCommandInterpreter,
			// foundMethod);
			//
			// Thread.sleep(2000);
			// if (childThread1 == null) {
			// return fail ("No property notification");
			// }
			// if (childThread1 == parentThread) {
			// return fail ("Command not executed in separate thread");
			// }
			// if (child1AfterChild2) {
			// return fail ("Interleaved threads");
			// }
			// return pass();
			// if (childThread1 != parentThread) {
			// childThread1.stop();
			// return pass ();
			// }
			// System.out.println("Executed method");

		} catch (Exception e) {
			return fail("Could not instantiate aCommand Interpreter");
		} finally {
			// TraceableBus.removeTraceableListener(this);
			// processFinally();
			String anOutput = restoreOutputAndGetRedirectedOutput();
			if (anOutput != null && !anOutput.isEmpty()) {
				System.out.println(anOutput);
				RunningProject.appendToTranscriptFile(aProject, getCheckable().getName(), anOutput);
			}
		}
		// return superValue;

	}

	// Thread parentThread, childThread1, childThread2;
	// protected boolean child2AfterChild1;
	// protected boolean child1AfterChild2;
	// @Override
	// public void newEvent(Exception aTraceable) {
	// if (aTraceable instanceof PropertyChangeEventInfo) {
	// Thread currentThread = Thread.currentThread();
	// if (childThread1 == null ) {
	// childThread1 = currentThread;
	// System.out.println("child 1 starts");
	// } else if (childThread1 != currentThread && childThread2 == null){
	// System.out.println("child 2 starts");
	// childThread2 = currentThread;
	// } else if (childThread1 != null && childThread2 != null && childThread2
	// == currentThread) {
	// child2AfterChild1 = true;
	// System.out.println("child 2 executes after child 1");
	//
	// } else if (childThread1 != null && childThread2 != null && childThread1
	// == currentThread) {
	// child1AfterChild2 = true;
	// System.out.println("child 1 executes after child 2");
	// }
	////
	//// else if ()
	//// if (childThread2 == null ) {
	//// childThread1 = Thread.currentThread();
	//// }
	////
	//// else if (childThread1 != null && childThread)
	//// childThread1 = Thread.currentThread();
	//// PropertyChangeEventInfo aPropertyChange = (PropertyChangeEventInfo)
	// aTraceable;
	//// System.out.println("Property change event:" + aTraceable);
	//// newThread.stop();
	//// System.out.println ("New thread == old thread:" + (newThread ==
	// initialThread));
	//
	// }
	//
	// }
	protected Method getUniqueParameterlessMethod(String aMethodName) {
		List<Method> methods = IntrospectionUtil.getOrFindMethodList(project, this, commandInterpreterClass,
				aMethodName, aMethodName);
		if (methods.size() < 1) {
			System.out.println("Could not find:" + aMethodName);
			throw new NotGradableException();
		}
		if (methods.get(0).getParameterTypes().length != 0) {
			System.out.println(aMethodName + " takes more than one parameter");
			throw new NotGradableException();
		}
		return methods.get(0);
	}
}
