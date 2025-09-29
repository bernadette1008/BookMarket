package kr.ac.kopo.yj.bookmarket.repository;

import kr.ac.kopo.yj.bookmarket.domain.Order;

public interface OrderRepository {
    // 주문 목록 저장
    Long saveOrder(Order order);
}
