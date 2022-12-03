package com.id.colombiancars.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="lastname")
    private String lastname;

    @Column(name="dni", unique = true)
    private String dni;

    @Column(name="isSubscribed")
    private boolean isSubscribed;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cell_id")
    private Cell cell;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices;

}
