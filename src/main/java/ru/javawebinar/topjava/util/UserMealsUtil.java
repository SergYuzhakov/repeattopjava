package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCalPerDay = new HashMap<>();
        for (UserMeal usermeal : meals) {
            mapCalPerDay.merge(usermeal.getDate(), usermeal.getCalories(), (oldVal, newVal) -> (oldVal + newVal));
        }

        List<UserMealWithExcess> filterUserMeal = new ArrayList<>();
        for (UserMeal usermeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(usermeal.getTime(), startTime, endTime)) {
                filterUserMeal.add(mealWithExcesses(usermeal, mapCalPerDay.get(usermeal.getDate()) > caloriesPerDay));
            }
        }
        // TODO return filtered list with excess. Implement by cycles
        return filterUserMeal;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //  Map<LocalDate, Integer> mapCalPerDay = new HashMap<>();
        //  meals.stream().forEach(meal -> mapCalPerDay.merge(meal.getDate(), meal.getCalories(), (oldVal, newVal) -> (oldVal + newVal)));
        Map<LocalDate, Integer> mapCalPerDay = meals.stream()
                .collect(
                        // Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories))
                        Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum)
                );

        return meals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> mealWithExcesses(meal, mapCalPerDay.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        // TODO Implement by streams

    }

    private static UserMealWithExcess mealWithExcesses(UserMeal meals, boolean excess) {
        return new UserMealWithExcess(meals.getDateTime(), meals.getDescription(), meals.getCalories(), excess);
    }

}
