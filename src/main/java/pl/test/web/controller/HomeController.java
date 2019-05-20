package pl.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.test.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        if(null == session.getAttribute("theme")) {
            session.setAttribute("theme", "style.css");
        }
        model.addAttribute("products",productService.findLimit(8));
        return "index";
    }

    @GetMapping("/theme")
    public String theme(HttpServletRequest request,HttpSession session){
        Object v = session.getAttribute("theme");
        if(v != null && v.equals("style.css")){
            session.setAttribute("theme", "dark.css");
        }else {
            session.setAttribute("theme", "style.css");
        }
        return getPreviousPageByRequest(request).orElse("redirect:/");
    }

    @GetMapping("/chart")
    public String chart(){
        return "chart";
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    private Optional<String> getPreviousPageByRequest(HttpServletRequest request)
    {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}