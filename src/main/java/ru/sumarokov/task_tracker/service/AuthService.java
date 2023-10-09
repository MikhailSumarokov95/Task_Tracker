package ru.sumarokov.task_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.sumarokov.task_tracker.entity.User;
import ru.sumarokov.task_tracker.exception.EntityNotFoundException;
import ru.sumarokov.task_tracker.repository.UserRepository;

@Component
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userRepository.findById(user.getId())
                .orElseThrow(EntityNotFoundException::new);
    }
}
