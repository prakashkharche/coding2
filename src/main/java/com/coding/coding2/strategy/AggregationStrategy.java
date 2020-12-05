package com.coding.coding2.strategy;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.Metric;

import java.util.List;

public interface AggregationStrategy {
    Double aggregate(List<Metric> metrics);
}
