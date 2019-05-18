package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.test.component.ShoppingCart;
import pl.test.model.Product;
import pl.test.service.ProductService;
import java.util.List;

@Controller
public class ShoppingCartController {

    private ShoppingCart shoppingCart;
    private ProductService productService;


    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @Autowired
    public void setShoppingCart(ShoppingCart shoppingCart){ this.shoppingCart = shoppingCart; }

    @GetMapping("/cart/add/{id}")
    public String addProduct(@PathVariable("id") long id){
        Product product = productService.findById(id);
        System.out.println(product);
        shoppingCart.getProducts().add(product);

        return "redirect:/cart";
    }
    @GetMapping("/cart")

    public String showCart(Model model){
        List list = shoppingCart.getProducts();
        model.addAttribute("products",list);
        model.addAttribute("price",shoppingCart.getPrice());
        return "shoppingCart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        shoppingCart.getProducts().remove(id);
        return "redirect:/cart";
    }

}
