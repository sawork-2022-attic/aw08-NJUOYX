package com.micropos.carts.rest;

import com.micropos.carts.api.CartApi;
import com.micropos.carts.dto.ItemDto;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import com.micropos.poscounter.dto.UserDto;
import com.micropos.poscounter.mapper.UserMapper;
import com.micropos.poscounter.mapper.UserMapperImpl;
import com.micropos.poscounter.model.User;
import com.micropos.posorder.mapper.OrderMapper;
import com.micropos.posorder.mapper.OrderMapperImpl;
import com.micropos.posorder.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class CartController implements CartApi {
    @Autowired
    private final ItemMapper itemMapper;
    @Autowired
    private final CartService cartService;
    private UserMapper userMapper = new UserMapperImpl();
    private OrderMapper orderMapper = new OrderMapperImpl();

    @LoadBalanced
    private RestTemplate restTemplate(){return new RestTemplate();}

    private Boolean checkUser(String uid){
        String url = "http://localhost:6001/api/login/"+uid;
        return restTemplate().getForObject(url, UserDto.class) != null;
    }

    private User getUser(String uid){
        String url = "http://localhost:6001/api/login/"+uid;
        return userMapper.toUser(restTemplate().getForObject(url, UserDto.class));
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
        System.out.println(uid);
        User user = getUser(uid);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String url = "http://localhost:6001/api/order";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity postRequest = new HttpEntity(orderMapper.toOrderDto(new Order<>(user, cartService.getItems())), headers);
        Boolean res = restTemplate().postForObject(
                        url, postRequest,Boolean.class);
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
