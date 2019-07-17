package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.test.model.Order;
import pl.test.service.OrderService;
import pl.test.service.UserService;

import java.security.Principal;

@Controller
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setOrderService(OrderService orderService){this.orderService = orderService;}

    @Autowired
    public void setUserService(UserService userService) {this.userService = userService;}

    @GetMapping("/order")
    public String makeAnOrder(Principal principal){
        if(userService.findByEmail(principal.getName()).getUserSpecific() != null) {
            Order order = new Order();
            orderService.addOrder(order, principal.getName());
            return "redirect:/orders?success=true";
        }
        return "redirect:/account?success=false";
    }

    @GetMapping("/orders")
    public String showUserOrders(Model model, Principal principal){
        model.addAttribute("orders", orderService.findByUser(principal.getName()));

        return "user-order";
    }

    @GetMapping("/orders/show/all")
    public String showAllOrders(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }


}
