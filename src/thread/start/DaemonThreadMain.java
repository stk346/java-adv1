package thread.start;

public class DaemonThreadMain {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " : main() start");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); // 데몬 쓰레드 여부
        // 데몬 쓰레드는 백그라운드에서 실행되기 때문에 main 쓰레드 스택이 비워지자 마자 종료됨
        // -> run() 메소드에서 10초를 기다리는 동안 main 쓰레드 스택이 비워지고 프로세스 종료
        daemonThread.start();
        System.out.println(Thread.currentThread().getName() + " : main() end");
    }

    static class DaemonThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : run() start");
            try {
                Thread.sleep(10000); // 10초간 실행
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " : run() end");
        }
    }
}
