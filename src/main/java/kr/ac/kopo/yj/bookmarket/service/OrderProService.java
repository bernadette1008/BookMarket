package kr.ac.kopo.yj.bookmarket.service;

import kr.ac.kopo.yj.bookmarket.repository.OrderProRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.ac.kopo.yj.bookmarket.domain.Order;

@Service
public class OrderProService {
    @Autowired
    private OrderProRepository orderProRepository;

    public void save(Order order){
        orderProRepository.save(order);
    }
}
