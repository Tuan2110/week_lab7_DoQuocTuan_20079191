package vn.edu.iuh.fit.pks;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.models.Product;

import java.io.Serializable;

@Setter @Getter
public class OrderDetailPK implements Serializable {
    private Order order;
    private Product product;
}
