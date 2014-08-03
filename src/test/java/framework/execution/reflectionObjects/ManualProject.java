package framework.execution.reflectionObjects;

import framework.execution.NotRunnableException;
import framework.execution.RunningProject;
import framework.project.ClassesManager;
import framework.project.Project;
import scala.Option;
import util.pipe.OutputBasedInputGenerator;
import util.trace.TraceableLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ManualProject implements Project {

    private ClassesManager classesManager;

    public ManualProject() {
        classesManager = new ManualClassesManager(Program.class);
    }

    @Override
    public RunningProject start(String input) {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    @Override
    public RunningProject launch(String input) {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    @Override
    public RunningProject start(String input, int timeout) throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RunningProject launch(String input, int timeout) throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RunningProject launchInteractive() throws NotRunnableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Option<ClassesManager> getClassesManager() {
        return Option.apply(classesManager);
    }

    @Override
    public File getSourceFolder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public File getBuildFolder(String preferredClass) throws FileNotFoundException {
        return new File("./");
    }

    @Override
    public TraceableLog getTraceableLog() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public RunningProject launch(
			OutputBasedInputGenerator anOutputBasedInputGenerator,
			String input, int timeout) throws NotRunnableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RunningProject launch(
			OutputBasedInputGenerator anOutputBasedInputGenerator,
			Map<String, String> aProcessToInput, int timeout)
			throws NotRunnableException {
		// TODO Auto-generated method stub
		return null;
	}
}
