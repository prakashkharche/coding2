package com.coding.coding2;/* crated by : prakash.kharche created on : 05/12/20*/

import com.coding.coding2.entity.*;
import com.coding.coding2.entity.dto.MetricAggregate;
import com.coding.coding2.repository.MetricDefinitionRepository;
import com.coding.coding2.service.MetricService;
import com.coding.coding2.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MetricsTests {

    @Autowired
    private MetricDefinitionRepository metricDefinitionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MetricService metricService;

    @Test
    void testMetricCreate() {
        MetricDefinition metricDefinition = createMetricDefinitions();
        User user = createUser();
        Device iWatch = createDevice(user, new Device("iwatch"));
        Device iPhone = createDevice(user, new Device("iphone"));

        Metric addedMetric = metricService.add(new Metric(metricDefinition, 70, User.forId(user.getId()), iWatch, null));
        Assertions.assertNotNull(addedMetric.getId());
    }


    @Test
    void testGetMetrics() {
        MetricDefinition metricDefinition = createMetricDefinitions();
        User user = createUser();
        User user2 = createUser();

        Device iWatch = createDevice(user, new Device("iwatch"));
        Device iPhone = createDevice(user, new Device("iphone"));

        metricService.add(new Metric(metricDefinition, 70, User.forId(user.getId()), iWatch, null));
        metricService.add(new Metric(metricDefinition, 50, User.forId(user.getId()), iPhone, null));
        metricService.add(new Metric(metricDefinition, 80, User.forId(user.getId()), iPhone, null));

        metricService.add(new Metric(metricDefinition, 70, User.forId(user2.getId()), iWatch, null));
        metricService.add(new Metric(metricDefinition, 50, User.forId(user2.getId()), iPhone, null));

        List<Metric> metrics = metricService.getForTimePeriod(user.getId(), metricDefinition.getMetricName(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        Assertions.assertEquals(3, metrics.size());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==70).count());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==50).count());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==80).count());

        List<Metric> metrics2 = metricService.getForTimePeriod(user2.getId(), metricDefinition.getMetricName(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        Assertions.assertEquals(2, metrics2.size());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==70).count());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==50).count());
    }


    @Test
    void testGetMetricsMultiple() {
        MetricDefinition bpmDefiniton = createMetricDefinition("heart-rate", "bpm");
        MetricDefinition heightDefinition = createMetricDefinition("height", "cms");
        User user = createUser();

        Device iWatch = createDevice(user, new Device("iwatch"));
        Device iPhone = createDevice(user, new Device("iphone"));

        metricService.add(new Metric(bpmDefiniton, 70, User.forId(user.getId()), iWatch, null));
        metricService.add(new Metric(bpmDefiniton, 50, User.forId(user.getId()), iPhone, null));
        metricService.add(new Metric(heightDefinition, 80, User.forId(user.getId()), iPhone, null));


        List<Metric> metrics = metricService.getForTimePeriod(user.getId(), bpmDefiniton.getMetricName(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        Assertions.assertEquals(2, metrics.size());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==70).count());
        Assertions.assertEquals(1, metrics.stream().filter(metric -> metric.getValue() ==50).count());

        List<Metric> metrics2 = metricService.getForTimePeriod(user.getId(), heightDefinition.getMetricName(), LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        Assertions.assertEquals(1, metrics2.size());
        Assertions.assertEquals(1, metrics2.stream().filter(metric -> metric.getValue() ==80).count());
    }

    @Test
    void testGetAggregate() {
        MetricDefinition bpmDefiniton = createMetricDefinition("heart-rate", "bpm");
        MetricDefinition heightDefinition = createMetricDefinition("height", "cms");
        User user = createUser();
        Device iWatch = createDevice(user, new Device("iwatch"));
        Device iPhone = createDevice(user, new Device("iphone"));

        metricService.add(new Metric(bpmDefiniton, 70, User.forId(user.getId()), iWatch, null));
        metricService.add(new Metric(bpmDefiniton, 50, User.forId(user.getId()), iPhone, null));
        metricService.add(new Metric(heightDefinition, 80, User.forId(user.getId()), iPhone, null));
        metricService.add(new Metric(heightDefinition, 50, User.forId(user.getId()), iPhone, null));

        MetricAggregate aggregate = metricService.getAggregate(user.getId(), AggregationType.AVERAGE, "heart-rate", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        Assertions.assertEquals(60, aggregate.getAggregatedValue(), 0.1d);

        MetricAggregate heightAggregate = metricService.getAggregate(user.getId(), AggregationType.AVERAGE, "height", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        Assertions.assertEquals(65, heightAggregate.getAggregatedValue(), 0.1d);
    }


    @Test
    void testGetDaily() {
        MetricDefinition metricDefinition = createMetricDefinitions();
        User user = createUser();
        Device iWatch = createDevice(user, new Device("iwatch"));
        Device iPhone = createDevice(user, new Device("iphone"));

        metricService.add(new Metric(metricDefinition, 70, User.forId(user.getId()), iWatch, null));
        metricService.add(new Metric(metricDefinition, 50, User.forId(user.getId()), iPhone, null));
        metricService.add(new Metric(metricDefinition, 80, User.forId(user.getId()), iPhone, null));

        MetricAggregate aggregate = metricService.getAggregate(user.getId(), AggregationType.AVERAGE, "heart-rate", LocalDateTime.now().with(LocalTime.of(0,0)), LocalDateTime.now().with(LocalTime.of(23,59)));
        Assertions.assertEquals(66.67, aggregate.getAggregatedValue(), 0.1d);
    }

    @Test
    void testGetWeekly() {
        MetricDefinition metricDefinition = createMetricDefinitions();
        User user = createUser();
        Device iWatch = createDevice(user, new Device("iwatch"));
        Device iPhone = createDevice(user, new Device("iphone"));

        metricService.add(new Metric(metricDefinition, 70, User.forId(user.getId()), iWatch, LocalDateTime.now().minusDays(2)));
        metricService.add(new Metric(metricDefinition, 50, User.forId(user.getId()), iPhone, LocalDateTime.now().minusDays(3)));
        metricService.add(new Metric(metricDefinition, 80, User.forId(user.getId()), iPhone, LocalDateTime.now().minusDays(4)));
        metricService.add(new Metric(metricDefinition, 90, User.forId(user.getId()), iPhone, LocalDateTime.now().minusDays(4)));
        metricService.add(new Metric(metricDefinition, 100, User.forId(user.getId()), iPhone, LocalDateTime.now().minusDays(4)));
        metricService.add(new Metric(metricDefinition, 110, User.forId(user.getId()), iPhone, LocalDateTime.now().minusDays(4)));


        MetricAggregate aggregate = metricService.getAggregate(user.getId(), AggregationType.AVERAGE, "heart-rate", LocalDateTime.now().minusDays(7), LocalDateTime.now());
        Assertions.assertEquals(83.33, aggregate.getAggregatedValue(), 0.1d);
    }

    private MetricDefinition createMetricDefinitions() {
        Optional<MetricDefinition> metricDefinition = metricDefinitionRepository.get("heart-rate");
        if (metricDefinition.isPresent()) {
            return metricDefinition.get();
        }
        return metricDefinitionRepository.create(new MetricDefinition("heart-rate", "bpm"));
    }

    private MetricDefinition createMetricDefinition(String name, String unit) {
        Optional<MetricDefinition> metricDefinition = metricDefinitionRepository.get(name);
        if (metricDefinition.isPresent()) {
            return metricDefinition.get();
        }
        return metricDefinitionRepository.create(new MetricDefinition(name, unit));
    }
    private User createUser() {
        User user = User.forName("prakash");
        User createdUser = userService.create(user);

        userService.addDevice(createdUser.getId(), new Device("iwatch"));
        return createdUser;
    }

    private Device createDevice(User user, Device device) {
        userService.addDevice(user.getId(), device);
        return device;
    }
}
