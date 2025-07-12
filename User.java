package SkillSwap.skill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String location;
    private String profilePhoto;
    private String availability;
    private String profileVisibility;
    private String skillsOffered;
    private String skillsWanted;
    private double rating;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
    public String getProfileVisibility() { return profileVisibility; }
    public void setProfileVisibility(String profileVisibility) { this.profileVisibility = profileVisibility; }
    public String getSkillsOffered() { return skillsOffered; }
    public void setSkillsOffered(String skillsOffered) { this.skillsOffered = skillsOffered; }
    public String getSkillsWanted() { return skillsWanted; }
    public void setSkillsWanted(String skillsWanted) { this.skillsWanted = skillsWanted; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}