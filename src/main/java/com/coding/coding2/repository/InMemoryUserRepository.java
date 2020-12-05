package com.coding.coding2.repository;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.Device;
import com.coding.coding2.entity.User;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> userMap = Maps.newHashMap();

    @Override
    public User create(User user) {
        user.setId(UUID.randomUUID().toString());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> get(String userId) {
        if (!userMap.containsKey(userId)) {
            return Optional.empty();
        }
        return Optional.of(userMap.get(userId));
    }

    @Override
    public User update(User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new RuntimeException("User not found");
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User addDevice(String userId, Device device) {
        if (!userMap.containsKey(userId)) {
            throw new RuntimeException("User id not found");
        }
        device.setId(UUID.randomUUID().toString());
        userMap.get(userId).getDevices().add(device);
        return userMap.get(userId);
    }
}
