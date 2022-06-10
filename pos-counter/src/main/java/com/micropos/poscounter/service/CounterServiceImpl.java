package com.micropos.poscounter.service;

import com.micropos.poscounter.model.User;
import com.micropos.poscounter.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService{

    @Autowired
    private CounterRepository counterRepository;

    @Override
    public User checkUser(String uid, String password) {
        return counterRepository.getUser(uid);
    }
}
