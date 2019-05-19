package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.test.component.ShoppingCart;
import pl.test.model.Order;
import pl.test.service.OrderService;
import pl.test.service.UserService;

import java.security.Principal;

@Controller
public class OrderController {

    private OrderService orderService;


    @Autowired
    public void setOrderService(OrderService orderService){this.orderService = orderService;}



    @GetMapping("/order")
    public String makeAnOrder(Principal principal){
        Order order = new Order();
        orderService.addOrder(order,principal.getName());
        return "redirect:/";
    }
}
