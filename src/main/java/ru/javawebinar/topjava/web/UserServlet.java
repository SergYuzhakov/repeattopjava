package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = new InMemoryUserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Change User");
        User user = getUser(request);
        log.info("User change to {}", user);
        SecurityUtil.setAuthUser(user);
        //response.sendRedirect("index.html");
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    private User getUser(HttpServletRequest request) {
        String paramUser = Objects.requireNonNull(request.getParameter("user"));
        log.info("Get user from input form {}", paramUser);
        return userRepository.get(Integer.parseInt(paramUser));
    }


}
