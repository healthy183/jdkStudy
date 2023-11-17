
import java.util.HashSet;

/**
 * User:
 * Description:
 * Date: 2023-11-11
 * Time: 8:59
 */
public class ArthasDemo {

    private static HashSet<String> hashSet = new HashSet();

    public static void main(String[] args) {
        cpuHigh();
        deadLock();
        addHashSetThread();
    }

    private static void deadLock() {
        Object lockA = new Object();
        Object lockB = new Object();

        new Thread(()->{
            synchronized (lockA){
                try {
                    System.out.println("threadA begin");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                synchronized (lockB){
                    System.out.println("threadA end");
                }
            }
        }).start();

        new Thread(()->{
            System.out.println("threadB begin");
            synchronized (lockB){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                synchronized (lockA){
                    System.out.println("threadB end");
                }
            }
        }).start();
        System.out.println("main thread stop");
    }

    public static void  cpuHigh(){
        new Thread(()->{
            while (true){
            }
        }).start();
    }

    public static void  addHashSetThread(){
        new Thread(()->{
            int count = 0;
            while (true){
                try {
                    count++;
                    hashSet.add("count"+count);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
