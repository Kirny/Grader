package gradingTools.comp401f15.assignment10.testCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import scala.Option;
import tools.CompilationNavigation;
import tools.classFinder2.ClassFinder;
import tools.classFinder2.ClassType;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ParsableClassDescription;
import grader.basics.project.ClassDescription;
import grader.basics.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class SayMoveCommandInvokedTestCase extends BasicTestCase {

    public SayMoveCommandInvokedTestCase() {
        super("Command objects returned from say and move are invoked.");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Get the command interpreter
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag("Command Interpreter", autoGrade, ClassType.CLASS);
        if (classDescription.isEmpty())
            return fail("Command interpreter not found.");
        //Class<?> _class = classDescription.get().getJavaClass();

        // The approach to check this is to check
        //  1. find the caller of the parser methods
        //  2. check that run() is called

        // Get the parser methods
        Option<Method> sayMethod = getMethodOption(classDescription, "parseSay");
        Option<Method> moveMethod = getMethodOption(classDescription, "parseMove");
        if (sayMethod.isEmpty() && moveMethod.isEmpty())
            return fail("Could not find either parser method");

        // Find where the parser methods are called.
        Set<MethodDeclaration> callers = new HashSet<>();
        callers.addAll(findCallers(classDescription.get(), sayMethod));
        callers.addAll(findCallers(classDescription.get(), moveMethod));

        // Look in each caller function for ".run()"
//        for (MethodDeclaration caller : callers) {
//            String code = caller.toString();
//            if (code.contains(".run()"))
//                return pass();
//        }
        if (callers.parallelStream().anyMatch(caller -> caller.toString().contains(".run()"))) {
            return pass();
        }
        return fail("Couldn't find a parser invoker that called .run()");
    }

    private Option<Method> getMethodOption(Option<ClassDescription> classDescription, String tag) {
        List<Method> methods = classDescription.get().getTaggedMethods(tag);
        return methods.isEmpty() ? Option.<Method>empty() : Option.apply(methods.get(0));
    }

    private List<MethodDeclaration> findCallers(ClassDescription classDescription, Option<Method> method) throws NotGradableException {
        if (method.isEmpty())
            return new ArrayList<>();
        List<MethodDeclaration> callers = new ArrayList<>();
        
        // Get the name of the method
        String mName = method.get().getName();

        // Look in the code for places where it is called
        try {
            ClassOrInterfaceDeclaration classDef = CompilationNavigation.getClassDef(((ParsableClassDescription) classDescription).parse());
            List<MethodDeclaration> methods = CompilationNavigation.getMethods(classDef);

            callers = methods.parallelStream()
                    .filter(m -> !m.getName().equals(mName))
                    .filter(m -> m.toString().contains(mName + "("))
                    .collect(Collectors.toList());
//            for (MethodDeclaration m : methods) {
//                if (!m.getName().equals(name)) {
//                    String code = m.toString();
//                    if (code.contains(name + "("))
//                        callers.add(m);
//                }
//            }
        } catch (IOException e) {
            throw new NotGradableException();
        }

        return callers;
    }
}

