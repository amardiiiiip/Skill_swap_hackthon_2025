package SkillSwap.skill.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SkillSwap.skill.model.SwapRequest;
import SkillSwap.skill.model.User;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long> {
    List<SwapRequest> findByReceiver(User receiver);
    List<SwapRequest> findByRequester(User requester);
    List<SwapRequest> findByRequesterAndStatus(User requester, String status);
}