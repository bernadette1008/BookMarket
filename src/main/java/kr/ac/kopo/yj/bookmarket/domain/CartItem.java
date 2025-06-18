package kr.ac.kopo.yj.bookmarket.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class CartItem {
    private Book book;
    private int quantity;
    private BigDecimal totalPrice;

    public CartItem(Book book) {
        this.book = book;
        this.quantity = 1;
        this.totalPrice = book.getUnitPrice();
    }

    public void setBook(Book book){
        this.book = book;
        updateTotalPrice();
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        this.totalPrice = book.getUnitPrice().multiply(new BigDecimal(quantity));
    }
}
