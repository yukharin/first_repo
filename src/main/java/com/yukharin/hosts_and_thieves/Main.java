package com.yukharin.hosts_and_thieves;

import com.yukharin.hosts_and_thieves.entities.Home;
import com.yukharin.hosts_and_thieves.entities.Host;
import com.yukharin.hosts_and_thieves.entities.Thief;
import com.yukharin.hosts_and_thieves.threads.HostThread;
import com.yukharin.hosts_and_thieves.threads.ThiefThread;
import com.yukharin.hosts_and_thieves.utils.Utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int HOSTS = 100;
    private static final int THIEVES = 100;
    private static final int ITEMS_PER_HOST = 100;
    private static final int TOTAL_THREADS = HOSTS + THIEVES;
    private static final Semaphore semaphore = new Semaphore(HOSTS);
    private static final Home home = new Home();
    private static final Runnable task = () ->
            Utils.printInfo(home);
    private static final CyclicBarrier barrier = new CyclicBarrier(TOTAL_THREADS, task);
    private static final AtomicInteger threadsCounter = new AtomicInteger();


    public static void main(String[] args) throws InterruptedException {
        long startingTime = System.currentTimeMillis();
        System.out.println("Starting time: " + startingTime);
        ExecutorService service = Executors.newFixedThreadPool(TOTAL_THREADS);
        for (int i = 0; i < HOSTS; i++) {
            service.submit(new HostThread(new Host(ITEMS_PER_HOST), home, semaphore, barrier, threadsCounter));
        }
        for (int i = 0; i < THIEVES; i++) {
            service.submit(new ThiefThread(new Thief(), home, semaphore, HOSTS, barrier, threadsCounter));
        }
        service.shutdown();
        service.awaitTermination(3, TimeUnit.MINUTES);
        long endingTime = System.currentTimeMillis();
        System.out.println("Ending time: " + endingTime);
        System.out.println("Perfomance: " + (endingTime - startingTime) + " millis");
    }

}
