package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<User, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        log.info("Start filling user's Meals repository");
        MealsUtil.meals.forEach(m -> save(m, MealsUtil.USER));
        log.info("Filling user's Meals repository");
        MealsUtil.adminMeals.forEach(a -> save(a, MealsUtil.ADMIN));
        log.info("Filling admin's Meals repository");
    }

    @Override
    public Meal save(Meal meal, User user) {

        Map<Integer, Meal> meals = repository.computeIfAbsent(user, key -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            log.info("Add  Meal id={} to repository", meal.getId());
            return meal;
        }
        // handle case: update, but not present in storage
        log.info("Update meals with id = {} for User id = {}", meal.getId(), user.getId());
        return meals.computeIfPresent(meal.getId(), (id, old) -> meal);
    }

    @Override
    public boolean delete(int id, User user) {
        log.info("Delete Meal with id={} for User id={}", id, user.getId());
        Map<Integer, Meal> meals = repository.get(user);
        return meals == null ? null : meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, User user) {
        log.info("Get meal with id = {} for User id={}", id, user.getId());
        Map<Integer, Meal> meals = repository.get(user);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(User user) {
        log.info("Return list all meals for User id = {}", user.getId());
        return getFilteredMealByUser(user, meal -> true);
    }

    @Override
    public List<Meal> getFilteredMealByUser(User user, Predicate<Meal> filter) {
        log.info("Return sorted list meals for User id = {}", user.getId());
        Map<Integer, Meal> meals = repository.get(user);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() : meals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }


}


