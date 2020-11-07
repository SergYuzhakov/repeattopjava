package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_1 = START_SEQ + 2;
    public static final int MEAL_ID_2 = START_SEQ + 3;
    public static final int MEAL_ID_3 = START_SEQ + 4;
    public static final int MEAL_ID_4 = START_SEQ + 5;
    public static final int MEAL_ID_5 = START_SEQ + 6;
    public static final int MEAL_ID_6 = START_SEQ + 7;
    public static final int MEAL_ID_7 = START_SEQ + 8;
    public static final int MEAL_ID_ADMIN_1 = START_SEQ + 9;
    public static final int MEAL_ID_ADMIN_2 = START_SEQ + 10;

    public static final Meal MEAL_USER_1 = new Meal(MEAL_ID_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_USER_2 = new Meal(MEAL_ID_2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_USER_3 = new Meal(MEAL_ID_3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_USER_4 = new Meal(MEAL_ID_4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_USER_5 = new Meal(MEAL_ID_5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_USER_6 = new Meal(MEAL_ID_6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_USER_7 = new Meal(MEAL_ID_7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal MEAL_ADMIN_1 = new Meal(MEAL_ID_ADMIN_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак Админа", 500);
    public static final Meal MEAL_ADMIN_2 = new Meal(MEAL_ID_ADMIN_2, LocalDateTime.of(2020, Month.JANUARY, 30, 14, 0), "Ланч Админа", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 16, 0), "Полдник", 150);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 10), "Завтрак2", 600);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
