package SkillSwap.skill.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import SkillSwap.skill.model.User;
import SkillSwap.skill.service.SwapRequestService;
import SkillSwap.skill.service.UserService;

@Controller
public class SwapRequestAcceptController {
    @Autowired
    private UserService userService;
    @Autowired
    private SwapRequestService swapRequestService;

    @GetMapping("/swap-request-accept")
    public String swapRequestAcceptPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            User loggedInUser = userService.findByEmail(email);
            model.addAttribute("loggedInUser", loggedInUser);
            List<SkillSwap.skill.model.SwapRequest> receivedRequests = swapRequestService.getSwapRequestsByReceiver(loggedInUser);
            List<SkillSwap.skill.model.SwapRequest> sentPendingRequests = swapRequestService.getPendingRequestsByRequester(loggedInUser);
            List<SkillSwap.skill.model.SwapRequest> sentAcceptedRequests = swapRequestService.getAcceptedRequestsByRequester(loggedInUser);
            List<SkillSwap.skill.model.SwapRequest> allRequests = new ArrayList<>();
            allRequests.addAll(receivedRequests);
            allRequests.addAll(sentPendingRequests);
            allRequests.addAll(sentAcceptedRequests);
            model.addAttribute("requests", allRequests);
        } else {
            model.addAttribute("loggedInUser", null);
            model.addAttribute("requests", new ArrayList<>());
        }
        return "swap-request-accept";
    }

    @PostMapping("/api/swap/accept")
    public String acceptSwapRequest(@RequestParam Long requestId) {
        swapRequestService.updateSwapRequestStatus(requestId, "Accepted");
        return "redirect:/swap-request-accept";
    }

    @PostMapping("/api/swap/reject")
    public String rejectSwapRequest(@RequestParam Long requestId) {
        swapRequestService.updateSwapRequestStatus(requestId, "Rejected");
        return "redirect:/swap-request-accept";
    }
}