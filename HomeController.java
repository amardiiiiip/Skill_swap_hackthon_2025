package SkillSwap.skill.controller;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import SkillSwap.skill.model.User;
import SkillSwap.skill.service.UserService;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping({"/", "/index"})
    public String homePage(Model model) {
        model.addAttribute("users", userService.getAllUsers().stream()
            .filter(u -> "Public".equals(u.getProfileVisibility()))
            .collect(Collectors.toList()));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            User loggedInUser = userService.findByEmail(email);
            model.addAttribute("loggedInUser", loggedInUser);
            if ("Public".equals(loggedInUser.getProfileVisibility())) {
                model.addAttribute("showLoggedInUserCard", true);
            }
        } else {
            model.addAttribute("loggedInUser", null);
            model.addAttribute("showLoggedInUserCard", false);
        }
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        return "index";
    }
}