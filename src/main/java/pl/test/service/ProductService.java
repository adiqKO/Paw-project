package pl.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.test.model.Product;
import pl.test.repository.ProductRepository;

import java.util.List;

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

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public void deleteProduct(long id){
        productRepository.delete(productRepository.getOne(id));
    }
}

