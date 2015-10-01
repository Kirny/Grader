package gradingTools.comp401f15.assignment4.testcases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.util.IntrospectionUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew Vitkus
 */
public class ScannerBeanReturnsTokenInterfaceArrayTestCase extends BasicTestCase {
	public static final String COMMON_TOKEN_INTERFACE = "CommonTokenInterface";
	public static final String TOKEN_METHOD = "Token Method";

    String[] scannerDescriptions = {null, "ScannerBean", ".*Bean.*", ".*Bean.*"};
    
    private static final String[][] tokenDescriptions = new String[][]{{null, "End", ".*End.*", ".*End.*"},
                                                                       {null, "Minus", ".*Minus.*", ".*Minus.*"},
                                                                       {null, "Number", ".*Number.*", ".*Number.*"},
                                                                       {null, "Plus", ".*Plus.*", ".*Plus.*"},
                                                                       {null, "Quote", ".*Quote.*", ".*Quote.*"},
                                                                       {null, "Start", ".*Start.*", ".*Start.*"},
                                                                       {null, "Word", ".*Word.*", ".*Word.*"},
                                                                       {null, "call", ".*call.*", ".*call.*"},
                                                                       {null, "define", ".*define.*", ".*define.*"},
                                                                       {null, "move", ".*move.*", ".*move.*"},
                                                                       {null, "proceedAll", ".*proceedAll.*", ".*proceedAll.*"},
                                                                       {null, "redo", ".*redo.*", ".*redo.*"},
                                                                       {null, "repeat", ".*repeat.*", ".*repeat.*"},
                                                                       {null, "rotateLeftArm", ".*rotateLeftArm.*", ".*rotateLeftArm.*"},
                                                                       {null, "rotateRightArm", ".*rotateRightArm.*", ".*rotateRightArm.*"},
                                                                       {null, "say", ".*say.*", ".*say.*"},
                                                                       {null, "sleep", ".*sleep.*", ".*sleep.*"},
                                                                       {null, "thread", ".*thread.*", ".*thread.*"},
                                                                       {null, "undo", ".*undo.*", ".*undo.*"},
                                                                       {null, "wait", ".*wait.*", ".*wait.*"}};

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        Class tokenInterface = IntrospectionUtil.getCommonInterface(project, tokenDescriptions);
        if (tokenInterface == null) {
            return fail("No common interface for all tokens");
        }
        getCheckable().getRequirements().putUserObject(COMMON_TOKEN_INTERFACE, tokenInterface);
        Class scannerClass = IntrospectionUtil.findClass(project, 
                                scannerDescriptions[0],
                                scannerDescriptions[1],
                                scannerDescriptions[2],
                                scannerDescriptions[3]);
        String methodName = "getTokens";
        String methodRegex = ".*get.*[tT]oken.*";
//        String methodName = "foo";
//        String methodRegex = ".*get.*bar.*";
        boolean getTokensNamedWrong = false;
        try {
            Method getTokensMethod;
            try {
                getTokensMethod = scannerClass.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                getTokensMethod = null;
                getTokensNamedWrong = true;
            }
            if (getTokensMethod == null) {
                for(Method m : scannerClass.getMethods()) {
                    if (m.getName().matches(methodRegex)) {
                        getTokensMethod = m;
                        break;
                    }
                    Class retType = m.getReturnType();
                    if (retType.isArray()) {
                        if(retType.equals(Array.newInstance(tokenInterface, 0).getClass())) {
                            getTokensMethod = m;
                        }
                    }
                }
            }
            if (getTokensMethod == null) {
                return fail("No getTokens method or other method returning a token typed array");
            }
            getCheckable().getRequirements().putUserObject(TOKEN_METHOD, getTokensMethod);

            Class returnType = getTokensMethod.getReturnType();
            if (!returnType.isArray()) {
                if (getTokensNamedWrong) {
                    return partialPass(0.05, "getTokens method does not return an array and is named incorrectly");
                } else {
                    return partialPass(0.1, "getTokens method does not return an array");
                }
            }
            if(!returnType.equals(Array.newInstance(tokenInterface, 0).getClass())) {
                if (getTokensNamedWrong) {
                    return partialPass(0.25, "getTokens method does not return common token interface type array and is named incorrectly");
                } else {
                    return partialPass(0.5, "getTokens method does not return common token interface type array");
                }
            }
        } catch (SecurityException ex) {
            throw new NotGradableException();
        }
        if (getTokensNamedWrong) {
            return partialPass(0.9, "tokens getter should be " + methodName);
        } 
        return pass();
    }
    
    public ScannerBeanReturnsTokenInterfaceArrayTestCase() {
        super("Scanner Bean Returns Token Inteface Array Test Case");
    }
//    public static List<Class> getAllInterfaces(Class aClass) {
//    	List<Class> retVal = new ArrayList();
//    	while (aClass != null) {
//    	   retVal.addAll(Arrays.asList(aClass.getInterfaces()));
//    	   aClass = aClass.getSuperclass();
//    	}
//    	return retVal;
//    	
//    }
    
//    private static Class getCommonInterface(Project project) {
//        List<Class> interfaces = new ArrayList<>(5);
//        for(String[] classDescriptions : tokenDescriptions) {
//            Class aClass = IntrospectionUtil.findClass(project, 
//                            classDescriptions[0],
//                            classDescriptions[1],
//                            classDescriptions[2],
//                            classDescriptions[3]);
//            if (aClass == null) 
//            	continue;
//            	
//            Class[] tokenInterfaces = aClass.getInterfaces();
//            if(tokenInterfaces.length == 0) {
//                return null;
//            } else {
//                if (interfaces.isEmpty()) {
//                    interfaces.addAll(Arrays.asList(tokenInterfaces));
//                } else {
//                    interfaces.retainAll(Arrays.asList(tokenInterfaces));
//                }
//            }
//        }
//        if (interfaces.size() != 1) {
//            return null;
//        } else {
//            return interfaces.get(0);
//        }
//    }
//    public static Class getCommonInterface(Project project, String[][] aClassDescriptions) {
//        List<Class> interfaces = new ArrayList<>(5);
//        for(String[] classDescriptions : aClassDescriptions) {
//            Class aClass = IntrospectionUtil.findClass(project, 
//                            classDescriptions[0],
//                            classDescriptions[1],
//                            classDescriptions[2],
//                            classDescriptions[3]);
//            if (aClass == null) 
//            	continue;
//            	
//           List<Class> tokenInterfaces = IntrospectionUtil.getAllInterfaces(aClass);
//            if(tokenInterfaces.size() == 0) {
//                return null;
//            } else {
//                if (interfaces.isEmpty()) {
//                    interfaces.addAll(tokenInterfaces);
//                } else {
//                    interfaces.retainAll(tokenInterfaces);
//                }
//            }
//        }
//        if (interfaces.size() != 1) {
//            return null;
//        } else {
//            return interfaces.get(0);
//        }
//    }
}
