package kr.ac.kopo.yj.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.domain.Cart;
import kr.ac.kopo.yj.bookmarket.domain.CartItem;
import kr.ac.kopo.yj.bookmarket.exception.BookIdException;
import kr.ac.kopo.yj.bookmarket.service.BookService;
import kr.ac.kopo.yj.bookmarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @PutMapping("/{cartId}")
    public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {
        System.out.println("read");
        return cartService.read(cartId);
    }

    @GetMapping
    public String requestCartId(HttpServletRequest request) {
        System.out.println("redirect cart After requestCartId");
        String sessionid = request.getSession(true).getId();
        return "redirect:/cart/"+ sessionid;
    }

    @PostMapping
    public @ResponseBody Cart create(@RequestBody Cart cart ) {
        System.out.println("Cart 생성");
        return  cartService.create(cart);
    }

    @GetMapping( "/{cartId}")
    public String requestCartList(@PathVariable(value = "cartId") String cartId,  Model model) {
        System.out.println("requestCartList ");
        Cart cart = cartService.read(cartId);
        model.addAttribute("cart",cart);
        return "cart";
    }




    @PutMapping("/book/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addCartByNewItem(@PathVariable("bookId") String bookId, HttpServletRequest request) {

        String sessionId = request.getSession(true).getId();


        Cart cart = cartService.read(sessionId);

        if(cart == null)
            cart = cartService.create(new Cart(sessionId));

        Book book = bookService.getBookById(bookId);

        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId));

        cart.addCartItem(new CartItem(book));

        cartService.update(sessionId, cart);

    }

    @DeleteMapping("/book/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCartByItem(@PathVariable("bookId") String bookId, HttpServletRequest request) {


        String sessionId = request.getSession(true).getId();
        Cart cart = cartService.read(sessionId);
        if(cart== null)
            cart = cartService.create(new Cart(sessionId));
        Book book = bookService.getBookById(bookId);
        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId));

        cart.removeCartItem(new CartItem(book));

        cartService.update(sessionId, cart);

    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCartList(@PathVariable("cartId") String cartId) {

        cartService.delete(cartId);
    }

}
