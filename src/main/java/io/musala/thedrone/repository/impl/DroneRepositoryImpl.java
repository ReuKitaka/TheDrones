package io.musala.thedrone.repository.impl;

import io.musala.thedrone.model.Drone;
import io.musala.thedrone.repository.DroneRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class DroneRepositoryImpl extends JpaRepositoryImplementation<Drone, Long> implements DroneRepository{

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
                .filter(drone -> drone.getBatteryCapacity() >= 25)
                .collect(Collectors.toList());
    }
}
