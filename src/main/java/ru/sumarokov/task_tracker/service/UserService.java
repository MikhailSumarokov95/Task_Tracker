package ru.sumarokov.task_tracker.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sumarokov.task_tracker.entity.TaskGroup;
import ru.sumarokov.task_tracker.entity.User;
import ru.sumarokov.task_tracker.repository.TaskGroupRepository;
import ru.sumarokov.task_tracker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, TaskGroupRepository taskGroupRepository ,BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: " + username));
    }

    public void createNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        taskGroupRepository.save(new TaskGroup("default", newUser,true));
    }
}
