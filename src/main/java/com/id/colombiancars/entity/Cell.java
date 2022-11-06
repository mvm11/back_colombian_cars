package com.id.colombiancars.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cells", uniqueConstraints = {@UniqueConstraint(columnNames = {"cellName"})})
@Data
@NoArgsConstructor
public class Cell {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cellName")
    private String cellName;

    @Column(name = "isOccupied")
    private boolean isOccupied;
}
