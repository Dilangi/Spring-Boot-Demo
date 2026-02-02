package com.example.demo.service;

import com.example.demo.dto.ConsumerDTO;
import com.example.demo.entity.Consumer;
import com.example.demo.exception.RecordNotExistException;
import com.example.demo.repository.ConsumerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ConsumerService {
    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ConsumerDTO saveUser(ConsumerDTO consumerDTO){
        consumerRepository.save(modelMapper.map(consumerDTO, Consumer.class));
        return consumerDTO;
    }

    public List<ConsumerDTO> getUsers() {
        List<Consumer> users = consumerRepository.findAll();
        return modelMapper.map(users, new TypeToken<List<ConsumerDTO>>(){}.getType());
    }

    public ConsumerDTO updateUser(Integer id, ConsumerDTO consumerDTO) {
        Consumer consumer = consumerRepository.findById(id)
                .orElseThrow(() ->
                        new RecordNotExistException("Account with id " + id + " not found"));

        consumer.setName(consumerDTO.getName());
        consumer.setAge(consumerDTO.getAge());
        consumer.setGender(consumer.getGender());

        Consumer updatedConsumer = consumerRepository.save(consumer);

        return modelMapper.map(updatedConsumer, ConsumerDTO.class);
    }

    public void deleteUser(Integer id) {
        if (!consumerRepository.existsById(id)) {
            throw new RecordNotExistException("Account with id " + id + " not found");
        }
        consumerRepository.deleteById(id);
    }

    public ConsumerDTO getUserById(int userId) {
        Consumer user = consumerRepository.getUserById(userId);
        if (user == null) {
            throw new RecordNotExistException("User with ID " + userId + " not found.");
        }
        return modelMapper.map(user, ConsumerDTO.class);
    }

    public ConsumerDTO getUserByNameAndAge(String name, int age) {
        Consumer user = consumerRepository.findByNameAndAge(name, age)
                .orElseThrow(() ->
                        new RecordNotExistException(
                        "User not found with name=" + name + " and age=" + age
                        )
                );

        return modelMapper.map(user, ConsumerDTO.class);
    }
}
