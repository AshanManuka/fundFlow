package com.fundflow.fundFlowApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nic;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private double income;

    @Column(nullable = false)
    private double creditScore;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private User user;
}
