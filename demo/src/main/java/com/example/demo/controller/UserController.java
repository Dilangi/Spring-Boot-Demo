package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/v1/user")
@CrossOrigin
public class UserController {
    @GetMapping("/getUser")
    public String getUser(){
        return "I am first user";
    }

    @PostMapping("/addUser")
    public String addUser(){
        return "First User Added";
    }

    @PutMapping("/updateUser")
    public String updateUser(){
        return "First become second";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(){
        return "User Removed!";
    }
}
