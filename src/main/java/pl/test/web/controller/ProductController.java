package pl.test.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.catalina.Server;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.jca.context.ResourceAdapterApplicationContext;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring5.context.SpringContextUtils;
import pl.test.model.Product;
import pl.test.service.ProductService;
import javax.validation.Valid;

@Controller
public class ProductController {

    private ProductService productService;
    private static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

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
                    Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
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
}
