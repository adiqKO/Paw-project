package pl.test.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.test.component.ShoppingCart;
import pl.test.model.Product;
import pl.test.service.ProductService;
import javax.validation.Valid;

@Controller
public class ProductController {

    private ProductService productService;
    //Path currentPath = Paths.get(".");
   // Path absolutePath = currentPath.toAbsolutePath();
    //private static String uploadDirectory = "/public/";


    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/product/add")
    public String addPhoto(Model model){
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @PostMapping("/product/add")
    public String addPhotoToBase(@RequestParam("imageFile") MultipartFile[] files,
                                 @ModelAttribute @Valid Product product, BindingResult bindResult){

            if(bindResult.hasErrors())
                return "productForm";
            StringBuilder fileNames = new StringBuilder();
            for (MultipartFile file : files) {
                if (Objects.equals(file.getContentType(), "image/jpeg")) {
                   // Path fileNameAndPath = Paths.get(absolutePath+"/src/main/resources/static/public/" + file.getOriginalFilename());
                    Path fileNameAndPath = Paths.get("C:/Software/paw-project/target/classes/static/public/" + file.getOriginalFilename());
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
        return "redirect:/";
    }

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return "redirect:/products?success";
    }

}
