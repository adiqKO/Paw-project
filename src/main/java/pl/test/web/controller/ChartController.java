package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.test.Population;
import pl.test.model.Product;
import pl.test.service.ProductService;

import java.util.List;
import java.util.Random;

@RestController
public class ChartController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {this.productService = productService;}

    @RequestMapping("/city")
    public Population getCity() {
        Random random = new Random();
        int amount = random.nextInt(20);
        return new Population(7, amount);
    }

    @RequestMapping("/api/products")
    public List<Product> getProducts() {
        return productService.findAll();
    }
}
