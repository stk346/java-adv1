package thread.collection.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListMain {

    public static void main(String[] args) {
        // 이렇게 하면 컬렉션의 모든 메서드에 synchronized가 걸려 오버헤드가 발생하고 최적화가 불가능하다.
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add("data1");
        list.add("data2");
        list.add("data3");
        System.out.println(list.getClass());
        System.out.println("list = " + list);
    }
}
