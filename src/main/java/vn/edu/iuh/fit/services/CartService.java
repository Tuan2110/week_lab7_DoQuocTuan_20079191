package vn.edu.iuh.fit.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import vn.edu.iuh.fit.dtos.ProductDTO;
import vn.edu.iuh.fit.exception.OutOfStockException;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION,proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartService {
    @Autowired
    private ProductRepository productRepository;
    private Map<ProductDTO,Integer> products = new HashMap<>();

    public void addProduct(ProductDTO product){
        if(products.containsKey(product)){
            products.replace(product,products.get(product)+1);
        }else{
            products.put(product,1);
        }
    }
    public void removeProduct(ProductDTO product){
        if(products.containsKey(product)){
            if(products.get(product)>1){
                products.replace(product,products.get(product)-1);
            }else if(products.get(product)==1){
                products.remove(product);
            }
        }
    }
    public Map<ProductDTO,Integer> getProducts(){
        return Collections.unmodifiableMap(products);
    }
    public void checkout() throws OutOfStockException{
        ProductDTO product;
        for(Map.Entry<ProductDTO,Integer> entry:products.entrySet()){
            product =entry.getKey();
            if(product.getQuantity()<entry.getValue()){
                throw new RuntimeException("Not enough product");
            }
            Product p = productRepository.findById(entry.getKey().getId()).get();
            p.setQuantity(entry.getKey().getQuantity()-entry.getValue());
            productRepository.save(p);
        }
        productRepository.flush();
        products.clear();
    }

    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for(Map.Entry<ProductDTO,Integer> entry:products.entrySet()){
            total = total.add(BigDecimal.valueOf(entry.getKey().getPrice()*entry.getValue()));
        }
        return total;
    }
}
