package pl.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test.model.TypeProduct;

public interface TypeProductRepository extends JpaRepository<TypeProduct,Long> {
    TypeProduct findByName(String name);
}
