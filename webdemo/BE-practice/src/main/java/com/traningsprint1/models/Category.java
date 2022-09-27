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
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name", length = 255)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private Set<Book> bookSet;
}
