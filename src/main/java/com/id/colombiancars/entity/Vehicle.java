package com.id.colombiancars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.id.colombiancars.request.VehicleRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="licensePlate")
    private String licensePlate;
    @Column(name="type")
    private String type;

    @Column(name="isParking")
    private boolean isParking;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();


    public Vehicle(VehicleRequest vehicleRequest) {
        this.licensePlate = vehicleRequest.getLicensePlate();
        this.type = vehicleRequest.getType();
    }


}
