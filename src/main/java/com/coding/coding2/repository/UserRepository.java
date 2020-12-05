package com.coding.coding2.repository;/* crated by : prakash.kharche created on : 05/12/20*/


import com.coding.coding2.entity.Device;
import com.coding.coding2.entity.User;

import java.util.Optional;

public interface UserRepository {
    User create(User user);
    Optional<User> get(String userId);
    User update(User user);
    User addDevice(String userId, Device device);
}
