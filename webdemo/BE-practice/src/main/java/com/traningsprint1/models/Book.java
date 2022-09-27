package com.traningsprint1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

/**
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="name", nullable = false)
    @Length(max = 255)
    private String name;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name ="image", columnDefinition = "LONGTEXT")
    private String image;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "delete_flag", columnDefinition = "BIT default 0")
    private Boolean deleteFlag;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "book")
    @JsonBackReference
    private Set<InvoiceDetail> invoiceDetailSet;

}
