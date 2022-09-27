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
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name",nullable = false, length = 50)
    private String userName;
    @Column(name = "encrypt_password", nullable = false, columnDefinition = "LONGTEXT")
    private String encryptPassword;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "is_enabled")
    private Boolean isEnabled;
    @Column(name = "verification_code", length = 255)
    private String verificationCode;

    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "account")
    @JsonBackReference
    private Set<AccountRole> accountRoleSet;


}
