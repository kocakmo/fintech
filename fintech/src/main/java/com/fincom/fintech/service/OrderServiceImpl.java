package com.fincom.fintech.service;

import com.fincom.fintech.jpa.OrderRepository;
import com.fincom.fintech.model.MarketOrder;
import com.fincom.fintech.model.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public void cancelMarketOrder(Long id) {

    }

    @Override
    public void createMarketOrder(MarketOrder order) {
        orderRepository.save(order);
    }
}
