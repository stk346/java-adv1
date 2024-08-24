package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService service) {

        if (service instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size(); // 작업 대기 큐에 쓰레드가 얼마나 들어가 있는지
            long completedTasks = poolExecutor.getCompletedTaskCount();
            log("[pool=" + pool + ", active=" + active +
                    ", queuedTasks=" + queuedTasks + ", completedTasks=" + completedTasks + "]");
        } else {
            log(service);
        }
    }

}
