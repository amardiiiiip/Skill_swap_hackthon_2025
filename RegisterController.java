package SkillSwap.skill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import SkillSwap.skill.model.User;
import SkillSwap.skill.service.UserService;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/api/users/register")
    public String register(@ModelAttribute User user, Model model) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            model.addAttribute("error", "Email already registered. Please use a different email.");
            return "register";
        }
        // Set default values to avoid null issues
        if (user.getProfileVisibility() == null) user.setProfileVisibility("Public");
        if (user.getAvailability() == null) user.setAvailability("Available");
        userService.registerUser(user);
        return "redirect:/login?success";
    }
}