package io.musala.thedrone.schedulers;

import io.musala.thedrone.model.AuditLog;
import io.musala.thedrone.model.State;
import io.musala.thedrone.repository.AuditLogRepository;
import io.musala.thedrone.repository.DroneRepository;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Stateless
public class AuditLogScheduler {
    @Inject
    private AuditLogRepository auditLogRepository;

    @Inject
    private DroneRepository droneRepository;

    @Schedule(minute = "*/1", hour = "*", persistent = false)
    @Transactional
    private void log() {
        var all = droneRepository.findAll();
        all.forEach(drone -> {
            var auditLog = new AuditLog();
            auditLog.setAsAt(LocalDateTime.now());
            auditLog.setDrone(drone);
            auditLog.setBatteryPercentageLevel(drone.getBatteryCapacity());
            auditLogRepository.create(auditLog);
        });
        all.forEach(drone -> {
            if (State.LOADING.equals(drone.getState())) {
                drone.setBatteryCapacity(drone.getBatteryCapacity() - 3);
            } else if (State.LOADED.equals(drone.getState())) {
                drone.setBatteryCapacity(drone.getBatteryCapacity() - 4);
            } else if (State.DELIVERING.equals(drone.getState())) {
                drone.setBatteryCapacity(drone.getBatteryCapacity() - 5);
            } else if (State.RETURNING.equals(drone.getState())) {
                drone.setBatteryCapacity(drone.getBatteryCapacity() - 2);
            }
            droneRepository.edit(drone);
        });

    }

}
