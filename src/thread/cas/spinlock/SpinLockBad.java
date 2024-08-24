package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockBad {

    private volatile boolean lock = false;

    // 1, 2번이 원자적이지 않기 때문에 문제가 발생
    public void lock() {
        log("락 획득 시도");
        while (true) {
            if (!lock) { // 1. 락 사용 여부를 확인
                sleep(100); // 문제 상황 확인용. 쓰레드 대기
                lock = true; // 2. 락의 값 변경 (락 획득을 가정)
                break;
            } else {
                // 락을 획득할 때 까지 스핀 대기(바쁜 대기)를 한다.
                log("락 획득 실패 - 스핀 대기");

            }
        }
        log("락 획득 완료");
    }

    public void unlock() {
        lock = false;
        log("락 반납 완료");
    }
}
