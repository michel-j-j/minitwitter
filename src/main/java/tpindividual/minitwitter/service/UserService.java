package tpindividual.minitwitter.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpindividual.minitwitter.entity.dto.UserResponse;
import tpindividual.minitwitter.entity.model.User;
import tpindividual.minitwitter.respository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(String userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User(userName);
        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getUserName());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUserName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponse(user.getId(), user.getUserName());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }
}