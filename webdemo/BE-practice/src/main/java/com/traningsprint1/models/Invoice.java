package com.traningsprint1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Entity
@Table(name = "invoice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "create_date", nullable = false, length = 50)
    private String createDate;
    @Column(name = "total_money")
    private Double totalMooney;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private Set<InvoiceDetail> invoiceDetailSet;




}
