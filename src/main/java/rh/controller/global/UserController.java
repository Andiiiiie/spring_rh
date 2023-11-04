package rh.controller.global;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rh.model.global.User;
import rh.service.global.UserService;

@Getter
@Setter
@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        return "global/user/login";
    }
}
