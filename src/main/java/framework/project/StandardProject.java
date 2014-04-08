package framework.project;

import framework.execution.*;
import grader.trace.project.BinaryFolderNotFound;
import scala.Option;
import tools.DirectoryUtils;
import util.trace.TraceableLog;
import util.trace.TraceableLogFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A "standard" project. That is, an IDE-based java project.
 */
public class StandardProject implements Project {

    private File directory;
    private File sourceFolder;
    private Option<ClassesManager> classesManager;
    private TraceableLog traceableLog;

    /**
     * Basic constructor
     *
     * @param directory The location of the project
     * @param name      The name of the project, such as "Assignment1"
     * @throws FileNotFoundException
     */
    public StandardProject(File directory, String name) throws FileNotFoundException {
        // Find the folder. We could be there or it could be in a different folder
        Option<File> src = DirectoryUtils.locateFolder(directory, "src");
        if (src.isEmpty())
            throw new FileNotFoundException("No src folder");
        sourceFolder = src.get();
        this.directory = src.get().getParentFile();

        try {
            File sourceFolder = new File(this.directory, "src");
            File buildFolder = getBuildFolder("main." + name);
            classesManager = Option.apply((ClassesManager) new ProjectClassesManager(buildFolder, sourceFolder));
        } catch (Exception e) {
            classesManager = Option.empty();
        }

        // Create the traceable log
        traceableLog = TraceableLogFactory.getTraceableLog();

    }

    /**
     * This figures out where the build folder is, taking into account variations due to IDE
     *
     * @param preferredClass The name of the class that has the main method, such as "main.Assignment1"
     * @return The build folder
     * @throws FileNotFoundException
     */
    public File getBuildFolder(String preferredClass) throws FileNotFoundException {
        Option<File> out = DirectoryUtils.locateFolder(directory, "out");
        Option<File> bin = DirectoryUtils.locateFolder(directory, "bin");

        // If there is no 'out' or 'bin' folder then give up
        if (out.isEmpty() && bin.isEmpty()) {
//            throw new FileNotFoundException();
        	BinaryFolderNotFound.newCase(directory.getAbsolutePath(), this);
        	File retVal = new File(directory, "bin");
        	retVal.mkdirs();
        	return retVal.getAbsoluteFile();
        	
        } else {
            // There can be more folders under it, so look around some more
            // But first check the class name to see what we are looking for
            File dir = null;
            if (out.isDefined())
                dir = out.get();
            if (bin.isDefined())
                dir = bin.get();

            if (preferredClass.contains(".")) {
                Option<File> packageDir = DirectoryUtils.locateFolder(dir, preferredClass.split("\\.")[0]);
                if (packageDir.isDefined())
                    return packageDir.get().getParentFile();
                else
                    return dir;
            } else
                return dir;
        }
    }

    @Override
    public TraceableLog getTraceableLog() {
        return traceableLog;
    }

    @Override
    public RunningProject start(String input) throws NotRunnableException {
        return new ReflectionRunner(this).run(input);
    }

    @Override
    public RunningProject launch(String input) throws NotRunnableException {
        return new ProcessRunner(this).run(input);
    }

    @Override
    public RunningProject start(String input, int timeout) throws NotRunnableException {
        return new ReflectionRunner(this).run(input, timeout);
    }

    @Override
    public RunningProject launch(String input, int timeout) throws NotRunnableException {
        return new ProcessRunner(this).run(input, timeout);
    }

    @Override
    public RunningProject launchInteractive() throws NotRunnableException {
        return new InteractiveConsoleProcessRunner(this).run("");
    }

    @Override
    public Option<ClassesManager> getClassesManager() {
        return classesManager;
    }

    @Override
    public File getSourceFolder() {
        return sourceFolder;
    }
}
