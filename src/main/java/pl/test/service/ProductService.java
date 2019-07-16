package pl.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.test.model.Product;
import pl.test.model.TypeProduct;
import pl.test.repository.ProductRepository;

import java.lang.reflect.Type;
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

    public List<Product> findLimit(int limit){
        Pageable limitPageable = PageRequest.of(0,limit);
        return productRepository.findAll(limitPageable).getContent();
    }

    public void deleteProduct(long id){
        productRepository.delete(productRepository.getOne(id));
    }

    public List<Product> findByType(TypeProduct typeProduct){
        return productRepository.findByType(typeProduct);
    }
}

