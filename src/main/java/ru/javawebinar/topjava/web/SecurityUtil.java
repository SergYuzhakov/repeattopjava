package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static User authUser = MealsUtil.USER;

    public static int authUserId() {
        return authUser.getId();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static User getAuthUser(){
        return authUser;
    }

    public static User setAuthUser(User user){
              authUser = user;
        return authUser;
    }
}