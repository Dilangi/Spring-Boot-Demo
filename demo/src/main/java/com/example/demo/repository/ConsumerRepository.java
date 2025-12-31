package com.example.demo.repository;

import com.example.demo.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
    @Query(value = "select * from Consumers where id=?1", nativeQuery = true)
    Consumer getUserById(int userId);

    Optional<Consumer> findByNameAndAge(String name, int age);
}
