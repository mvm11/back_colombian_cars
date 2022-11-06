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

    @Column(name="ownerName")
    private String ownerName;

    @Column(name="ownerLastname")
    private String ownerLastname;

    @Column(name="ownerDni")
    private String ownerDni;

    @Column(name="licensePlate")
    private String licensePlate;

    @Column(name="type")
    private String type;

    @Column(name="isParking")
    private boolean isParking;


    @OneToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    @JsonBackReference
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();


    public Vehicle(EntryRequest entryRequest) {
        this.ownerName = entryRequest.getOwnerName();
        this.ownerLastname = entryRequest.getOwnerLastname();
        this.ownerDni = entryRequest.getOwnerDni();
        this.licensePlate = entryRequest.getLicensePlate();
        this.type = entryRequest.getType();
    }
}
