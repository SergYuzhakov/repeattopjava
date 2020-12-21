package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(MealUIController.AJAX_URL)
public class MealUIController extends AbstractMealController {

    static final String AJAX_URL = "/meals";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping(AJAX_URL + "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                       @RequestParam String description,
                       @RequestParam Integer calories) {
        Meal meal = new Meal(localDateTime, description, calories);
        super.create(meal);
    }
}
