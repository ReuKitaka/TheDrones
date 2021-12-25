package io.musala.thedrone.db;

import io.musala.thedrone.model.Drone;
import io.musala.thedrone.model.Model;
import io.musala.thedrone.model.State;
import io.musala.thedrone.service.DroneService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class Seeder {
    @Inject
    private DroneService droneService;
    @PostConstruct
    public void seedDatabase(){
        for (int i = 0; i < 5; i++) {
            var drone = new Drone();
            drone.setSerialNumber("D12345"+i);
            drone.setBatteryCapacity(25+15.0*i);
            drone.setModel(Model.randomModel());
            drone.setState(State.IDLE);
            drone.setWeightLimit(100.0*(i+1));
            droneService.registerDrone(drone);
        }
    }
    @PreDestroy
    public  void clearDatabase(){
        droneService.clearDatabase();
    }
}