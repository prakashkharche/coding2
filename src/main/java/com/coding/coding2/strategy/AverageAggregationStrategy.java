package com.coding.coding2.strategy;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.Metric;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AVERAGE")
public class AverageAggregationStrategy implements AggregationStrategy{
    @Override
    public Double aggregate(List<Metric> metrics) {
        if (metrics.isEmpty()) {
            return 0d;
        }
        double sum = 0;
        for (Metric metric : metrics) {
            sum += metric.getValue();
        }
        return sum/metrics.size();
    }
}
