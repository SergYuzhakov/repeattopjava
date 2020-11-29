package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest  extends UserServiceTest {

    @Test
    public void getWithMeal(){
        User user = service.getWithMeal(ADMIN_ID);
        User expected = admin;
        USER_MATCHER.assertMatch(user, expected);

    }

    @Test
    public void getWithMealNotFound(){
        assertThrows(NotFoundException.class, () -> service.getWithMeal(USER_WITHOUT_MEALS_ID)) ;
    }

}
