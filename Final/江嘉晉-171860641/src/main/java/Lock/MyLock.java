package tk.jimkong.project.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLock {
    public static Lock lock;
    public static Lock guiLock;
    public static int guiState;
    public static int stop;

    static {
        lock = new ReentrantLock(true);
        guiLock = new ReentrantLock(true);
        guiState = 1;
        stop = 0;
    }
}
