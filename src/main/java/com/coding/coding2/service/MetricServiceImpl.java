package com.coding.coding2.service;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.AggregationType;
import com.coding.coding2.entity.Metric;
import com.coding.coding2.entity.User;
import com.coding.coding2.entity.dto.MetricAggregate;
import com.coding.coding2.repository.MetricRepository;
import com.coding.coding2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MetricServiceImpl implements MetricService {
    private MetricRepository metricRepository;
    private UserRepository userRepository;

    @Autowired
    public MetricServiceImpl(MetricRepository metricRepository, UserRepository userRepository) {
        this.metricRepository = metricRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Metric add(Metric metric) {
        Optional<User> userOptional = userRepository.get(metric.getUser().getId());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        if (metric.getTimestamp() == null) {
            metric.setTimestamp(LocalDateTime.now());
        }
        return metricRepository.add(metric);
    }

    @Override
    public List<Metric> getForTimePeriod(String userId, String metricName, LocalDateTime startDate, LocalDateTime endDate) {
        return metricRepository.getForTimePeriod(userId, metricName, startDate, endDate);
    }

    @Override
    public MetricAggregate getAggregate(String userId, AggregationType aggregationType, String metricName, LocalDateTime startDate, LocalDateTime endDate) {
        return metricRepository.getAggregate(userId, aggregationType,metricName, startDate, endDate);
    }
}
