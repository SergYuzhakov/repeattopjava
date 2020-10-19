package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final Integer CAL_PER_DAY = 2000;
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        int id;
        Meal meal;
        switch (action == null ? "" : action) {
            case "delete":
                id = getId(request);
                mealDao.delete(id);
                log.info("Delete meal with id = {}", id);
                response.sendRedirect("meals");
                break;
            case "edit":
                id = getId(request);
                meal = mealDao.getById(id);
                request.setAttribute("action", "Edit");
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                log.info("Forward meal with id = {} to update", id);
                break;

            case "add":
                meal = new Meal(LocalDateTime.now(), "", 1000, 0);
                request.setAttribute("action", "Add");
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                log.info("Add meal");


            default:

                List<MealTo> mealTos = MealsUtil.mealToAll(mealDao.getAll(), CAL_PER_DAY);
                request.setAttribute("meals", mealTos);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                log.info("Get Meals and forward to meals.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = request.getParameter("id").isEmpty() ? 0 : Integer.parseInt(request.getParameter("id"));
        int cal = request.getParameter("calories").isEmpty() ? 0 : Integer.parseInt(request.getParameter("calories"));
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        log.info("Get data - {}, {}, {}, {}", localDateTime, description, cal, id);
        Meal meal = new Meal(localDateTime, description, cal, id);
        mealDao.update(meal);
        response.sendRedirect("meals");

    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
