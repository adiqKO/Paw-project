package pl.test.component;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.test.model.Product;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope(scopeName= WebApplicationContext.SCOPE_SESSION, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {

    private List<Product> products;
    private BigDecimal price;

    public ShoppingCart(){
        products = new LinkedList<>();
        price = new BigDecimal(0);
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getPrice(){
        price = BigDecimal.ZERO;
        for(Product product : products){
            price = price.add(product.getPrice());
        }
        return price;
    }
}
