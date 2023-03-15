package com.example.UserBlogs.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Table(name="tbl_user")
@Data

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private Integer id;


    @Column(name="username")
    private String username;
    @Column(name="password")
     private String password;

    @Column(name="name")
    private String name;
    @Column(name="gender")
    private String gender;


    @Column(name="mail")
    private String email;

}
