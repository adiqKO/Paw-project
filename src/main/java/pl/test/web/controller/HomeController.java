package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.test.model.Product;
import pl.test.service.ProductService;

@Controller
public class HomeController {

    private ProductService productService;
    private static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping("/")
    public String home(Model model) {
        Product product = productService.findById(1L);
        model.addAttribute("directory",uploadDirectory);
        model.addAttribute("product",product);
        return "index";
    }

    @RequestMapping("/test")
    public String homeTets(Model model) {
        return "test";
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }



}