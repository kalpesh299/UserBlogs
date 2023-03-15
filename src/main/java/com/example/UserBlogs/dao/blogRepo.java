package com.example.UserBlogs.dao;

import com.example.UserBlogs.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface blogRepo extends JpaRepository<Blog,Integer> {
//@Query(value = "select * from tbl_post where post= :blog",nativeQuery = true)
//public List<Blog> findByblogPost(String blog);
}
