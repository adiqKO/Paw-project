package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
