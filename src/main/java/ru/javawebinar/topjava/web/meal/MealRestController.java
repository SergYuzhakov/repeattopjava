package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        log.info("Save meal id = {}, user = {}", meal.getId(), SecurityUtil.authUserId());
        return service.create(meal, SecurityUtil.getAuthUser());
    }

    public void delete(int id) {
        log.info("Delete meal id = {}, user = {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.getAuthUser());
    }

    public Meal get(int id) {
        log.info("Get meal id = {}, user = {}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.getAuthUser());
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(SecurityUtil.getAuthUser()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("Get all filtered meals by Date start = {},  end = {} and Time start = {}, end = {}", startDate, endDate, startTime, endTime);
        return MealsUtil.getFilteredTos
                (service.getFilteredAll(SecurityUtil.getAuthUser(), startDate, endDate),
                        SecurityUtil.authUserCaloriesPerDay(),
                        startTime,
                        endTime);
    }
}