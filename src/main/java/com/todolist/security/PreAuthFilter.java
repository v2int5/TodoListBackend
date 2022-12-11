package com.todolist.security;

import com.todolist.service.EventInvitationService;
import com.todolist.service.TaskService;
import com.todolist.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class PreAuthFilter {

    final
    UserService userService;
    final
    EventInvitationService invitationService;
    final
    TaskService taskService;

    public PreAuthFilter(UserService userService, EventInvitationService invitationService, TaskService taskService) {
        this.userService = userService;
        this.invitationService = invitationService;
        this.taskService = taskService;
    }


    public boolean checkIfUserInEvent(Authentication authentication, String eventId) {
        if(eventId.matches("[0-9]+")){
            return userService.isUserInEvent(authentication.getName(), Long.valueOf(eventId));
        }
        return false;
    }


    public boolean checkIfUserIsInvited(Authentication authentication, String invitationId) {
        if (invitationId.matches("[0-9]+")) {
            return this.invitationService.isInvitationValid(authentication.getName(), Long.valueOf(invitationId));
        }
        return false;
    }

    public boolean checkIfUserInTask(String taskId){
        if(taskId.matches("[0-9]+")){
            return this.taskService.isUserTaskCreatorOrAssignedToTask(Long.valueOf(taskId));
        }
        return false;
    }
}