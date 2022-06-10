package com.micropos.poscounter.service;

import com.micropos.poscounter.model.User;

public interface CounterService {
    User checkUser(String uid, String password);
}
