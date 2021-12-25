package io.musala.thedrone.repository.impl;

import io.musala.thedrone.model.Drone;
import io.musala.thedrone.repository.DroneRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static io.musala.thedrone.util.Constants.BATTERY_MIN_LEVEL;

public class DroneRepositoryImpl extends JpaRepositoryImplementation<Drone, Long> implements DroneRepository {

    @Inject
    private EntityManager entityManager;

    public DroneRepositoryImpl() {
        super(Drone.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return findAll().stream()
                .filter(drone -> drone.getBatteryCapacity() >= BATTERY_MIN_LEVEL)
                .collect(Collectors.toList());
    }
}
