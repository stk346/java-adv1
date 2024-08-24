package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureCancelMain {

//    private static boolean mayInterruptIfRunning = true; // 변경
    private static boolean mayInterruptIfRunning = false; // 변경

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
//        log("Future.state: " + future.state()); // java 19 이상에서만 가

        // 일정 시간 후 취소
        sleep(3000);

        // cancel() 호출
        log("Future.cancel( " + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        log("cancel( " + mayInterruptIfRunning + ") result: " + cancelResult);

        // 결과 확인
        try {
            log("Future.result: " + future.get());
        } catch (CancellationException e) {
            log("Future는 이미 취소되었습니다.");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        es.shutdown();
    }

    static class MyTask implements Callable {
        @Override
        public String call() throws Exception {
            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중: " + i);
                    Thread.sleep(1000); // 1초 동안 sleep
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }
            return "Completed";
        }
    }

}
