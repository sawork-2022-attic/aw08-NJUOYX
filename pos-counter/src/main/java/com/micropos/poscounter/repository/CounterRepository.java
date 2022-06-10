package com.micropos.poscounter.repository;

import com.micropos.poscounter.model.User;

public interface CounterRepository {
    User getUser(String uid);
}
