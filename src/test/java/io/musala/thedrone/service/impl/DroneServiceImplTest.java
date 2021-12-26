package io.musala.thedrone.service.impl;


import io.musala.thedrone.JaxRsConfiguration;
import io.musala.thedrone.model.Drone;
import io.musala.thedrone.model.Medication;
import io.musala.thedrone.model.Model;
import io.musala.thedrone.model.State;
import io.musala.thedrone.service.DroneService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RunWith(Arquillian.class)
public class DroneServiceImplTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        var war= ShrinkWrap.create(WebArchive.class).
                addPackages(true, JaxRsConfiguration.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }
    @Inject
    DroneService droneService;

    @InSequence(1)
    public void injectedService(){
        Assert.assertNotNull(droneService);
    }

    @org.junit.Test
    @InSequence(2)
    public void registerDrone() {
        var drone = new Drone();
        drone.setSerialNumber("DRONETEST1");
        drone.setBatteryCapacity(100.0);
        drone.setModel(Model.randomModel());
        drone.setState(State.IDLE);
        drone.setWeightLimit(450.0);
        var drone1 = droneService.registerDrone(drone);
        Assert.assertNotNull(drone1.getId());
    }

    @org.junit.Test
    @InSequence(3)
    public void getAvailableDrones() {
        Assert.assertEquals(6,droneService.getAvailableDrones().size());
    }

    @org.junit.Test(expected = RuntimeException.class)
    @InSequence(4)
    public void loadDroneMedicationWithException() {
        var medication = new Medication();
        medication.setCode("FACE_123");
        medication.setName("FaceMask");
        medication.setWeight(500.0);
        medication.setImageUrl("some url");
        var drone = droneService.getAvailableDrones().get(5);
        droneService.loadDroneMedication(drone.getId(), List.of(medication));
    }
    @org.junit.Test()
    @InSequence(5)
    public void loadDroneMedicationWithSuccess() {
        var medication = new Medication();
        medication.setCode("FACE_123");
        medication.setName("FaceMask");
        medication.setWeight(400.0);
        medication.setImageUrl("some url");
        var drone = droneService.getAvailableDrones().get(5);
        Assert.assertNotNull(drone);
        var medications = new ArrayList<Medication>();
        medications.add(medication);
        droneService.loadDroneMedication(drone.getId(),medications);

    }

    @org.junit.Test
    @InSequence(6)
    public void getDroneMedications() {
        var drone = droneService.getAvailableDrones().get(5);
        var droneMedications = droneService.getDroneMedications(drone.getId());
        Assert.assertNotNull(droneMedications);
        Assert.assertEquals(1,droneService.getDroneMedications(drone.getId()).size());
    }




    @org.junit.Test
    @InSequence(7)
    public void getBatteryLevel() {
        var drone = droneService.getAvailableDrones().get(5);

        Assert.assertEquals(Double.valueOf(100.0), drone.getBatteryCapacity());

    }

}