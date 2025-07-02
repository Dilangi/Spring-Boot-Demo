package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.RecordNotExistException;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO saveUser(UserDTO userDTO){
        userRepository.save(modelMapper.map(userDTO, User.class));
        return userDTO;
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
    }

    public UserDTO updateUser(UserDTO userDTO) {
        userRepository.save(modelMapper.map(userDTO, User.class));
        return userDTO;
    }

    public Boolean deleteUser(UserDTO userDTO) {
        userRepository.delete(modelMapper.map(userDTO, User.class));
        return true;
    }

    public UserDTO getUserById(int userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RecordNotExistException("User with ID " + userId + " not found.");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public int getUserByNameAndAge(String name, int age) {
        User user = userRepository.getUserByNameAndAge(name, age);
        return modelMapper.map(user, UserDTO.class).getId();
    }
}
