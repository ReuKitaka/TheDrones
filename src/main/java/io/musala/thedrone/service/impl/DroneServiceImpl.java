package io.musala.thedrone.service.impl;

import io.musala.thedrone.model.Drone;
import io.musala.thedrone.model.Medication;
import io.musala.thedrone.model.State;
import io.musala.thedrone.repository.DroneRepository;
import io.musala.thedrone.repository.MedicationRepository;
import io.musala.thedrone.service.DroneService;
import static io.musala.thedrone.util.Constants.*;


import javax.inject.Inject;
import java.util.List;

public class DroneServiceImpl implements DroneService {
    @Inject
    private DroneRepository repository;
    @Inject
    private MedicationRepository medicationRepository;

    @Override
    public Drone registerDrone(Drone drone) {
        return repository.create(drone);
    }

    @Override
    public void loadDroneMedication(Long droneId, List<Medication> medications) throws Exception {
        var optionalDrone = repository.findById(droneId);
        optionalDrone.ifPresentOrElse(drone -> {
                    if (drone.getBatteryCapacity() >= BATTERY_MIN_LEVEL && State.IDLE.equals(drone.getState())) {
                        var totalMedicationWeight = medications.stream().mapToDouble(Medication::getWeight).sum();
                        if (totalMedicationWeight <= drone.getWeightLimit()) {
                            for (Medication medication :
                                    medications) {
                                medication.addDrone(drone);
                                medicationRepository.create(medication);
                                drone.addMedication(medication);
                                drone.setState(State.LOADING);

                            }
                            drone.setState(State.LOADED);
                            repository.edit(drone);
                        } else {
                            throw new RuntimeException("Medication weight exceeds the drone weight limit");
                        }

                    } else {

                        throw new RuntimeException("Drone not available for loading because state is " + drone.getState() + " and battery  level is " + drone.getBatteryCapacity());

                    }

                },
                optionalDrone::orElseThrow
        );


    }

    @Override
    public List<Medication> getDroneMedications(Long droneId) {
        return repository.find(droneId).getMedications();
    }

    @Override
    public List<Medication> getDroneMedications(Drone drone) {
        return getDroneMedications(drone.getId());
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return repository.getAvailableDrones();
    }

    @Override
    public Double getBatteryLevel(Long droneId) {
        return repository.find(droneId).getBatteryCapacity();
    }

    @Override
    public Double getBatteryLevel(Drone drone) {
        return getBatteryLevel(drone.getId());
    }

    @Override
    public void clearDatabase() {
        var all = repository.findAll();
        for (Drone drone :
                all) {
            repository.deleteById(drone.getId());
        }

    }
}
