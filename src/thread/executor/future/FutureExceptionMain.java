package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000); // 잠시 대기

        try {
//        log("future.get() 호출 시도, future.state(): " + future.state());
            Integer result = future.get();
            System.out.println("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) { // Future는 원본 예외를 ExecutionException으로 감싸서 던진다.
            log("e = " + e);
            // ExecutionException에서 원본 예외를 추출
            Throwable cause = e.getCause(); // 원본 예외
            log("cause = " + cause);
        }
        es.shutdown();
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalArgumentException("ex!");
        }
    }
}
