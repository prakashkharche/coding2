package com.coding.coding2.repository;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.MetricDefinition;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryMetricDefRepository implements MetricDefinitionRepository {
    private Map<String, MetricDefinition> metricDefinitionMap = Maps.newHashMap();

    @Override
    public Optional<MetricDefinition> get(String metricName) {
        if (!metricDefinitionMap.containsKey(metricName)) {
            return Optional.empty();
        }
        return Optional.of(metricDefinitionMap.get(metricName));
    }

    @Override
    public MetricDefinition create(MetricDefinition metricDefinition) {
        if (metricDefinitionMap.containsKey(metricDefinition.getMetricName())) {
            throw new RuntimeException("Metric already present with this name");
        }
        metricDefinitionMap.put(metricDefinition.getMetricName(), metricDefinition);
        return metricDefinition;
    }
}
