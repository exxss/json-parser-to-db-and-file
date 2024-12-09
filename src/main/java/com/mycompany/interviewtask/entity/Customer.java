package com.mycompany.interviewtask.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "customers",schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("all")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "number_of_purchases", nullable = false)
    private Integer numberOfPurchases;

    @Column(name = "number_of_returns", nullable = false)
    private Integer numberOfReturns;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "rating", nullable = false)
    private Integer rating;
}
