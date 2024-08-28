package thread.executor;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

public class ExecutorShutdownMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000)); // 100초 대기
        ExecutorUtils.printState(es);
        log("== showdown 시작");
        shutdownAndAwaitTermination(es);
        log("== showdown 완료");
        ExecutorUtils.printState(es);
   }

    // 1. 정상 종료 시도
    // 2. 10초 동안 메인쓰레드가 기다림
    // 3. 그래도 안끝나면 강제 종료 시도
    // 4. 강제 종료 시도 후 10초 기다림
    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업을 받지 않는다. 처리 중이거나, 큐에 이미 대기중인 작업은 처리한다. 이후에 풀의 쓰레드를 종료한다.
        try {
            // 이미 대기중인 작업들을 모두 완료할 때 까지 10초 기다린다.
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                // 정상 종료가 너무 오래 걸리면
                log("정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow();
                // 작업이 취소될 때 까지 대기한다.
                // (인터럽트가 걸리더라도 작동할 때 까지는 시간이 걸림)
                // ex) 쓰레드가 while (true)와 같은 무한루프를 실행중일 때
                if (es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }

            }
        } catch (InterruptedException e) {
            // awaitTermination() 으로 대기중인 쓰레드가 인터럽트될 수 있다.
            es.shutdownNow();
        }

    }
}
