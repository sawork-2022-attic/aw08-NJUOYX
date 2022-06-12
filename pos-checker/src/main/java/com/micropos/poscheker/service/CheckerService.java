package com.micropos.poscheker.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

@Service
public class CheckerService {
    public void getUid(LinkedMultiValueMap uid){
        System.out.println("info: "+uid.toString());
    }


}
