package com.coding.coding2.repository;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.AggregationType;
import com.coding.coding2.entity.Metric;
import com.coding.coding2.entity.dto.MetricAggregate;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository {
    Metric add(Metric metric);

    List<Metric> getForTimePeriod(String userId, String metricName, LocalDateTime startDate, LocalDateTime endDate);

    MetricAggregate getAggregate(String userId, AggregationType aggregationType, String metricName, LocalDateTime startDate, LocalDateTime endDate);
}
