package io.musala.thedrone.service;

import io.musala.thedrone.model.Drone;
import io.musala.thedrone.model.Medication;

import java.util.List;

public interface DroneService {
    Drone registerDrone(Drone drone);

    void loadDroneMedication(Long droneId, List<Medication> medications) ;

    List<Medication> getDroneMedications(Long droneId);

    List<Medication> getDroneMedications(Drone drone);

    List<Drone> getAvailableDrones();

    Double getBatteryLevel(Long droneId);

    Double getBatteryLevel(Drone drone);

    void clearDatabase();
}
