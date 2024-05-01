package com.fincom.fintech.service;

import com.fincom.fintech.model.MarketOrder;
import com.fincom.fintech.model.OrderType;

public interface OrderService {

    void cancelMarketOrder(Long id);
    void createMarketOrder(MarketOrder order);
}
