package thread.collection.simple.list;

import java.util.Arrays;

import static util.ThreadUtils.sleep;

public class BasicList implements SimpleList {

    private static final int DEFAULT_CAPACITY = 5;

    private Object[] elementData;
    private int size;

    public BasicList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    // 컬렉션의 add() 메서드는 원자적 연산이 아니다.
    @Override
    public void add(Object e) {
        elementData[size] = e;
        sleep(100); // 멀티쓰레드 문제를 쉽게 확인할 수 있도록 sleep
        size++;
    }

    @Override
    public Object get(int index) {
        return elementData[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elementData, size)) +
                " size=" + size + ", capacity=" + elementData.length;
    }
}
