package SkillSwap.skill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import SkillSwap.skill.model.User;
import SkillSwap.skill.service.UserService;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            User loggedInUser = userService.findByEmail(email);
            model.addAttribute("loggedInUser", loggedInUser);
        } else {
            return "redirect:/login";
        }
        return "profile";
    }

    @PostMapping("/api/users/update")
    public String updateProfile(@ModelAttribute User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setLocation(user.getLocation());
                existingUser.setSkillsOffered(user.getSkillsOffered());
                existingUser.setSkillsWanted(user.getSkillsWanted());
                existingUser.setAvailability(user.getAvailability());
                existingUser.setProfileVisibility(user.getProfileVisibility());
                userService.updateUser(existingUser);
                model.addAttribute("loggedInUser", existingUser); // Refresh loggedInUser with updated data
            }
        }
        return "profile"; // Stay on profile page to show updated card
    }
}