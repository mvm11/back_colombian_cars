package com.id.colombiancars.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="assignedCell")
    private String assignedCell;

    @Column(name="entryHour")
    private Date entryHour;

    @Column(name="departureHour")
    private Date departureHour;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

}
