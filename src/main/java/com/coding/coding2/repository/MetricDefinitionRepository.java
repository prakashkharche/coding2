package com.coding.coding2.repository;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.MetricDefinition;

import java.util.Optional;

public interface MetricDefinitionRepository {
    Optional<MetricDefinition> get(String metricName);
    MetricDefinition create(MetricDefinition metricDefinition);
}
