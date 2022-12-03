package com.id.colombiancars.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="amount")
    private Double amount;

    @Column(name="state")
    private String state;

    @Column(name="initialDate")
    private LocalDate startDate;

    @Column(name="cutOfDate")
    private LocalDate cutOfDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
