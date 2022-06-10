package com.micropos.carts.rest;

import com.micropos.carts.api.CartApi;
import com.micropos.carts.dto.ItemDto;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import com.micropos.poscounter.dto.UserDto;
import com.micropos.poscounter.mapper.UserMapper;
import com.micropos.poscounter.model.User;
import com.micropos.posorder.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class CartController implements CartApi {
    private final ItemMapper itemMapper;
    private final CartService cartService;
    private UserMapper userMapper;
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @LoadBalanced
    private RestTemplate restTemplate(){return new RestTemplate();}

    private Boolean checkUser(String uid){
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("checkUser");
        String url = "http://localhost:6001/api/login/"+uid;
        return circuitBreaker.run(()-> restTemplate().getForObject(url, UserDto.class) != null);
    }

    private User getUser(String uid){
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("checkUser");
        String url = "http://localhost:6001/api/login/"+uid;
        return circuitBreaker.run(()-> userMapper.toUser(restTemplate().getForObject(url, UserDto.class)));
    }

    public CartController(ItemMapper itemMapper, CartService cartService) {
        this.itemMapper = itemMapper;
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<List<ItemDto>> showCart(String uid){
        if(!checkUser(uid)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<ItemDto> items = new ArrayList<>(this.itemMapper.toItemsDto(this.cartService.getItems()));
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> addItemToCart(String productId, String uid){
        if(!checkUser(uid)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Boolean res = this.cartService.addItem(productId);
        if(res == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> checkout(String uid) {
        User user = getUser(uid);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("sendOrder");
        String url = "http://localhost:6001/api/order";
        Boolean res = circuitBreaker.run(()->
                restTemplate().postForObject(
                        url, new Order<>(user, cartService.getItems()),Boolean.class));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getTotal(String uid){
        if(!checkUser(uid)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Double res = this.cartService.getTotal();
        if(res == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
