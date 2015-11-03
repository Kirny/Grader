package gradingTools.comp401f15.assignment7.testCases.commandInterpreter;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.util.ExecutionUtil;
import grader.util.IntrospectionUtil;
import gradingTools.sharedTestCase.MethodExecutionTestCase;
import gradingTools.sharedTestCase.MethodExecutionTestCase.MethodEnvironment;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

/**
 *
 * @author Andrew Vitkus
 */
public class MoveCommandInterpretedTestCase extends BasicTestCase {

    private static final String TEST_COMMAND = "move Arthur 10 10";
        
    public MoveCommandInterpretedTestCase() {
        super("Move command test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        Class<?> commandInterpreterClass = IntrospectionUtil.findClass(project, null, "CommandInterpreter", ".*[cC]ommand.*[iI]nterpreter.*", ".*[cC]ommand[iI]nterpreter.*");
        Class<?> bridgeSceneClass = IntrospectionUtil.findClass(project, null, "BridgeScene", ".*[bB]ridge.*[sS]cene.*", ".*[bB]ridge[sS]cene.*");
        Class<?> scannerBeanClass = IntrospectionUtil.findClass(project, null, "ScannerBean", ".*[sS]canner.*[bB]ean.*", ".*[sS]canner[bB]ean.*");
        
        Constructor<?> commandInterpreterConstructor = null;
        Constructor<?> bridgeSceneConstructor;
        Constructor<?> scannerBeanConstructor;
        
        boolean bridgeFirst = true;
        try {
            Constructor<?>[] commandInterpreterConstructors = commandInterpreterClass.getConstructors();
            for(Constructor<?> c : commandInterpreterConstructors) {
                Object[] params = c.getParameterTypes();
                if (params.length != 2) {
                    continue;
                }
                if (((Class<?>)params[0]).isAssignableFrom(bridgeSceneClass)
                        && ((Class<?>)params[1]).isAssignableFrom(scannerBeanClass)) {
                    commandInterpreterConstructor = c;
                    bridgeFirst = true;
                } else if (((Class<?>)params[0]).isAssignableFrom(scannerBeanClass)
                        && ((Class<?>)params[1]).isAssignableFrom(bridgeSceneClass)) {
                    commandInterpreterConstructor = c;
                    bridgeFirst = false;
                }
            }
            Objects.requireNonNull(commandInterpreterConstructor);
            bridgeSceneConstructor = bridgeSceneClass.getConstructor();
            scannerBeanConstructor = scannerBeanClass.getConstructor();
        } catch(Exception e) {
            e.printStackTrace();
            return fail("Couldn't find correct constructor for CommandInterpreter, BridgeScene, or ScannerBean");
        }
        
        Method setCommand = null;
        Method getScannedString = null;
        Method getArthur = null;
        Method[] getX = new Method[2];
        Method[] getY = new Method[2];
        
        try {
            setCommand = IntrospectionUtil.getOrFindMethodList(project, this, commandInterpreterClass, "Command").stream().filter((Method m) -> m.getName().contains("set")).collect(Collectors.toList()).get(0);
            getScannedString = IntrospectionUtil.getOrFindMethodList(project, this, scannerBeanClass, "ScannedString").stream().filter((Method m) -> m.getName().contains("get")).collect(Collectors.toList()).get(0);
            getArthur = IntrospectionUtil.getOrFindMethodList(project, this, bridgeSceneClass, "Arthur").get(0);
            List<Method> lm = IntrospectionUtil.getOrFindMethodList(project, this, getArthur.getReturnType(), "Head");
            lm = lm.stream().filter((s)->s.getName().contains("get")).collect(Collectors.toList());
            getX[0] = lm.get(0);
            getY[0] = lm.get(0);
            lm = IntrospectionUtil.getOrFindMethodList(project, this, getX[0].getReturnType(), "X");
            lm = lm.stream().filter((s)->s.getName().contains("get")).collect(Collectors.toList());
            getX[1] = lm.get(0);
            
            lm = IntrospectionUtil.getOrFindMethodList(project, this, getY[0].getReturnType(), "Y");
            lm = lm.stream().filter((s)->s.getName().contains("get")).collect(Collectors.toList());
            getY[1] = lm.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return fail("At least one of the following can't be found: getScannedString, getArthur, Avatar.getHead, Head.getX, Head.getY");
        }
        
        boolean[] results = checkInterpretMove(commandInterpreterConstructor, bridgeSceneConstructor, scannerBeanConstructor, bridgeFirst, setCommand, getScannedString, getArthur, getX, getY);

        if (results.length == 1) {
            return fail("Failed to instantiate CommandInterpreter");
        } else {
            int correct = count(results, true);
            int possible = results.length;
            if (correct == 0) {
                return fail("Incorrect or no fail");
            } else if (correct == possible) {
                return pass();
            } else {
                double score = ((double)correct) / possible;
                String message = buildMessage(results);
                return partialPass(score, message);
            }   
        }
    }
        
    private static boolean[] checkInterpretMove(Constructor<?> commandInterpreterConstructor, Constructor<?> bridgeSceneConstructor, Constructor<?> scannerBeanConstructor, boolean bridgeFirst, Method setCommand, Method getScannedString, Method getArthur, Method[] getX, Method[] getY) {
        boolean[] ret = new boolean[4];
        Object scannerBeanInstance = ExecutionUtil.timedInvoke(scannerBeanConstructor, new Object[]{});
        Object bridgeSceneInstance = ExecutionUtil.timedInvoke(bridgeSceneConstructor, new Object[]{});
        
        
        MethodExecutionTestCase.MethodEnvironment[] methods = new MethodExecutionTestCase.MethodEnvironment[]{
            MethodEnvironment.get(bridgeSceneInstance, getArthur),                                                      // 0
            MethodEnvironment.get(MethodExecutionTestCase.CYCLIC_GET_PROPERTY, MethodExecutionTestCase.M0_RET, getX),   // 1
            MethodEnvironment.get(MethodExecutionTestCase.CYCLIC_GET_PROPERTY, MethodExecutionTestCase.M0_RET, getY) ,  // 2
            MethodEnvironment.get(setCommand, TEST_COMMAND),                                                            // 3
            MethodEnvironment.get(scannerBeanInstance, getScannedString),                                               // 4
            MethodEnvironment.get(MethodExecutionTestCase.CYCLIC_GET_PROPERTY, MethodExecutionTestCase.M0_RET, getX),   // 5
            MethodEnvironment.get(MethodExecutionTestCase.CYCLIC_GET_PROPERTY, MethodExecutionTestCase.M0_RET, getY)    // 6
        };
        
        Object[] exData;
        if (bridgeFirst) {
            exData = MethodExecutionTestCase.invoke(commandInterpreterConstructor,
                new Object[]{bridgeSceneInstance, scannerBeanInstance}, methods);
        } else {
            exData = MethodExecutionTestCase.invoke(commandInterpreterConstructor,
                new Object[]{scannerBeanInstance, bridgeSceneInstance}, methods);
        }
        System.err.println(Arrays.toString(exData));
        
        if (exData.length == 1) {
            return new boolean[]{false};
        }
        
        ret[0] = checkNotIntance(exData, 3, Exception.class);
        ret[1] = checkEqual(exData, 4, TEST_COMMAND);
        ret[2] = checkGTValue(exData, 5, 1) == 0;
        ret[3] = checkGTValue(exData, 6, 2) == 0;
        
        System.err.println(Arrays.toString(ret));
        
        return ret;
    }
    
    private static boolean checkNEqual(Object[] results, int a, Object value) {
        if (a >= results.length) {
            return false;
        }
        
        return !Objects.equals(results[a], value);
    }
    
    private static boolean checkEqual(Object[] results, int a, Object value) {
        if (a >= results.length) {
            return false;
        }
        
        return Objects.equals(results[a], value);
    }
    
    private static boolean checkNotIntance(Object[] results, int a, Class<?> c) {
        return a < results.length && !c.isInstance(results[a]);
    }
    
    private static int checkEqualValue(Object[] resutls, int a, int b) {
        return checkCompare(resutls, a, b, 0);
    }
    
    private static int checkLTValue(Object[] resutls, int a, int b) {
        return checkCompare(resutls, a, b, -1);
    }
    
    private static int checkGTValue(Object[] resutls, int a, int b) {
        return checkCompare(resutls, a, b, 1);
    }
    
    private static int checkCompare(Object[] results, int a, int b, int sign) {
        if (results.length < a || results.length < b) {
            return 1; // checking out of bounds
        }
        Object oA = results[a];
        Object oB = results[b];
        if (oA instanceof Exception || oB instanceof Exception) {
            return 2; // error in execution
        }
        if (!(oA instanceof Comparable) || !(oB instanceof Comparable)) {
            return 3; // not comparable
        }
        Comparable cA = (Comparable)oA;
        Comparable cB = (Comparable)oB;
        if (Math.signum(cA.compareTo(cB)) != sign) {
            return 4; // check comparison
        }
        
        return 0;
    }
    
    private static String buildMessage(boolean[] notes) {
        StringBuilder ret = new StringBuilder();
        if (notes[0] == false) {
            ret.append("Exception when setting command\n");
        }
        if (notes[1] == false) {
            ret.append("The ScannerBean's ScannedString is not set properly\n");
        }
        if (notes[2] == false) {
            ret.append("Does not move avatar right with positive X value\n");
        }
        if (notes[3] == false) {
            ret.append("Does not move avatar down with positive Y value\n");
        }
        return ret.toString();
    }
    
    private static int count(boolean[] arr, boolean value) {
        int count = 0;
        for(boolean bool : arr) {
            if (bool == value) {
                count ++;
            }
        }
        return count;
    }
}
