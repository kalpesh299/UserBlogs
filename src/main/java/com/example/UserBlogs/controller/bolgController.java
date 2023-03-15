package com.example.UserBlogs.controller;

import com.example.UserBlogs.dao.blogRepo;
import com.example.UserBlogs.dao.userRepo;
import com.example.UserBlogs.model.Blog;
import com.example.UserBlogs.model.User;
import com.example.UserBlogs.service.blogService;
import com.example.UserBlogs.service.userService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/blog")
public class bolgController {

    @Autowired
    blogService blogservice;

    @Autowired
    userRepo userrepo;

    @Autowired
    blogRepo blogrepo;

    @PostMapping("add-blog")
    public ResponseEntity<String>addPost(@RequestBody String blog){
        JSONObject validatedpost=isvalidPost(blog);
        if(!validatedpost.keySet().isEmpty()){
            return new ResponseEntity<>(validatedpost.toString(), HttpStatus.BAD_REQUEST);
        }
        Blog newblog=setBlog(blog);
        blogservice.addBlog(newblog);

       return new  ResponseEntity<>("blog posted successfully",HttpStatus.CREATED);
    }

    @GetMapping("/get-allblogs")
    public List<Blog> findBlogs(){
           return blogservice.findAllblogs();
    }

    @PutMapping("/update-blog")
    public ResponseEntity<String>updateBlog(@RequestParam Integer userid,@RequestParam Integer blogid,@RequestBody String blog){
        JSONObject isuservalid=uservalidation(userid,blogid);
        if(!isuservalid.keySet().isEmpty()){return new ResponseEntity<>(isuservalid.toString(),HttpStatus.BAD_REQUEST);}
        JSONObject validatedpost=isvalidPost(blog);
        if(!validatedpost.keySet().isEmpty()){
            return new ResponseEntity<>(validatedpost.toString(), HttpStatus.BAD_REQUEST);
        }

        Blog newblog=setBlog(blog);
        blogservice.updateBlog(newblog,blogid);
        return new ResponseEntity<>("post updated successfully",HttpStatus.OK);
    }

    @DeleteMapping("delete-blog/{id}")
    private ResponseEntity<String>deleteBlog(@PathVariable Integer id){
        Blog blog=blogrepo.findById(id).get();
        if(blog!=null){
            blogservice.deletBlog(id);
            return new ResponseEntity<>("post deleted successfully with id= "+id,HttpStatus.OK);
        }
        return new ResponseEntity<>("user not exist",HttpStatus.BAD_REQUEST);
    }

    private JSONObject uservalidation(Integer userid,Integer blogid) {
        JSONObject errorObj=new JSONObject();
        User user=userrepo.findById(userid).get();
        Blog blog=blogrepo.findById(blogid).get();
        User blogowner=blog.getUserid();
        if(user.getId()!=blogowner.getId()){
            errorObj.put("user","User is not authorized");
        }
        return errorObj;
    }

    private Blog setBlog(String blog) {
        Blog newblog=new Blog();
        JSONObject json=new JSONObject(blog);
        newblog.setPost(json.getString("post"));
        User user=userrepo.findById(json.getInt("userid")).get();
        newblog.setUserid(user);
        return newblog;
    }

    private JSONObject isvalidPost(String blog) {
        JSONObject json=new JSONObject(blog);
        JSONObject errorObj=new JSONObject();
        if(!json.has("post")){
            errorObj.put("post","missing");
        }
        if(json.has("userid")){
            User user=userrepo.findById(json.getInt("userid")).get();
            if(user==null){
                errorObj.put("user","user not exist");
            }
        }else{
            errorObj.put("user","parameter is missing");
        }
        return errorObj;
    }
}
