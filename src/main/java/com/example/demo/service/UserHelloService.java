package com.example.demo.service;

import com.example.demo.entity.UserHelloEntity;
import com.example.demo.repository.UserHelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserHelloService {

    private final UserHelloRepository userHelloRepository;

    @Autowired
    public UserHelloService(UserHelloRepository userHelloRepository) {
        this.userHelloRepository = userHelloRepository;
    }

    public List<UserHelloEntity> findAll() {
        return userHelloRepository.findAll();
    }

    public Optional<UserHelloEntity> findById(Long id) {
        return userHelloRepository.findById(id);
    }

    public UserHelloEntity save(UserHelloEntity userHelloEntity) {
        return userHelloRepository.save(userHelloEntity);
    }

    public void deleteById(Long id) {
        userHelloRepository.deleteById(id);
    }

    public Optional<UserHelloEntity> findByUserName(String userName) {
        return userHelloRepository.findByUserName(userName).stream().findFirst();
    }

}