package com.coding.coding2.entity.dto;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.MetricDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricAggregate {
    private MetricDefinition definition;
    private double aggregatedValue;
}
