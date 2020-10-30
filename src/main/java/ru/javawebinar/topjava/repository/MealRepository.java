package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.function.Predicate;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, User user);

    // false if not found
    boolean delete(int id, User user);

    // null if not found
    Meal get(int id, User user);

    List<Meal> getAll(User user);

    List<Meal> getFilteredMealByUser(User user, Predicate<Meal> filter);
}
