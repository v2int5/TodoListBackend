package com.todolist.repository;

import com.todolist.model.EventModel;
import com.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

    UserModel findUserByEmail(String email);

    UserModel findUserById(Long id);


    boolean existsUserByEventsIdAndUsername(Long eventId, String username);

    List<UserModel> findUsersByEvents(EventModel event);
    @Query("SELECT u.username FROM UserModel u WHERE :eventId NOT IN (SELECT eu.id FROM u.events eu) AND :eventId NOT IN (SELECT ei.eventId FROM u.eventInvitations ei)")
    List<String> findUsersNotInEvent(@Param("eventId") Long eventId);

    @Query("Select u FROM UserModel u Where u.username IN :usernames")
    Set<UserModel> findAllUsersByUsernameSet(Set<String> usernames);
}
