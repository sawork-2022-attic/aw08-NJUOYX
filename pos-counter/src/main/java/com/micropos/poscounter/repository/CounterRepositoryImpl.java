package com.micropos.poscounter.repository;

import com.micropos.poscounter.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class CounterRepositoryImpl implements CounterRepository{
    private final List<String> users = Arrays.asList("Donald", "Theresa", "Vladimir", "Angela", "Emmanuel", "Shinzō", "Jacinda", "Kim");
    private final List<String> passwords = Arrays.asList("123", "abc", "123","abc","123","abc","123","abc");

    @Override
    public User getUser(String uid) {
        for(int i = 0;i<users.size();++i){
            if(users.get(i).equalsIgnoreCase(uid)){
                return new User(uid, passwords.get(i));
            }
        }
        return null;
    }
}
