package com.ecommerce.shopapp.Controllers;
import com.ecommerce.shopapp.Business.Abstracts.UserService;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        return "Home";
    }
}
