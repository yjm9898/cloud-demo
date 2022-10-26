package xyz.jiemin.thread;

/**
 * Created by jiemin on 2022/10/26 20:02
 */
public class Thread01 {

    /**
     * Java 8 lambda 表达式。
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("main start ... ");
        Thread t = new Thread(
                () -> {
                    System.out.println("子线程 = "+ Thread.currentThread().getName() + " start");
                    System.out.println("我是子线程");
                    System.out.println("子线程 = "+ Thread.currentThread().getName() + " end");
                }
        );
        t.start();
        System.out.println("main end ... ");
    }


}
