package grader.checkStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.misc.Common;
import framework.execution.ProcessRunner;
import framework.execution.Runner;
import framework.execution.RunningProject;
import framework.utils.GradingEnvironment;
import grader.assignment.AnAssignmenDataFolder;
import grader.compilation.ClassFilesCompiler;
import grader.documents.AWordDocumentDisplayer;
import grader.documents.DocumentDisplayer;
import grader.language.LanguageDependencyManager;
import grader.project.folder.ARootCodeFolder;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.settings.GraderSettingsManagerSelector;
import grader.trace.file.open.WordOpenedFile;

public class AJavaCheckStyleInvoker  implements CheckStyleInvoker{
	
	
	
	
	

	
	public RunningProject checkStyle(String aSourceFileFlder) {
		String aConfigurationFileName = ASakaiProjectDatabase.getCurrentSakaiProjectDatabase().getAssignmentDataFolder().getCheckStyleConfigurationFileName();

//        String windowsName = Common.toWindowsFileName(aSourceFileFlder);
			String windowsName = aSourceFileFlder;
        
//        String[] command = {compilerPath, windowsName, "-o",  fullObjName , EXECUTABLE_OPTION  + fullExecName};
        String[] command = {"java", "com.puppycrawl.tools.checkstyle.Main", "-c",  aConfigurationFileName ,   windowsName };
// java com.puppycrawl.tools.checkstyle.Main -c ../UNCCheckStyle/unc_checks.xml \
//        src/ > checkstyle.txt
//        String[] command = {compilerPath, windowsName};

//        String[] command = {"Test Data/Test C/Assignment1/All, Correct (acorrect)/Submission attachment(s)/program1/Program1/src/Simple.exe"};
        String[] args = {};
        Runner processRunner = new ProcessRunner(new File("."));
        return processRunner.run(null, command, "", args, 2000);
//		Common.exec(command);

//        Common.exec(command);
//       WordOpenedFile.newCase(aFileName, this);

    }
	
	
	public static void main (String[] args) {
        
		AJavaCheckStyleInvoker checkStyleInvoker = new AJavaCheckStyleInvoker();
//		compiler.compileFile("Test Data/Test C/Assignment1/All, Correct (acorrect)/Submission attachment(s)/program1/Program1/src/Simple.c",
//				"Test Data/Test C/Assignment1/All, Correct (acorrect)/Submission attachment(s)/program1/Program1/bin");
//		compiler.compileFile("src/Simple.c",
//				"Test Data/Test C/Assignment1/All, Correct (acorrect)/Submission attachment(s)/program1/Program1/bin");
		File aFile = new File("D:/dewan_backup/Java/Comp110-Grader/Test Data/Correct 110 F13 Results/Assignment3/All, Correct (acorrect)/Submission attachment(s)/acorrect_program3/Assignment3/src");
		if (!aFile.exists()) {
			System.err.println("No file");
			
		} else {
			RunningProject aProject;
			try {
				aProject = checkStyleInvoker.checkStyle (aFile.getCanonicalPath());
			
			String anOutput = aProject.getOutput();
			System.out.println("Check sty;e output:" + anOutput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Main ends");
//		checkStyleInvoker.checkStyle ("Test Data/Test 110 F13 Assignments/Assignment3/All, Correct (acorrect)/Submission attachment(s)/program1/Program1/src/");
//		checkStyleInvoker.checkStyle ("src/src");

//		compiler.compileFile("src/Simple.c",
//				"Test Data/Test C/Assignment1/All, Correct (acorrect)/Submission attachment(s)/program1/Program1");
    }

	
	

}
