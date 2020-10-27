package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<User, List<Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(MealsUtil.USER, new CopyOnWriteArrayList<>());
        repository.put(MealsUtil.ADMIN, new CopyOnWriteArrayList<>());
        log.info("Start filling user's Meals repository");
        MealsUtil.meals.stream().forEach(m -> save(m, MealsUtil.USER));
        log.info("Filling user's Meals repository");
        MealsUtil.adminMeals.forEach(a -> save(a, MealsUtil.ADMIN));
        log.info("Filling admin's Meals repository");
    }

    @Override
    public Meal save(Meal meal, User user) {

        if (user.isNew()) {
            repository.put(user, new CopyOnWriteArrayList<>());
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(user).add(meal);
            log.info("Add  Meal id={} to repository", meal.getId());
            return meal;
        }
        // handle case: update, but not present in storage
        Meal mealOld = getMealById(meal.getId(), user);
        repository.get(user).set(repository.get(user).indexOf(mealOld), meal);
        log.info("Update meals with id = {} for User id = {}", meal.getId(), user.getId());
        return meal;
    }

    @Override
    public boolean delete(int id, User user) {
        log.info("Delete Meal with id={} for User id={}", id, user.getId());
        return repository.get(user).remove(getMealById(id, user));
    }

    @Override
    public Meal get(int id, User user) {
        log.info("Get meal with id = {} for User id={}", id, user.getId());
        return repository.get(user).get(repository.get(user).indexOf(getMealById(id, user)));
    }

    @Override
    public List<Meal> getAll(User user) {
        log.info("Return sorted list meals for User id = {}", user.getId());
        return repository.get(user).stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private Meal getMealById(int id, User user) {
        return repository.get(user).stream().filter(m -> m.getId().equals(id)).findAny().orElse(null);
    }
}


