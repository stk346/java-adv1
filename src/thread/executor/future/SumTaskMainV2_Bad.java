package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class SumTaskMainV2_Bad {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = es.submit(task1);
        Integer result1 = future1.get();

        Future<Integer> future2 = es.submit(task2);
        Integer result2 = future2.get();

        log("task1.result = " + result1);
        log("task2.result = " + result2);

        int sumAll = result1 + result2;
        log("task1 + task2 = " + sumAll);
        log("End");

        es.shutdown();
    }

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            log("작업 시작");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;;
            }
            log("작업 완료, result = " + sum);
            return sum;
        }
    }
}
