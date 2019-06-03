package com.yukharin;

import com.yukharin.homes.Home;
import com.yukharin.host_threads.HostThread;
import com.yukharin.hosts.Host;
import com.yukharin.thief_threads.ThiefThread;
import com.yukharin.thieves.Thief;
import com.yukharin.utils.Utils;

import java.util.concurrent.*;

public class Main {

    private static final long TIMEOUT = 2L;
    private static final int HOSTS = 300;
    private static final int THIEVES = 50;
    private static final int ITEMS_PER_HOST = 3;
    private static final int TOTAL_THREADS = HOSTS + THIEVES;
    private static final Semaphore semaphore = new Semaphore(HOSTS);

    private static final Runnable startTask = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("Start !!!");
                Utils.printInfo();
                TimeUnit.SECONDS.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private static final CyclicBarrier start = new CyclicBarrier(TOTAL_THREADS, startTask);
    private static final Runnable endTask = new Runnable() {
        @Override
        public void run() {
            System.out.println("Finish !!!");
            Utils.printInfo();
        }
    };
    private static final CyclicBarrier finish = new CyclicBarrier(TOTAL_THREADS, endTask);

    public static void main(String[] args) {
        Home home = new Home();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < THIEVES; i++) {
            service.submit(new ThiefThread(new Thief(), home, start, finish, semaphore, HOSTS));
        }
        for (int i = 0; i < HOSTS; i++) {
            service.submit(new HostThread(new Host(home, ITEMS_PER_HOST), start, finish, semaphore));
        }
        service.shutdown();
    }

}
