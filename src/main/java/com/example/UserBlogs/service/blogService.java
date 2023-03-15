package com.example.UserBlogs.service;

import com.example.UserBlogs.dao.blogRepo;
import com.example.UserBlogs.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class blogService {
    @Autowired
    blogRepo blogrepo;
    public void addBlog(Blog newblog) {
        blogrepo.save(newblog);
    }

    public List<Blog> findAllblogs() {
        return blogrepo.findAll();
    }

    public void updateBlog(Blog newblog,Integer blogid) {
        Blog oldblog=blogrepo.findById(blogid).get();
        oldblog.setPost(newblog.getPost());
        oldblog.setUserid(newblog.getUserid());
    }

    public void deletBlog(Integer id) {
        blogrepo.deleteById(id);
    }
}
