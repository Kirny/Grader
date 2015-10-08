package gradingTools.comp401f15.assignment4.testcases.commands.created;

import framework.grading.testing.BasicTestCase;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import grader.util.ExecutionUtil;
import grader.util.IntrospectionUtil;
import gradingTools.comp401f15.assignment4.testcases.ScannerBeanReturnsTokenInterfaceArrayTestCase;

public abstract class AbstractCommandCreatedTestCase extends BasicTestCase {

    public AbstractCommandCreatedTestCase(String aName) {
        super(aName);
    }

    String[] beanDescriptions = {null, "ScannerBean", ".*Bean.*", ".*Bean.*"};
    Class[] constructorArgTypes2 = {String.class};
    Class[] constructorArgTypes1 = {};
    String[] constructorArgs2 = {""};
    String[] constructorArgs1 = {};
    
    protected String commandIdentifier() { return "";}
    protected String[] commandDescriptions() { return new String[]{null, "", "", ""};}
    protected String commandName() { return "";}

    protected String inputEndingSpaces() {return commandName() + " ";}
    protected String input(){return commandName();}
    protected String tokenPropertyName = "Tokens";
    
    String[] outputPropertyNames()  {
    	Method getTokenswMethod = (Method) getCheckable().getRequirements().getUserObject(ScannerBeanReturnsTokenInterfaceArrayTestCase.TOKEN_METHOD);
    	if (getTokenswMethod != null) {
    		tokenPropertyName = getTokenswMethod.getName().substring(3);
    	} 
    		return new String[]{tokenPropertyName};
    	
    	};

    public TestCaseResult test(Project aProject, Class[] aConstructorArgTypes, Object[] aConstructorArgs, String aScannedString) throws NotAutomatableException, NotGradableException {
        Map<String, Object> anInputs = new HashMap();
        anInputs.put("ScannedString", aScannedString);
        Map<String, Object> anActualOutputs = ExecutionUtil.testBean(getCheckable().getName(), getName(), aProject, beanDescriptions, aConstructorArgTypes, aConstructorArgs, anInputs, outputPropertyNames());

        if (anActualOutputs.get(ExecutionUtil.MISSING_CLASS) != null) { // only output, no object
            return fail("Could not find scanner bean");
        }
        if (!anActualOutputs.containsKey(ExecutionUtil.MISSING_READ)) {
            Object tokenRet = anActualOutputs.get(tokenPropertyName);
            if (tokenRet instanceof Object[]) {
                Object[] tokens = (Object[])tokenRet;
                Class aClass = IntrospectionUtil.findClass(aProject, 
                            commandDescriptions()[0],
                            commandDescriptions()[1],
                            commandDescriptions()[2],
                            commandDescriptions()[3]);
                if (aClass == null) {
                    return fail("Cannot find a class for the '" + commandName() + "' command token");
                }
                if (aClass.isInstance(tokens[0])) {
                    return pass("Correctly creates '" + commandName() + "' command tokens");
                } else {
                    return fail("Fails to create '" + commandName() + "' command tokens");
                }
            }
        }
        return fail("Scanner does not return token array");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty()) {
            throw new NotGradableException();
        }

        TestCaseResult result = test(project, constructorArgTypes1, constructorArgs1, inputEndingSpaces());
        if (result.getNotes().contains("Could not find scanner bean")) {
            return result;
        }
        if (result.getPercentage() < 0.7) {
            result = test(project, constructorArgTypes2, constructorArgs2, inputEndingSpaces());
            if (result.getPercentage() < 0.7) {
                result = test(project, constructorArgTypes1, constructorArgs1, input());
                if (result.getPercentage() < 0.7) {
                    result = test(project, constructorArgTypes2, constructorArgs2, input());
                }
            }
        }
        return result;
    }
}