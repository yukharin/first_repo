package com.yukharin.hosts_and_thieves.threads;

import com.yukharin.hosts_and_thieves.entities.Home;
import com.yukharin.hosts_and_thieves.entities.Host;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class HostThread implements Runnable {

    private Host host;
    private Home home;
    private Semaphore semaphore;
    private CountDownLatch latch;
    private AtomicInteger counter;
    private static Logger logger = LogManager.getLogger(HostThread.class);


    public HostThread(Host host, Home home, Semaphore semaphore, CountDownLatch latch, AtomicInteger counter) {
        this.host = Objects.requireNonNull(host);
        this.semaphore = Objects.requireNonNull(semaphore);
        this.latch = Objects.requireNonNull(latch);
        this.counter = Objects.requireNonNull(counter);
        this.home = Objects.requireNonNull(home);
    }

    @Override
    public void run() {
        try {
            latch.countDown();
            putItems();
        } catch (InterruptedException e) {
            logger.warn(e);
        }
    }

    private void putItem() throws InterruptedException {
        while (host.haveItems()) {
            semaphore.acquire();
            try {
                counter.incrementAndGet();
                host.putItem(home);
                logger.info("Hosts inside home: " + counter.intValue());
                counter.decrementAndGet();
            } finally {
                semaphore.release();
            }
        }
    }

    private void putItems() throws InterruptedException {
        semaphore.acquire();
        try {
            counter.incrementAndGet();
            host.putItems(home);
            logger.info("Hosts inside home: " + counter.intValue());
            counter.decrementAndGet();
        } finally {
            semaphore.release();
        }
    }


}
