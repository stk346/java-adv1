package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("submit 호출");
        Future<Integer> future = es.submit(new MyCallable()); // 여기서 future이 작업을 완료 했을지 못했을지 이해하는 것이 중요!
        log("future 즉시 반환, future = " + future);

        log("future.get() [블로킹] 메서드 호출 시작 -> main 쓰레드 WAITING");
        Integer result = future.get();
        log("future.get() [블로킹] 메서드 호출 완료 -> main 쓰레드 RUNNABLE");

        log("result value = " + result);
        log("future 완료, future = " + future);
        es.shutdown();
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("created value = " + value);
            log("Callable 완료");
            return value;
        }
    }
}
