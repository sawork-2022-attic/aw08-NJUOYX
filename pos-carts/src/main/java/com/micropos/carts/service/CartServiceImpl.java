package com.micropos.carts.service;

import com.micropos.carts.dto.ProductDto;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.CartRepository;
import com.micropos.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ItemMapper itemMapper;
    private CircuitBreakerFactory circuitBreakerFactory;

    public CartServiceImpl(@Autowired CartRepository cartRepository,
                           @Autowired ItemMapper itemMapper,@Autowired CircuitBreakerFactory circuitBreakerFactory){
        this.cartRepository = cartRepository;
        this.itemMapper = itemMapper;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @LoadBalanced
    private RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Override
    public List<Item> getItems() {
        return this.cartRepository.getItems();
    }

    @Override
    public Boolean addItem(String productId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("getProductBreaker");
        String url = "http://localhost:6001/api/products/" + productId;
        return circuitBreaker.run(()->{
            ProductDto productDto = restTemplate().getForObject(url, ProductDto.class);
            Product product = itemMapper.toProduct(productDto);
            return this.cartRepository.addItem(new Item(product, 1));
        },throwable -> false);
    }

    @Override
    public Double getTotal() {
        return this.cartRepository.getTotal();
    }
}
