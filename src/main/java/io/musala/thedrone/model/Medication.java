package io.musala.thedrone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medication_id")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$")
    private String name;

    private Double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;

    private String imageUrl;

    @ManyToMany(mappedBy = "medications", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Drone> drones = new ArrayList<>();


    public void addDrone(Drone drone) {
        getDrones().add(drone);
    }

    public void removeDrone(Drone drone) {
        getDrones().remove(drone);
    }

}
