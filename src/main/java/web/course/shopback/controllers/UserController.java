package web.course.shopback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import web.course.shopback.models.User;
import web.course.shopback.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();
        // Дополнительный лог для проверки возвращаемых данных
        System.out.println("Полученные пользователи: " + users);
        return users;
    }
}
