package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from User where id=?1", nativeQuery = true)
    User getUserById(int userId);

    @Query(value = "select * from User where name=?1 and age=?2", nativeQuery = true)
    User getUserByNameAndAge(String name,int age);
}
