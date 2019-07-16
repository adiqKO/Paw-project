package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.test.model.TypeProduct;
import pl.test.service.TypeProductService;

import javax.validation.Valid;

@Controller
public class TypeProductController {

    private TypeProductService typeProductService;

    @Autowired
    public void setTypeProductService(TypeProductService typeProductService) {
        this.typeProductService = typeProductService;
    }

    @GetMapping("/type/add")
    public String showForm(Model model){
        model.addAttribute("typeProduct", new TypeProduct());
        return "typeForm";
    }

    @PostMapping("/type/add")
    public String addType(@ModelAttribute @Valid TypeProduct typeProduct, BindingResult bindResult) {

        if(bindResult.hasErrors())
            return "typeForm";
        else {
            typeProductService.addType(typeProduct);
            return "redirect:/product/add";
        }
    }

}
