package pl.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.test.model.TypeProduct;
import pl.test.repository.TypeProductRepository;

import java.util.List;

@Service
public class TypeProductService {

    private TypeProductRepository typeProductRepository;

    @Autowired
    public void setTypeProductRepository(TypeProductRepository typeProductRepository) {
        this.typeProductRepository = typeProductRepository;
    }

    public void addType(TypeProduct typeProduct) {
        typeProductRepository.save(typeProduct);
    }

    public List<TypeProduct> findAll(){
        return typeProductRepository.findAll();
    }

    public TypeProduct findByName(String name){ return typeProductRepository.findByName(name);}
}
