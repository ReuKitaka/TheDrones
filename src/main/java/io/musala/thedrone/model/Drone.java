package io.musala.thedrone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "drone_id")
    private Long id;


    @NotNull
    @Column(name = "serialNumber", unique = true)
    @Size(max = 100)
    private String serialNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Model model;

    @NotNull
    @Max(value = 500)
    @Min(value = 1)
    private Double weightLimit;

    @NotNull
    @Max(value = 100)
    @Min(value = 0)
    private Double batteryCapacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "drone_medication_load",
            joinColumns = @JoinColumn(name = "drone_id", referencedColumnName = "drone_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "medication_id")

    )
    @JsonIgnore
    private List<Medication> medications=new ArrayList<>();;


    public void addMedication(Medication medication) {
        getMedications().add(medication);
        medication.getDrones().add(this);
    }

    public void removeMedication(Medication medication) {
        getMedications().remove(medication);
        medication.getDrones().remove(this);
    }

}
