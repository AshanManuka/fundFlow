package com.fundflow.fundFlowApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    private Date createdDate;

    private Date updateDate;


    @OneToOne
    @JoinColumn(name = "id")
    private User user;
}
