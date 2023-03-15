package com.example.UserBlogs.controller;

import com.example.UserBlogs.model.Blog;
import com.example.UserBlogs.model.User;
import com.example.UserBlogs.service.userService;
import com.example.UserBlogs.util.validationUtil;

import com.sun.istack.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class userController {
@Autowired
userService userservice;

@PostMapping("/add-user")
   public ResponseEntity<String>addUser(@RequestBody String user){

    if(!validatedUser(user).keySet().isEmpty()){
        return new ResponseEntity<>(validatedUser(user).toString(),HttpStatus.BAD_REQUEST);
    }else{
        User newuser=reauestedUser(user);
        userservice.addUser(newuser);
    }


    return new ResponseEntity<>("new User has been saved", HttpStatus.CREATED);
}

@PostMapping("/login")
public ResponseEntity<String>loginUser(@RequestBody String credentials){
      JSONObject jsondata=new JSONObject(credentials);
     JSONObject validatedjson=isvalidcred(jsondata);
if(validatedjson.keySet().isEmpty()){
    String username= jsondata.getString("username");
    String password= jsondata.getString("password");
   JSONObject userdata= userservice.loginUser(username,password);
    return new ResponseEntity<>(userdata.toString(),HttpStatus.OK);
}
return new ResponseEntity<>("enter credentials correctly",HttpStatus.BAD_REQUEST);

}

    private JSONObject isvalidcred(JSONObject jsondata) {
    JSONObject errorObj=new JSONObject();
    if(!jsondata.has("username")){
errorObj.put("username","parameter is missing");
    }
    if(!jsondata.has("password")){
        errorObj.put("password","parameter is missing");
    }
    return errorObj;
    }

    @GetMapping("get-users")
public List<User> getUsers(){

    return userservice.getUsers();

    }


@PutMapping("/update-user/{id}")
public ResponseEntity<String>updateUser(@PathVariable Integer id,@RequestBody String updateduser){
    if(validatedUser(updateduser).keySet().isEmpty()){
        userservice.updateUser(id,reauestedUser(updateduser));
    }else{
        return new ResponseEntity<>(validatedUser(updateduser).toString(),HttpStatus.OK);
    }


    return new ResponseEntity<>("userupdated with id"+id,HttpStatus.OK);
}

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String>deleteUser(@PathVariable Integer id){
    userservice.deleteUser(id);
    return new ResponseEntity<>("user dleted with id"+id,HttpStatus.OK);
    }

    public User reauestedUser(String user) {
    JSONObject userobj=new JSONObject(user);
    User newuser=new User();
    newuser.setEmail(userobj.getString("email"));
    newuser.setName(userobj.getString("name"));
    newuser.setPassword(userobj.getString("password"));
    newuser.setUsername(userobj.getString("username"));
    newuser.setGender(userobj.getString("gender"));
    return newuser;

    }




    public JSONObject validatedUser(String user) {
    JSONObject errorobj=new JSONObject();
    JSONObject requesteduser=new JSONObject(user);

    if(!requesteduser.has("username")){
        errorobj.put("username","parameter is missing");
    }

    if(requesteduser.has("password")) {
            String password= requesteduser.getString("password");
            if(!validationUtil.isValidPassword(password)){
                errorobj.put("password","enter valid password");
            }
        }else{
                errorobj.put("password","parameter is missing");
        }

    if(!requesteduser.has("name")){
        errorobj.put("name","parameter is missing");
    }
    if(!requesteduser.has("gender")){
        errorobj.put("gender","parameter is missing");
    }
    if(requesteduser.has("email")){
        String mail= requesteduser.getString("email");
        if(!validationUtil.isValidEmail(mail)){
            errorobj.put("email","please enter valid email");
        }

    }else{
        errorobj.put("email","parameter is missing");
    }

    return errorobj;
    }




}
