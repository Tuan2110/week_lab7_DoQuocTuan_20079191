package vn.edu.iuh.fit.dtos;

import vn.edu.iuh.fit.models.ProductImage;

import java.util.List;
import java.util.Objects;

public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private String unit;
    private Integer quantity;

    private String manufacturer;
    private List<ProductImage> productImages;
    private double price;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, String description, String unit, Integer quantity, String manufacturer, List<ProductImage> productImages, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.productImages = productImages;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
