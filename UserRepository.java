package SkillSwap.skill.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import SkillSwap.skill.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}