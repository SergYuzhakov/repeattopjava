package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;
    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_1, USER_ID);
        assertMatch(meal, MealTestData.MEAL_USER_1);
    }

    @Test
    public void getForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID);
        assertNull(repository.get(MEAL_ID_1, USER_ID));
    }

    @Test
    public void deleteForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updateMeal = MealTestData.getUpdated();
        service.update(updateMeal, USER_ID);
        assertMatch(service.get(MEAL_ID_1, USER_ID), updateMeal);
    }

    @Test
    public void updateForeignMeals() {
        Meal updateMeal = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updateMeal, ADMIN_ID));

    }

    @Test
    public void create() {
        Meal createdMeal = service.create(MealTestData.getNew(), USER_ID);
        Integer createdId = createdMeal.getId();
        Meal expectedMeal = MealTestData.getNew();
        expectedMeal.setId(createdId);
        assertMatch(createdMeal, expectedMeal);
        assertMatch(repository.get(createdMeal.getId(), USER_ID), expectedMeal);
    }


    @Test
    public void duplicateDateTimeCreate() {
        Meal meal = new Meal(null,
                LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак2",
                600);
        assertThrows(DataAccessException.class, () -> service.create(meal, USER_ID));

    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                USER_ID), MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), MEAL_ADMIN_2, MEAL_ADMIN_1);
    }


}