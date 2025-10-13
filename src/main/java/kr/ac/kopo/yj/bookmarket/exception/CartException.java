package kr.ac.kopo.yj.bookmarket.exception;

public class CartException extends RuntimeException {
    private String cartId;

    public CartException(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }
}
