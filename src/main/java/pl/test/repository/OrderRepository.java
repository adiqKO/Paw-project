package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.Order;
import pl.test.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
