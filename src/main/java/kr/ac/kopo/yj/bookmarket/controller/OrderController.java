package kr.ac.kopo.yj.bookmarket.controller;

import kr.ac.kopo.yj.bookmarket.domain.*;
import kr.ac.kopo.yj.bookmarket.service.BookService;
import kr.ac.kopo.yj.bookmarket.service.CartService;
import kr.ac.kopo.yj.bookmarket.service.OrderProService;
import kr.ac.kopo.yj.bookmarket.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    Order order;
    List<Book> listofBooks;

    @Autowired
    private OrderProService orderProService;

    //	@Autowired
    //   private BookService bookService;
    @GetMapping( "/{cartId}")
    public String requestCartList(@PathVariable(value = "cartId") String cartId, Model model) {
        Cart cart =  cartService.validateCart(cartId);
        order = new Order();
        listofBooks = new ArrayList<Book>();
        for(CartItem item : cart.getCartItems().values()){
            OrderItem  orderItem = new  OrderItem();
            Book book =item.getBook();
            listofBooks.add(book);
            orderItem.setBookId(book.getBookId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getOrderItems().put(book.getBookId(), orderItem);
        }
        order.setCustomer(new Customer());
        order.setShipping(new Shipping());
        order.setGrandTotal(cart.getGrandTotal());
        return "redirect:/order/orderCustomerInfo";
    }
    @GetMapping( "/orderCustomerInfo")
    public String requestCustomerInfoForm(Model model) {
        model.addAttribute("customer", order.getCustomer());
        return "orderCustomerInfo";
    }

    @PostMapping("/orderCustomerInfo")
    public String requestCustomerInfo( @ModelAttribute Customer  customer, Model model) {
        order.setCustomer(customer);
        return "redirect:/order/orderShippingInfo";
    }

    @GetMapping("/orderShippingInfo")
    public String requestShippingInfoForm(Model model) {
        model.addAttribute("shipping", order.getShipping());
        return "orderShippingInfo";
    }

    @PostMapping("/orderShippingInfo")
    public String requestShippingInfo(@Valid @ModelAttribute Shipping  shipping, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "orderShippingInfo";
        order.setShipping(shipping);
        model.addAttribute("order",order);
        return "redirect:/order/orderConfirmation";
    }

    @GetMapping("/orderConfirmation")
    public String requestConfirmation( Model model ) {
        model.addAttribute("bookList",listofBooks);
        model.addAttribute("order",order);
        return "orderConfirmation";
    }
    @PostMapping("/orderConfirmation")
    public String requestConfirmationFinished( Model model ) {
        model.addAttribute("order",order);
        orderProService.save(order);
        return "redirect:/order/orderFinished";
    }

    @GetMapping("/orderFinished")
    public String requestFinished( HttpServletRequest request, Model model ) {
        // Long orderId=
        orderService.saveOrder(order);
        //order.setOrderId(orderId);
        model.addAttribute("order",order);
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "orderFinished";
    }

    @GetMapping("/orderCancelled")
    public String requestCancelled(HttpServletRequest request ) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "orderCancelled";
    }
}
