package com.id.colombiancars.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "cells")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cell {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cellName", unique = true)
    private String cellName;

    @Column(name = "isOccupied")
    private boolean isOccupied;

    @Column(name = "hasVehicle")
    private boolean hasVehicle;
}
