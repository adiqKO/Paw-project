package pl.test.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.test.model.Product;
import pl.test.model.TypeProduct;
import pl.test.service.ProductService;
import pl.test.service.TypeProductService;

import javax.validation.Valid;

@Controller
public class ProductController {

    private ProductService productService;
    private TypeProductService typeProductService;
    //Path currentPath = Paths.get(".");
   // Path absolutePath = currentPath.toAbsolutePath();
    //private static String uploadDirectory = "/public/";


    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @Autowired
    public void setTypeProductService(TypeProductService typeProductService) {this.typeProductService = typeProductService; }

    @GetMapping("/product/add")
    public String addPhoto(Model model){
        model.addAttribute("types", typeProductService.findAll());
        model.addAttribute("product", new Product());
        return "admin/productForm";
    }

    @PostMapping("/product/add")
    public String addPhotoToBase(@RequestParam("imageFile") MultipartFile[] files,
                                 @ModelAttribute @Valid Product product, BindingResult bindResult, Model model){

            if(bindResult.hasErrors()){
                model.addAttribute("errors", bindResult.getModel());
                model.addAttribute("types", typeProductService.findAll());
                return "admin/productForm";
            }
            StringBuilder fileNames = new StringBuilder();
            for (MultipartFile file : files) {
                if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
                   // Path fileNameAndPath = Paths.get(absolutePath+"/src/main/resources/static/public/" + file.getOriginalFilename());
                    Path fileNameAndPath = Paths.get("C:/Software/Paw-project/target/classes/static/public/" + file.getOriginalFilename());
                    System.out.println(fileNameAndPath);
                    fileNames.append(file.getOriginalFilename()).append(" ");
                    try {
                        Files.write(fileNameAndPath, file.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else { return "redirect:/product/add?success=false"; }
            }
        product.setPathToPhoto(String.valueOf(fileNames));
        productService.addProduct(product);
        return "redirect:/product/add?success=true";
    }

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.findAll());
        return "admin/products";
    }

    @GetMapping("/items")
    public String showProducts(Model model){
        List<Product> products = productService.findAll();
        Set<TypeProduct> types = new HashSet<>();
        for (Product product: products){
            types.add(product.getType());
        }
        model.addAttribute("types", types);
        model.addAttribute("products",products);
        return "items";
    }

    @GetMapping("/items/type/{type}")
    public String showTypeProducts(@PathVariable("type") String type,Model model){
        String typeEdit = type.replace('+',' ');
        System.out.println(typeEdit);
        List<Product> products = productService.findAll();
        Set<TypeProduct> types = new HashSet<>();
        for (Product product: products){
            types.add(product.getType());
        }
        model.addAttribute("types", types);
        model.addAttribute("products", productService.findByType(typeProductService.findByName(typeEdit)));
        return "items";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return "redirect:/products?success";
    }

    @GetMapping("/item/show/{id}")
    public String showProduct(@PathVariable("id") long id, Model model){
        model.addAttribute("product",productService.findById(id));
        return "item";
    }

}
