package com.traningsprint1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_image")
    private String customerImage;

    @OneToMany(mappedBy = "customer" )
    private Set<Invoice> invoiceSet;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


}
