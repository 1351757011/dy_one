package cn.java.demo;

import cn.java.entity.Singleton;
import cn.java.entity.Test;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Demo {
    private List<int []> list = new ArrayList<>();
    private static int[] init;
    private static int count = 0;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runtime.getRuntime();//单例模式的应用     Calendar简单工厂模式的应用
        int [] arr = {1,2,3,4,5,6};
        init = new int[arr.length];
//        show(arr);
        System.out.println(count);
/*        for (int i = 0; i< 100; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Singleton instance = Singleton.getInstance();
                    System.out.println(instance);
                }
            }).start();
            FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "*********";
                }
            });
            futureTask.run();
            String s = futureTask.get();
            System.out.println(s);
        }*/
        String s = "hello";
        System.out.println(s.hashCode());
        s += "world";
        System.out.println(s.hashCode());
        String s2 = new String("hello");
        System.out.println(s2.hashCode());
        s2 += "world";
        System.out.println(s2.hashCode());
        System.out.println(s==s2);
    }
    public static void show(int[] arr){
        int[] brr = new int[arr.length];
        for (int i = 0;i < brr.length;i++){
//            System.out.println(arr[i]);
            init[init.length-arr.length] = arr[i];
            int[] crr = new int[brr.length-1];
            if (brr.length == 1){
                count++;
                System.out.println(Arrays.toString(init));
            }
            int k = 0;
            for (int j = 0;j < arr.length;j++){
                if (j != i){
                    crr[k] = arr[j];
                    k++;
                }
            }
            show(crr);
        }
    }

}
