package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
