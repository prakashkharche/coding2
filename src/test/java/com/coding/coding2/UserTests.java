package com.coding.coding2;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.Device;
import com.coding.coding2.entity.User;
import com.coding.coding2.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserService userService;

    @Test
    void testUserCreation() {
        User user = User.forName("prakash");
        User createdUser = userService.create(user);

        Assertions.assertNotNull(createdUser.getId());
    }

    @Test
    void testAddDevice() {
        User user = User.forName("prakash");
        User createdUser = userService.create(user);

        userService.addDevice(createdUser.getId(), new Device("iphone"));
        userService.addDevice(createdUser.getId(), new Device("iphone"));
        User userWithDeviceAdded = userService.addDevice(createdUser.getId(), new Device("samsung"));

        Assertions.assertFalse(userWithDeviceAdded.getDevices().isEmpty());
        Assertions.assertEquals(3, userWithDeviceAdded.getDevices().size());
    }

    @Test
    void testUserLogin() {
        User user = User.forName("prakash");
        User createdUser = userService.create(user);

        User loggedInUser = userService.login(createdUser.getId());
        Assertions.assertTrue(loggedInUser.isLoggedIn());
    }
}
