package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;


public class JUnitStat extends Stopwatch {

    private static final Logger log = getLogger("result");
    private static StringBuilder strData = new StringBuilder();

    @Override
    protected void finished(long nanos, Description description) {
        String nameTest = String.format("\n%-25s %7d ms", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        strData.append(nameTest);
        log.info(nameTest);

    }

    public static void printStatistic() {
        log.info("\n---------------------------------------------"
                + "\nNAME TEST                    WORKTIME"
                + "\n---------------------------------------------"
                + strData.toString()
                + "\n---------------------------------------------");
    }


}
