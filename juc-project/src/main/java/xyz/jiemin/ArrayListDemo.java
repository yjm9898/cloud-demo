package xyz.jiemin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by jiemin on 2022/10/10 9:02
 */
public class ArrayListDemo {

    //arraylist 是线程不安全的； 并发添加会出现concurrentmodified报错
    //解决方案： 使用vector;  使用Collections.synchronizedList();  CopyOnWriteArrList;
    public static void main(String[] args) {
//        Vector<String> list = new Vector<>();
        List<String> list = Collections.synchronizedList(new ArrayList());

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, "线程" + String.valueOf(i)).start();
        }
    }


}
