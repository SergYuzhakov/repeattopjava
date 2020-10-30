package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.ValidationUtil.*;


public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, User user) {
        return repository.save(meal, user);
    }

    public void delete(int id, User user) {
        checkNotFoundWithId(repository.delete(id, user), id);
    }

    public Meal get(int id, User user) {
        return checkNotFoundWithId(repository.get(id, user), id);
    }

    public List<Meal> getAll(User user) {
        return repository.getAll(user);
    }

    public <T extends Comparable<? super T>> List<Meal> getFilteredAll(User user, T start, T end){
        Predicate<Meal> predicate = m -> DateTimeUtil.isBetweenHalfOpen((T)m.getDate(), start, end);
        return repository.getFilteredMealByUser(user, predicate);
    }

}