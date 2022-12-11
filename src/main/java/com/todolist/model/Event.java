package com.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_event_registration",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> eventUsers = new HashSet<>();

    @Transient
    private Set<String> eventUsernames = new HashSet<>();
    public Event() {
    }

    public Event(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<User> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(Set<User> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public void registerUserToEvent(User user) {
        this.eventUsers.add(user);
    }

    public void removeUserFromEvent(User user){
        this.eventUsers.remove(user);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getEventUsernames() {
        return eventUsernames;
    }

    public void setEventUsernames(Set<String> eventUsernames) {
        this.eventUsernames = eventUsernames;
    }
}
