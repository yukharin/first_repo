
package com.yukharin.hosts_and_thieves.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class Bag implements Iterable<Item> {

    private final static AtomicInteger sumValue = new AtomicInteger();
    private final static AtomicInteger sumWeight = new AtomicInteger();
    private final int maxWeight;
    private final List<Item> items;
    private int currentWeight;


    Bag(final int maxWeight) {
        this.items = new ArrayList<>();
        this.maxWeight = maxWeight;
    }

    public static int getSumValue() {
        return sumValue.intValue();
    }

    public static int getSumWeight() {
        return sumWeight.intValue();
    }

    public void add(final Item item) {
        Objects.requireNonNull(item);
        if (!isEnoughSpace(item)) {
            throw new IllegalArgumentException("Item is too heavy for this bag");
        }
        items.add(item);
        currentWeight += item.getWeight();
        sumValue.addAndGet(item.getValue());
        sumWeight.addAndGet(item.getWeight());
    }

    public void addAll(final List<Item> itemsToSteal) {
        Objects.requireNonNull(itemsToSteal);
        for (Item item : itemsToSteal) {
            if (!isEnoughSpace(item)) {
                throw new IllegalArgumentException("Item is too heavy for this bag");
            }
            items.add(item);
            currentWeight += item.getWeight();
            sumValue.addAndGet(item.getValue());
            sumWeight.addAndGet(item.getWeight());
        }
    }

    public boolean isEnoughSpace(final Item item) {
        Objects.requireNonNull(item);
        return (item.getWeight() + currentWeight <= maxWeight);
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    @Override
    public String toString() {
        return "items " + items.toString() + ", current weight: " + currentWeight;
    }
}