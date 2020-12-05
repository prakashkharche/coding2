package com.coding.coding2.service;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.Device;
import com.coding.coding2.entity.User;

public interface UserService {
    User create(User user);
    User login(String userId);
    User addDevice(String userId,Device device);
}
