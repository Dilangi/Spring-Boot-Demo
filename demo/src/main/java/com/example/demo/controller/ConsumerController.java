package com.example.demo.controller;

import com.example.demo.dto.ConsumerDTO;
import com.example.demo.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/user")
@CrossOrigin
@Slf4j
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/getUsers")
    public List<ConsumerDTO> getUser(){
        return consumerService.getUsers();
    }

    @PostMapping("/addUser")
    public ConsumerDTO addUser(@RequestBody ConsumerDTO consumerDTO){
        return consumerService.saveUser(consumerDTO);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ConsumerDTO> updateUser(@PathVariable Integer id, @RequestBody ConsumerDTO consumerDTO){
        return ResponseEntity.ok(consumerService.updateUser(id, consumerDTO));}

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        consumerService.deleteUser(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @GetMapping("/getUserById")
    public ResponseEntity<ConsumerDTO> getUserById(@RequestParam int id){
        ConsumerDTO user = consumerService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUserByNameAndAge")
    public ResponseEntity<ConsumerDTO> getUserByNameAndAge(@RequestParam String name ,@RequestParam int age){
        return ResponseEntity.ok(
                consumerService.getUserByNameAndAge(name, age)
        );
    }


}
