package com.coding.coding2.repository;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.AggregationType;
import com.coding.coding2.entity.Metric;
import com.coding.coding2.entity.dto.MetricAggregate;
import com.coding.coding2.strategy.AggregationStrategy;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InMemoryMetricRepository implements MetricRepository {
    private Map<String, Metric> metricMap = Maps.newHashMap();
    private Map<String, AggregationStrategy> aggregationStrategyMap;

    @Autowired
    public InMemoryMetricRepository(Map<String, AggregationStrategy> aggregationStrategyMap) {
        this.aggregationStrategyMap = aggregationStrategyMap;
    }

    @Override
    public Metric add(Metric metric) {
        metric.setId(UUID.randomUUID().toString());
        metricMap.put(metric.getId(), metric);
        return metric;
    }

    @Override
    public List<Metric> getForTimePeriod(String userId, String metricName, LocalDateTime startDate, LocalDateTime endDate) {
        return metricMap.values().stream()
                .filter(metric -> metric.getUser().getId().equalsIgnoreCase(userId))
                .filter(metric -> metric.getTimestamp().isAfter(startDate) && metric.getTimestamp().isBefore(endDate))
                .filter(metric -> metric.getMetricDefinition().getMetricName().equalsIgnoreCase(metricName))
                .collect(Collectors.toList());
    }

    @Override
    public MetricAggregate getAggregate(String userId, AggregationType aggregationType, String metricName, LocalDateTime startDate, LocalDateTime endDate) {
        List<Metric> metricsForUser = this.getForTimePeriod(userId, metricName, startDate, endDate);
        if (metricsForUser.isEmpty()) {
            return new MetricAggregate(null, 0);
        }
        AggregationStrategy aggregationStrategy = aggregationStrategyMap.get(aggregationType.name());
        Double aggregate = aggregationStrategy.aggregate(metricsForUser);
        return new MetricAggregate(metricsForUser.get(0).getMetricDefinition(), aggregate);
    }
}
