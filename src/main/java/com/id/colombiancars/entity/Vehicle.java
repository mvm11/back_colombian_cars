package com.id.colombiancars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.id.colombiancars.request.EntryRequest;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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


    @OneToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @JsonBackReference
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();


    public Vehicle(EntryRequest entryRequest) {
        this.licensePlate = entryRequest.getLicensePlate();
        this.type = entryRequest.getType();
    }


}
