package pl.test.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.test.logic.PdfReport;
import pl.test.model.Order;
import pl.test.service.OrderService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class ReportController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService){this.orderService = orderService;}

    @GetMapping(value = "/pdfreport/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport(@PathVariable("id") long id, Principal principal, SecurityContextHolderAwareRequestWrapper request) throws IOException {

        Order findOrder = new Order();
        List<Order> orders = orderService.findAll();
        for (Order order : orders) {
            if (order.getId() == id) {
                findOrder = order;
            }
        }
        if (findOrder.getUser().getEmail().equals(principal.getName()) || request.isUserInRole("ROLE_ADMIN") ) {

            ByteArrayInputStream bis = PdfReport.orderReport(findOrder);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
        }
        return null;
    }
}
