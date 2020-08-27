package cn.java.entity;
//静态内部类实现单例模式
public class Singleton {
    private static volatile Singleton instance;
    //构造器私有化
    private Singleton() {
    }
    //写一个静态内部类，该类中有一个静态属性Singleton
    private static class SingletonInstance{
        {
            System.out.println("进入静态内部类");
        }
        private static Singleton INSTANCE = new Singleton();
    }
    //提供一个静态的公共方法，直接返回SingletonInstace.INSTANCE
    public static Singleton getInstance(){
        return SingletonInstance.INSTANCE;
    }
}
