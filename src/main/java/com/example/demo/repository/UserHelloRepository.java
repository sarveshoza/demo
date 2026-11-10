package com.example.demo.repository;

import com.example.demo.entity.UserHelloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHelloRepository extends JpaRepository<UserHelloEntity, Long> {
    // Add custom query methods if needed
    List<UserHelloEntity> findByUserName(String userName);
}