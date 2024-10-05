package web.course.shopback.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import web.course.shopback.models.User;
import web.course.shopback.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
