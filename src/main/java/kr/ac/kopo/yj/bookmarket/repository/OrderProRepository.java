package kr.ac.kopo.yj.bookmarket.repository;

import kr.ac.kopo.yj.bookmarket.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProRepository extends JpaRepository<Order, Long> {
}
