package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class JUnitStat extends Stopwatch {

    private static final Logger log = LoggerFactory.getLogger(JUnitStat.class);
    private static final Map<String, Long> statisticMap = new HashMap<>();

    @Override
    protected void finished(long nanos, Description description) {
        String nameTest = description.getMethodName();
        long executeTime = TimeUnit.NANOSECONDS.toMillis(nanos);
        statisticMap.put(nameTest, executeTime);
        log.info("Name test - {}, Work time - {} ms", nameTest, executeTime);
    }

    public static void printStatistic() {
        StringBuilder strData = new StringBuilder();
        strData.append(String.format("%n%-25s%-15s%n", "NAME TEST", "WORKTIME"));
        for (Map.Entry<String, Long> map : statisticMap.entrySet()) {
            strData.append(String.format("%-25s%-5s ms %n", map.getKey(), map.getValue()));
        }
        log.info(strData.toString());
    }


}
