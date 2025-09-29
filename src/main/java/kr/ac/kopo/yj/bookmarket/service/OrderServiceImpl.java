package kr.ac.kopo.yj.bookmarket.service;

import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.domain.Order;
import kr.ac.kopo.yj.bookmarket.repository.BookRepository;
import kr.ac.kopo.yj.bookmarket.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void confirmOrder(String bookId, long quantity) {
        Book bookById = bookRepository.getBookById(bookId);
        if(bookById.getUnitsInStock() < quantity){
            throw new IllegalArgumentException("해당 도서는 수량이 부족합니다. 구입 가능한 도서의 수량은 : " + bookById.getUnitsInStock() + " 개 입니다");
        }
        bookById.setUnitsInStock(bookById.getUnitsInStock() - quantity);
    }

    @Override
    public Long saveOrder(Order order) {
        Long orderId = orderRepository.saveOrder(order);
        return orderId;
    }
}