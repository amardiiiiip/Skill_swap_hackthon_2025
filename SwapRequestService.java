package SkillSwap.skill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import SkillSwap.skill.model.SwapRequest;
import SkillSwap.skill.model.User;
import SkillSwap.skill.repository.SwapRequestRepository;

@Service
public class SwapRequestService {
    private final SwapRequestRepository swapRequestRepository;

    public SwapRequestService(SwapRequestRepository swapRequestRepository) {
        this.swapRequestRepository = swapRequestRepository;
    }

    public SwapRequest createSwapRequest(User requester, User receiver, String offeredSkill, String wantedSkill) {
        SwapRequest swapRequest = new SwapRequest();
        swapRequest.setRequester(requester);
        swapRequest.setReceiver(receiver);
        swapRequest.setOfferedSkill(offeredSkill);
        swapRequest.setWantedSkill(wantedSkill);
        swapRequest.setStatus("Pending");
        swapRequest.setRating(0.0);
        return swapRequestRepository.save(swapRequest);
    }

    public List<SwapRequest> getSwapRequestsByReceiver(User receiver) {
        return swapRequestRepository.findByReceiver(receiver);
    }

    public List<SwapRequest> getAcceptedRequestsByRequester(User requester) {
        return swapRequestRepository.findByRequesterAndStatus(requester, "Accepted");
    }

    public List<SwapRequest> getPendingRequestsByRequester(User requester) {
        return swapRequestRepository.findByRequesterAndStatus(requester, "Pending");
    }

    public List<SwapRequest> getAllSwapRequests() {
        return swapRequestRepository.findAll();
    }

    public SwapRequest updateSwapRequestStatus(Long id, String status) {
        SwapRequest swapRequest = swapRequestRepository.findById(id).orElse(null);
        if (swapRequest != null) {
            swapRequest.setStatus(status);
            return swapRequestRepository.save(swapRequest);
        }
        return null;
    }
}