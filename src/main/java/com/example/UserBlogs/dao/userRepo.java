package com.example.UserBlogs.dao;


import com.example.UserBlogs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface userRepo extends JpaRepository<User,Integer> {
    @Query(value = "select * from tbl_user where username= :username",nativeQuery = true)
    public User findByUserName(String username);
}
