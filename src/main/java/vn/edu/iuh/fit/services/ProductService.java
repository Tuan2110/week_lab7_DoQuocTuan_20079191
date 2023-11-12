package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.dtos.ProductDTO;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductImageRepository;
import vn.edu.iuh.fit.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    public ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getProduct_id());
        productDTO.setName(product.getName());
        productDTO.setPrice(productPriceRepository.findNewPriceByProductId(product.getProduct_id()).getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setProductImages(product.getProductImageList());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setUnit(product.getUnit());
        productDTO.setManufacturer(product.getManufacturer());
        return productDTO;
    }
    public Page<ProductDTO> findAll(int pageNo, int pageSize, String sortBy,
                                    String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertToDTO);
    }
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

}
