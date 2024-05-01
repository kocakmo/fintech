package com.fincom.fintech.jpa;

import com.fincom.fintech.model.MarketOrder;
import com.fincom.fintech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<MarketOrder,Long>{

    List<MarketOrder> findOrdersByUser(User user);
    List<MarketOrder> findByUserAndOrderStatus(User user,Integer orderStatus);

    MarketOrder findByOrderId(Long id);
}
