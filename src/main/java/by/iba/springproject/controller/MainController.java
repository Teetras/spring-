package by.iba.springproject.controller;

import by.iba.springproject.form.UserForm;
import by.iba.springproject.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController {
    private static List<User> users = new ArrayList<User>();

    static {
        users.add(new User("Olga", "Pertova"));
        users.add(new User("Ivan", "Ivanov"));
    }
    // Вводится (inject) из application.properties.
    @Value("${welcome.message:Hi,dad}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("index was called");
        return modelAndView;
    }

    @GetMapping(value = {"/users"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        model.addAttribute("users", users);
        return modelAndView;
    }

    @GetMapping(value = {"/add"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("add");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return modelAndView;
    }

    @PostMapping(value = {"/add"})
    public ModelAndView savePerson(Model model, @ModelAttribute("userForm") UserForm userForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();

        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            User newUser = new User(firstName, lastName);
            users.add(newUser);
            model.addAttribute("users", users);
            return modelAndView;
        }

        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("add");
        return modelAndView;
    }

}