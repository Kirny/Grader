package grader.project;

import util.misc.TeePrintStream;
import grader.sakai.project.SakaiProject;
import grader.trace.overall_transcript.OverallTranscriptSaved;
import grader.trace.stepper.feature.transcript.FeatureTranscriptSaved;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class AReflectionBasedProjectRunner implements Runnable {
    public static final String DEFAULT_OUTPUT_FILE_NAME = "output.txt";

    String projectName;
    String mainClassName;
    String[][] mainArgs;
    Project project;
    String[] outputFiles;
    String[] inputFiles;
    Method mainMethod;
    Class mainClass;
    boolean appendedToTranscript;


    public AReflectionBasedProjectRunner(String aMainClassName, String[][] aMainArgs, Project aProject, String[] anInputFiles, String[] anOutputFiles, Class aMainClass, Method aMainMethod) {
        projectName = aProject.getProjectFolderName();
        mainClassName = aMainClassName;
        mainArgs = aMainArgs;
        project = aProject;
        outputFiles = anOutputFiles;
        inputFiles = anInputFiles;
        mainMethod = aMainMethod;
        mainClass = aMainClass;
    }

    public void run() {
        try {

            InputStream stdin = null;
            PrintStream stdout = null;
            PrintStream originalOut = System.out;
            if (inputFiles.length == 0) {
                inputFiles = new String[1];
            }

            if (outputFiles.length == 0) {
                outputFiles = new String[]{project.getOutputFileName()};
                appendedToTranscript = true;
            }
            if (mainArgs.length == 0) {
                mainArgs = new String[1][];
            }

            for (int i = 0; i < inputFiles.length; i++) {

                String inputFile = inputFiles[i];
                String outputFile = outputFiles[i];
                Object[] args = {mainArgs[i]};
                if (inputFile != null) {
                    stdin = new FileInputStream(inputFile);
                }
                if (outputFile != null) {
                    stdout = new TeePrintStream(new FileOutputStream(outputFile), originalOut);
                }
                if (stdin != null) {
                    System.setIn(stdin);
                }
                if (stdout != null) {
                    System.setOut(stdout);
                }
                try {

                    mainMethod.invoke(mainClass, args);
                    if (outputFile != null) {
                        stdout.close();
                    }
                    if (inputFile != null) {
                        stdin.close();
                    }
                } catch (Exception e) {
                    System.out.println("Could not successfully run:" + projectName + "with input file:" + inputFile);
                    e.printStackTrace();
                }
                project.setHasBeenRun(true);

                System.out.println("terminated main method");
                if (appendedToTranscript)
                	OverallTranscriptSaved.newCase(null, null,   (SakaiProject) project, project.getOutputFileName(), "???", this);
            }

        } catch (Exception e) {
            System.out.println("Could not run:" + projectName);
            project.setCanBeRun(false);
            e.printStackTrace();
        }
    }

}
