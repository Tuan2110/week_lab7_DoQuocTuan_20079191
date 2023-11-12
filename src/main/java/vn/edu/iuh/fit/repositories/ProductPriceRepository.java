package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.models.ProductPrice;
import vn.edu.iuh.fit.pks.ProductPricePK;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPricePK> {
    @Query(value = "select p from ProductPrice p where p.product.id = ?1 order by p.price_date_time desc limit 1")
    ProductPrice findNewPriceByProductId(long productId);
}