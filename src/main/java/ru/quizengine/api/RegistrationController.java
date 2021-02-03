package ru.quizengine.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.quizengine.user.User;
import ru.quizengine.user.UserService;

import javax.validation.Valid;

@RestController @RequestMapping("/api/register")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@Valid @RequestBody User user) {
        userService.addUser(user);
    }
}
