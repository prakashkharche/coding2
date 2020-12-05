package com.coding.coding2.service;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.Device;
import com.coding.coding2.entity.User;
import com.coding.coding2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User login(String userId) {
        Optional<User> userTryingToLogin = userRepository.get(userId);
        if (!userTryingToLogin.isPresent()) {
            throw new RuntimeException("User not present");
        }
        User user = userTryingToLogin.get();
        user.setLoggedIn(true);
        userRepository.update(user);
        return user;
    }

    @Override
    public User addDevice(String userId, Device device) {
        Optional<User> userOptional = userRepository.get(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User id not found");
        }
        User user = userOptional.get();

        device.setId(UUID.randomUUID().toString());
        user.getDevices().add(device);
        return userRepository.update(user);
    }
}
