package com.id.colombiancars.entity;

import com.id.colombiancars.request.EntryRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Data
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

    @OneToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;


    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;


    public Vehicle(EntryRequest entryRequest) {
        this.ownerName = entryRequest.getOwnerName();
        this.ownerLastname = entryRequest.getOwnerLastname();
        this.ownerDni = entryRequest.getOwnerDni();
        this.licensePlate = entryRequest.getLicensePlate();
        this.type = entryRequest.getType();
    }
}
