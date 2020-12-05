package com.coding.coding2.entity;/* crated by : prakash.kharche created on : 05/12/20*/

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Metric {
    private String id;
    private MetricDefinition metricDefinition;
    private double value;
    private User user;
    private Device device;
    private LocalDateTime timestamp;

    public Metric(MetricDefinition metricDefinition, double value, User user, Device device, LocalDateTime timestamp) {
        this.metricDefinition = metricDefinition;
        this.value = value;
        this.user = user;
        this.device = device;
    }
}
