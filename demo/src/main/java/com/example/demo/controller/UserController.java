package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/user")
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public List<UserDTO> getUser(){
        return userService.getUsers();
    }

    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO userDTO){
        return userService.saveUser(userDTO);
    }

    @PutMapping("/updateUser")
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO);}

    @DeleteMapping("/deleteUser")
    public Boolean deleteUser(@RequestBody UserDTO userDTO){
        return userService.deleteUser(userDTO);
    }

    @GetMapping("/getUserById")
    public ResponseEntity<UserDTO> getUserById(@RequestParam int id){
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            log.info("No such user with id"+id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUserByNameAndAge/{userName}/{userAge}")
    public int getUserByNameAndAge(@PathVariable String userName,@PathVariable int userAge){
        return userService.getUserByNameAndAge(userName, userAge);
    }


}
