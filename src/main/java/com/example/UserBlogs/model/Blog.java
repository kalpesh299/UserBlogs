package com.example.UserBlogs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tbl_post")
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="postid")
    private Integer id;
    @Column(name="post")
    private String post;

    @ManyToOne
    @JoinColumn(name="userid")
    private User userid;


}
