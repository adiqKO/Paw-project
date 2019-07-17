package pl.test.model;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2,message = "{pl.test.model.Product.title.Size}")
    private String title;
    @NotEmpty(message = "{pl.test.model.Product.description.NotEmpty}")
    private String description;
    @Column(name="price")
    @NotNull(message = "{pl.test.model.Product.price.NotNull}")
    @Digits(integer=5, fraction=2)
    @NumberFormat(pattern="##,##")
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER)
    private TypeProduct type;
    private String pathToPhoto;

    public Product(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public TypeProduct getType() {
        return type;
    }

    public void setType(TypeProduct type) {
        this.type = type;
    }
}