package pl.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.test.model.Product;
import pl.test.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository){this.productRepository = productRepository;}

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public Product findById(long id){
        return productRepository.getOne(id);
    }
}

