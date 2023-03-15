package com.example.UserBlogs.service;

import com.example.UserBlogs.dao.blogRepo;
import com.example.UserBlogs.dao.userRepo;
import com.example.UserBlogs.model.Blog;
import com.example.UserBlogs.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class userService {
@Autowired
userRepo userrepo;
@Autowired
blogRepo blogrepo;
    public void addUser(User user) {
       userrepo.save(user);
    }
    public List<User> getUsers(){
        return userrepo.findAll();
    }


    public void updateUser(Integer id,User reauestedUser) {
        User olduser=userrepo.findById(id).get();
        olduser.setName(reauestedUser.getName());
        olduser.setUsername(reauestedUser.getUsername());
        olduser.setGender(reauestedUser.getGender());
        olduser.setEmail(reauestedUser.getEmail());
        olduser.setPassword(reauestedUser.getPassword());
    }

    public void deleteUser(Integer id) {
        userrepo.deleteById(id);
    }

    public JSONObject loginUser(String username, String password) {
        User user=userrepo.findByUserName(username);
        JSONObject jsonObject = new JSONObject();
        if(user.getUsername().equals(username) && user.getPassword().equals(password)){
            jsonObject=createJsonobj(user);
        }else{
            jsonObject.put("error","credentials are not proper");
        }
     return jsonObject;
    }

    private JSONObject createJsonobj(User user) {

        JSONObject json=new JSONObject();
      json.put("userid",user.getId());
      json.put("username",user.getUsername());
      json.put("password",user.getPassword());
      json.put("name",user.getName());
      json.put("gender",user.getGender());
      json.put("email",user.getEmail());
      return json;
    }



}
