package xyz.jiemin;

/**
 * Created by jiemin on 2022/10/10 9:41
 */

/**
 * volatile
 */
public class NumberUnsafe {


    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
            int j = 1;
            new Thread(() -> {

//                j++;

            }, "线程" + i).start();
        }

    }


}
