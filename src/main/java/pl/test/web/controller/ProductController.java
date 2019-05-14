package pl.test.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.test.model.Product;
import pl.test.model.User;

import javax.validation.Valid;

@Controller
public class ProductController {

    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

    @GetMapping("/product/add")
    public String addPhoto(Model model){
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @PostMapping("/product/add")
    public String addPhotoToBase(Model model, @RequestParam("imageFile") MultipartFile[] files,
                                 @ModelAttribute @Valid Product product, BindingResult bindResult){

        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            if(Objects.equals(file.getContentType(), "image/jpeg")) {
                Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
                fileNames.append(file.getOriginalFilename() + " ");
                System.out.println(fileNameAndPath);
                try {
                    Files.write(fileNameAndPath, file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{ System.out.println("error"); }
        }

        if(bindResult.hasErrors()){
            return "productForm";
        }
        return "redirect:/";
    }
}
