package com.micropos.poscounter.rest;

import com.micropos.poscounter.api.LoginApi;
import com.micropos.poscounter.dto.UserDto;
import com.micropos.poscounter.mapper.UserMapper;
import com.micropos.poscounter.model.User;
import com.micropos.poscounter.service.CounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class CounterController implements LoginApi {
    private final UserMapper userMapper;
    private final CounterService counterService;

    public CounterController(UserMapper userMapper, CounterService counterService) {
        this.userMapper = userMapper;
        this.counterService = counterService;
    }

    @Override
    public ResponseEntity<UserDto> login(String uid) {
        User user = counterService.checkUser(uid, null);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
        }
    }
}
