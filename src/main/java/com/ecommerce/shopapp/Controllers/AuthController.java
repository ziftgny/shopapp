package com.ecommerce.shopapp.Controllers;


import com.ecommerce.shopapp.Business.Abstracts.UserService;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;
import com.ecommerce.shopapp.Core.Utils.Results.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Kayıt formunu göster
    @GetMapping("/register-user-page")
    public String RegisterUserPage(Model model) {
        model.addAttribute("user", new UserRequestDTO());
        return "registerUserPage";
    }

    // ✅ Kayıt işlemi
    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute("user") UserRequestDTO dto, Model model) {
        Result result = userService.createUser(dto);

        if (!result.isSuccess()) {
            model.addAttribute("error", result.getMessage());
            return "registerUserPage";
        }

        return "redirect:/auth/login-user-page";
    }

    // ✅ Login formunu göster
    @GetMapping("/login-user-page")
    public String showLoginPage() {
        return "loginUserPage";
    }
}
