package com.coding.coding2.entity;/* crated by : prakash.kharche created on : 05/12/20*/

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private String id;
    private String name;
    private List<Device> devices = Lists.newArrayList();
    private List<Metric> metrics;
    private boolean isLoggedIn = false;

    private User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static User forId(String id) {
        return new User(id, null);
    }

    public static User forName(String name) {
        return new User(null, name);
    }
}
