package com.id.colombiancars.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cells")
@Data
@NoArgsConstructor
public class Cell {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isOccupied")
    private boolean isOccupied;
}
