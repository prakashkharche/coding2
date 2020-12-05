package com.coding.coding2.entity;/* crated by : prakash.kharche created on : 05/12/20*/

import lombok.Data;

@Data
public class Device {
    private String id;
    private String deviceId;
    private String name;
    private User user;

    public Device(String name) {
        this.name = name;
    }
}
