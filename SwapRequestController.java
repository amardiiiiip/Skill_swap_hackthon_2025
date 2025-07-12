package SkillSwap.skill.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import SkillSwap.skill.model.User;
import SkillSwap.skill.service.SwapRequestService;
import SkillSwap.skill.service.UserService;

@Controller
public class SwapRequestController {
    @Autowired
    private UserService userService;
    @Autowired
    private SwapRequestService swapRequestService;

    @GetMapping("/swap-request/{userId}")
    public String swapRequestPage(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        if (user == null) {
            return "redirect:/index";
        }
        model.addAttribute("user", user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            User loggedInUser = userService.findByEmail(email);
            model.addAttribute("loggedInUser", loggedInUser);
        } else {
            model.addAttribute("loggedInUser", null);
        }
        return "swap-request";
    }

    @PostMapping("/api/swap/request")
    public String createSwapRequest(@RequestParam Long receiverId, @RequestParam String offeredSkill, @RequestParam String wantedSkill) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            User requester = userService.findByEmail(email);
            User receiver = userService.findById(receiverId);
            if (requester != null && receiver != null) {
                swapRequestService.createSwapRequest(requester, receiver, offeredSkill, wantedSkill);
                return "redirect:/swap-request-accept";
            }
        }
        return "redirect:/login";
    }
}