package pl.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.test.component.ShoppingCart;
import pl.test.model.Order;
import pl.test.model.Product;
import pl.test.model.User;
import pl.test.repository.OrderRepository;
import pl.test.repository.ProductRepository;
import pl.test.repository.UserRepository;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ShoppingCart shoppingCart;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setShoppingCart(ShoppingCart shoppingCart){ this.shoppingCart = shoppingCart; }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository){this.productRepository = productRepository;}

    public void addOrder(Order order, String email){
        List<Product> products = shoppingCart.getProducts();
        order.setUser(userRepository.findByEmail(email));
        for(Product product : products){
           order.getProducts().add(productRepository.getOne(product.getId()));
        }
        orderRepository.save(order);
        shoppingCart.getProducts().clear();
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public List<Order> findByUser(String email){
        User user = userRepository.findByEmail(email);
        return orderRepository.findByUser(user);
    }

    public Order findById(long id){
        return orderRepository.getOne(id);
    }
}
