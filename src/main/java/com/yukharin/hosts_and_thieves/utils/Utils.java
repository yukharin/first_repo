package com.yukharin.hosts_and_thieves.utils;

import com.yukharin.hosts_and_thieves.entities.Bag;
import com.yukharin.hosts_and_thieves.entities.Home;
import com.yukharin.hosts_and_thieves.entities.Host;
import com.yukharin.hosts_and_thieves.entities.Item;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static Logger logger = Logger.getLogger(Utils.class);
    private static final int LOWER_WEIGHT = 5;
    private static final int UPPER_WEIGHT = 30;
    private static final int LOWER_VALUE = 15;
    private static final int UPPER_VALUE = 150;

    private Utils() {
    }

    public static List<Item> generateItems(int count) {
        List<Item> items = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int weight = LOWER_WEIGHT + random.nextInt(UPPER_WEIGHT);
            int value = LOWER_VALUE + random.nextInt(UPPER_VALUE);
            items.add(new Item(weight, value));
        }
        return items;
    }

    public static void printInfo(Home home) {
        BasicConfigurator.configure();
        home.updateAggregationValues();
        logger.info("Sum value hosts: " + Host.getSumValue());
        logger.info("Sum weight hosts: " + Host.getSumWeight());
        logger.info("Sum value home: " + home.getSumValue());
        logger.info("Sum weight home: " + home.getSumWeight());
        logger.info("Sum value thieves: " + Bag.getSumValue());
        logger.info("Sum weight thieves: " + Bag.getSumWeight());
    }
}