package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimedProcess {

    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    private Process process;
    private ProcessBuilder processBuilder;
    private long timeout;

    public TimedProcess(ProcessBuilder builder, long timeout) {
        processBuilder = builder;
        this.timeout = timeout;
    }

    public Process start() throws IOException {
        process = processBuilder.start();
        return process;
    }
    
    public void destroy() {
    	process.destroy();
    }

    public InputStream getInputStream() {
        return process.getInputStream();
    }

    public OutputStream getOutputStream() {
        return process.getOutputStream();
    }
    
    public InputStream getErrorStream() {
    	return process.getErrorStream();
    }

    public Process getProcess() {
        return process;
    }

    public int waitFor() throws InterruptedException, ExecutionException, TimeoutException {
        // Don't timeout if the timeout is -1
        if (timeout == -1)
            return process.waitFor();

        return timedCall(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return process.waitFor();
            }
        }, timeout, TimeUnit.SECONDS);
    }
    private static <T> T timedCall(Callable<T> c, long timeout, TimeUnit timeUnit)
            throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<T> task = new FutureTask<T>(c);
        THREAD_POOL.execute(task);
        return task.get(timeout, timeUnit);
    }

}
