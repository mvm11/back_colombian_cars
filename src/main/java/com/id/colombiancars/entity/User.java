package com.id.colombiancars.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="ownerName")
    private String ownerName;

    @Column(name="ownerLastname")
    private String ownerLastname;

    @Column(name="ownerDni")
    private String ownerDni;

    public User(String ownerName, String ownerLastname, String ownerDni) {
        this.ownerName = ownerName;
        this.ownerLastname = ownerLastname;
        this.ownerDni = ownerDni;
    }
}
