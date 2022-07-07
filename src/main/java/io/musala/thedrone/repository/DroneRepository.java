    package io.musala.thedrone.repository;

    import io.musala.thedrone.model.Drone;

    import java.util.List;


    public interface DroneRepository extends JpaRepository<Drone,Long> {
        List<Drone> getAvailableDrones();
    }
