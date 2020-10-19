package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;
import java.util.List;

public interface Repository {
    Collection getAll();
    Meal getById(int id);
    void delete(int id);
    void update(Meal meal);

}
